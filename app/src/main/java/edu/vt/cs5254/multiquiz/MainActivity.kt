package edu.vt.cs5254.multiquiz

import android.os.Bundle
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import edu.vt.cs5254.multiquiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Name: Kingsley Bao Ho
    // Username: bgho21

    private lateinit var binding: ActivityMainBinding

    private val vm: QuizViewModel by viewModels()

    private lateinit var buttonList: List<Button>

    private val scoreActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        vm.currentQuestionIndex = 0
        val shouldReset =
            result.data?.getBooleanExtra(ScoreActivity.RESET_EXTRA, false) ?: false
        if (shouldReset) {
            vm.questionBank.forEach { question ->
                question.answerList.forEach { answer ->
                    answer.isSelected = false
                    answer.isEnabled = true
                }
            }
        }
        updateView(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        buttonList = listOf(
            binding.answer0Button,
            binding.answer1Button,
            binding.answer2Button,
            binding.answer3Button
        )

        vm.answerList.zip(buttonList).forEach { (ans, button) ->
            button.setOnClickListener {
                ans.isSelected = !ans.isSelected
                vm.answerList.filter { it != ans }.forEach { it.isSelected = false }
                updateView()
            }
        }

        binding.hintButton.setOnClickListener {
            vm.answerList.filter { !it.isCorrect && it.isEnabled }.random().let {
                it.isEnabled = false
                it.isSelected = false
            }
            updateView()
        }

        binding.submitButton.setOnClickListener {
            if (vm.isLastQuestion) {
                scoreActivityLauncher.launch(ScoreActivity.newIntent(this, vm.score))
            } else {
                vm.currentQuestionIndex = (vm.currentQuestionIndex + 1) % vm.questionBank.size
                updateView(true)
            }
        }

        updateView()
    }

    private fun updateView(fullUpdate: Boolean = false) {
        if (fullUpdate) {
            binding.questionText.setText(vm.questionResId)
            vm.answerList.zip(buttonList).forEach { (ans, button) ->
                button.setText(ans.textResId)
                button.setOnClickListener {
                    ans.isSelected = !ans.isSelected
                    vm.answerList.filter { it != ans }.forEach { it.isSelected = false }
                    updateView()
                }
            }
        }

        vm.answerList.zip(buttonList).forEach { (ans, button) ->
            button.isSelected = ans.isSelected
            button.isEnabled = ans.isEnabled
            button.updateColor()
        }

        binding.hintButton.isEnabled = vm.answerList.any { !it.isCorrect && it.isEnabled }
        binding.submitButton.isEnabled = vm.answerList.any { it.isSelected }
    }
}