package edu.vt.cs5254.multiquiz

import androidx.lifecycle.ViewModel

class ScoreViewModel : ViewModel() {
    var isReset: Boolean = false

    var scoreText: String = ""
}