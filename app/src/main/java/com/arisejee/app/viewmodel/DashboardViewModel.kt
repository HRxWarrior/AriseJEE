package com.arisejee.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.arisejee.app.AriseJeeApp
import com.arisejee.app.data.db.entity.DailyQuestEntity
import com.arisejee.app.data.db.entity.UserEntity
import com.arisejee.app.data.model.WeakTopic
import com.arisejee.app.data.repository.QuestRepository
import com.arisejee.app.data.repository.UserRepository
import com.arisejee.app.util.WeakTopicAnalyzer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate

class DashboardViewModel(app: Application) : AndroidViewModel(app) {
    private val db = (app as AriseJeeApp).database
    private val userRepo = UserRepository(db.userDao())
    private val questRepo = QuestRepository(db.dailyQuestDao())

    private val _user = MutableStateFlow<UserEntity?>(null)
    val user: StateFlow<UserEntity?> = _user.asStateFlow()
    private val _quests = MutableStateFlow<List<DailyQuestEntity>>(emptyList())
    val quests: StateFlow<List<DailyQuestEntity>> = _quests.asStateFlow()
    private val _weakTopics = MutableStateFlow<List<WeakTopic>>(emptyList())
    val weakTopics: StateFlow<List<WeakTopic>> = _weakTopics.asStateFlow()

    init {
        viewModelScope.launch { userRepo.ensureUserExists(); val today = LocalDate.now().toString(); questRepo.ensureQuestsExist(today); userRepo.getUser().collectLatest { _user.value = it } }
        viewModelScope.launch { questRepo.getQuests(LocalDate.now().toString()).collectLatest { _quests.value = it } }
        viewModelScope.launch { db.quizResultDao().getAllResults().collectLatest { _weakTopics.value = WeakTopicAnalyzer.analyze(it) } }
    }

    fun updateStreak() { viewModelScope.launch {
        val user = _user.value ?: return@launch; val today = LocalDate.now().toString()
        val newStreak = if (user.lastActiveDate == LocalDate.now().minusDays(1).toString() || user.lastActiveDate == today) user.streak + 1 else 1
        userRepo.updateStreak(newStreak, today)
    }}
}
