package edu.vt.cs5254.multiquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import edu.vt.cs5254.multiquiz.databinding.ActivityScoreBinding

class ScoreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScoreBinding

    private val vm: ScoreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!vm.isReset) {
            vm.scoreText = String.format("%s", intent.getIntExtra(SCORE_EXTRA, -1))
        }

        onBackPressedDispatcher.addCallback(this){
            if (!vm.isReset){
                setResult(Activity.RESULT_OK, Intent().putExtra(RESET_EXTRA, false))
            }
            finish()
        }

        binding.resetButton.setOnClickListener {
            setResult(Activity.RESULT_OK, Intent().putExtra(RESET_EXTRA, true))
            vm.isReset = true
            vm.scoreText = "?"
            updateView()
        }

        setResult(Activity.RESULT_OK, Intent().putExtra(RESET_EXTRA, vm.isReset))

        updateView()
    }

    private fun updateView() {
        binding.scoreText.text = vm.scoreText
        binding.resetButton.isEnabled = !vm.isReset
    }

    companion object {
        const val SCORE_EXTRA = "edu.vt.cs5254.multiquiz.score"
        const val RESET_EXTRA = "edu.vt.cs5254.multiquiz.reset"

        fun newIntent(context: Context, score: Int): Intent {
            return Intent(context, ScoreActivity::class.java).apply {
                putExtra(SCORE_EXTRA, score)
            }
        }
    }
}