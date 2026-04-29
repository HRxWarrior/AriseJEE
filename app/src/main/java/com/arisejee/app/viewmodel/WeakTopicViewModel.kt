package com.arisejee.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.arisejee.app.AriseJeeApp
import com.arisejee.app.data.model.WeakTopic
import com.arisejee.app.util.WeakTopicAnalyzer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class WeakTopicViewModel(app: Application) : AndroidViewModel(app) {
    private val db = (app as AriseJeeApp).database
    private val _weakTopics = MutableStateFlow<List<WeakTopic>>(emptyList())
    val weakTopics: StateFlow<List<WeakTopic>> = _weakTopics.asStateFlow()
    init { viewModelScope.launch { db.quizResultDao().getAllResults().collectLatest { _weakTopics.value = WeakTopicAnalyzer.analyze(it) } } }
}
