package com.arisejee.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.arisejee.app.AriseJeeApp
import com.arisejee.app.data.repository.StudyRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate

class StudyTimerViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = StudyRepository((app as AriseJeeApp).database.studySessionDao())
    private val _elapsedSeconds = MutableStateFlow(0L)
    val elapsedSeconds: StateFlow<Long> = _elapsedSeconds.asStateFlow()
    private val _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> = _isRunning.asStateFlow()
    private val _todayMinutes = MutableStateFlow(0)
    val todayMinutes: StateFlow<Int> = _todayMinutes.asStateFlow()
    private val _weekMinutes = MutableStateFlow(0)
    val weekMinutes: StateFlow<Int> = _weekMinutes.asStateFlow()
    private val _totalMinutes = MutableStateFlow(0)
    val totalMinutes: StateFlow<Int> = _totalMinutes.asStateFlow()
    private var timerJob: Job? = null
    private var startTimeMillis: Long = 0

    init {
        val today = LocalDate.now().toString(); val weekStart = LocalDate.now().minusDays(6).toString()
        viewModelScope.launch { repo.getTodayMinutes(today).collectLatest { _todayMinutes.value = it } }
        viewModelScope.launch { repo.getWeekMinutes(weekStart, today).collectLatest { _weekMinutes.value = it } }
        viewModelScope.launch { repo.getTotalMinutes().collectLatest { _totalMinutes.value = it } }
    }
    fun startTimer() {
        if (_isRunning.value) return; _isRunning.value = true; startTimeMillis = System.currentTimeMillis(); _elapsedSeconds.value = 0
        timerJob = viewModelScope.launch { while (true) { delay(1000); _elapsedSeconds.value++ } }
    }
    fun stopTimer() {
        if (!_isRunning.value) return; timerJob?.cancel(); _isRunning.value = false
        val dur = (_elapsedSeconds.value / 60).toInt().coerceAtLeast(1)
        viewModelScope.launch { repo.saveSession(LocalDate.now().toString(), startTimeMillis, System.currentTimeMillis(), dur) }
    }
}
