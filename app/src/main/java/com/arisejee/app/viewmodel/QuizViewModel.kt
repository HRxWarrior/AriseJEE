package com.arisejee.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.arisejee.app.AriseJeeApp
import com.arisejee.app.data.db.entity.QuestionEntity
import com.arisejee.app.data.db.entity.QuizResultEntity
import com.arisejee.app.data.model.Difficulty
import com.arisejee.app.data.model.Rank
import com.arisejee.app.data.repository.QuestionRepository
import com.arisejee.app.data.repository.UserRepository
import com.arisejee.app.util.XpCalculator
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class QuizViewModel(app: Application) : AndroidViewModel(app) {
    private val db = (app as AriseJeeApp).database
    private val questionRepo = QuestionRepository(db.questionDao())
    private val userRepo = UserRepository(db.userDao())
    private val resultDao = db.quizResultDao()

    private val _questions = MutableStateFlow<List<QuestionEntity>>(emptyList())
    val questions: StateFlow<List<QuestionEntity>> = _questions.asStateFlow()
    private val _currentIndex = MutableStateFlow(0)
    val currentIndex: StateFlow<Int> = _currentIndex.asStateFlow()
    private val _selectedAnswer = MutableStateFlow(-1)
    val selectedAnswer: StateFlow<Int> = _selectedAnswer.asStateFlow()
    private val _showResult = MutableStateFlow(false)
    val showResult: StateFlow<Boolean> = _showResult.asStateFlow()
    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score.asStateFlow()
    private val _totalXp = MutableStateFlow(0)
    val totalXp: StateFlow<Int> = _totalXp.asStateFlow()
    private val _quizFinished = MutableStateFlow(false)
    val quizFinished: StateFlow<Boolean> = _quizFinished.asStateFlow()
    private val _timer = MutableStateFlow(0L)
    val timer: StateFlow<Long> = _timer.asStateFlow()
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    private val _loadMoreMessage = MutableStateFlow("")
    val loadMoreMessage: StateFlow<String> = _loadMoreMessage.asStateFlow()
    private val _totalAvailable = MutableStateFlow(0)
    val totalAvailable: StateFlow<Int> = _totalAvailable.asStateFlow()

    private var timerJob: Job? = null
    private var streakCount = 0
    private var currentSubject = ""
    private var currentChapter = ""
    private var currentExamType = ""
    private var usedQuestionIds = mutableSetOf<Long>()

    fun loadQuiz(subject: String, chapter: String, examType: String) {
        currentSubject = subject; currentChapter = chapter; currentExamType = examType
        viewModelScope.launch {
            _isLoading.value = true
            questionRepo.ensureQuestionsLoaded(subject, chapter, examType)
            refreshQuestions()
            _currentIndex.value = 0; _score.value = 0; _totalXp.value = 0
            _quizFinished.value = false; _selectedAnswer.value = -1; _showResult.value = false
            streakCount = 0; usedQuestionIds.clear(); _isLoading.value = false; startTimer()
        }
    }

    private suspend fun refreshQuestions() {
        val allQs = questionRepo.getQuestions(currentSubject, currentChapter, currentExamType).first()
        _totalAvailable.value = allQs.size
        // Filter out already-used questions, then pick 15 (5E+5M+5H)
        val unused = allQs.filter { it.id !in usedQuestionIds }
        val pool = if (unused.size >= 5) unused else allQs // fallback to all if not enough unused
        val easy = pool.filter { it.difficulty == "EASY" }.shuffled().take(5)
        val med = pool.filter { it.difficulty == "MEDIUM" }.shuffled().take(5)
        val hard = pool.filter { it.difficulty == "HARD" }.shuffled().take(5)
        _questions.value = (easy + med + hard).shuffled()
    }

    fun loadMore(subject: String, chapter: String, examType: String) {
        viewModelScope.launch {
            val before = questionRepo.getCount(subject, chapter, examType)
            questionRepo.loadQuestions(subject, chapter, examType, 15)
            val after = questionRepo.getCount(subject, chapter, examType)
            if (after > before) {
                _loadMoreMessage.value = "${after - before} new questions added! Quiz will use them next round."
            } else {
                // Mark current questions as used and reload with different shuffle
                usedQuestionIds.addAll(_questions.value.map { it.id })
                refreshQuestions()
                if (_questions.value.isNotEmpty()) {
                    _loadMoreMessage.value = "Reshuffled! ${_totalAvailable.value} questions available."
                    _currentIndex.value = 0; _score.value = 0; _totalXp.value = 0
                    _quizFinished.value = false; _selectedAnswer.value = -1; _showResult.value = false
                    streakCount = 0; startTimer()
                } else {
                    _loadMoreMessage.value = "All offline questions completed! Connect API for more."
                }
            }
        }
    }

    fun clearLoadMoreMessage() { _loadMoreMessage.value = "" }

    private fun startTimer() {
        timerJob?.cancel(); _timer.value = 0
        timerJob = viewModelScope.launch { while (true) { delay(1000); _timer.value++ } }
    }

    fun selectAnswer(index: Int) { if (!_showResult.value) _selectedAnswer.value = index }

    fun confirmAnswer() {
        val q = _questions.value.getOrNull(_currentIndex.value) ?: return
        val correct = _selectedAnswer.value == q.correctAnswer
        val timeTaken = _timer.value.toInt()
        val diff = try { Difficulty.valueOf(q.difficulty) } catch (_: Exception) { Difficulty.MEDIUM }
        viewModelScope.launch {
            val user = userRepo.getUser().first()
            val rank = if (user != null) Rank.fromString(user.rank) else Rank.E
            if (correct) streakCount++ else streakCount = 0
            val xp = XpCalculator.calculate(diff, rank, correct, streakCount)
            _totalXp.value += xp.coerceAtLeast(0)
            if (correct) _score.value++
            userRepo.addXp(xp.coerceAtLeast(0)); userRepo.recordAnswer(correct)
            resultDao.insert(QuizResultEntity(subject=q.subject, chapter=q.chapter, examType=q.examType, questionId=q.id, isCorrect=correct, timeTakenSeconds=timeTaken, difficulty=q.difficulty))
        }
        usedQuestionIds.add(q.id)
        _showResult.value = true; timerJob?.cancel()
    }

    fun nextQuestion() {
        if (_currentIndex.value + 1 >= _questions.value.size) { _quizFinished.value = true; return }
        _currentIndex.value++; _selectedAnswer.value = -1; _showResult.value = false; startTimer()
    }
}
