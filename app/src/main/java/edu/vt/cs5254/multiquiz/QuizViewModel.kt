package edu.vt.cs5254.multiquiz

import androidx.lifecycle.ViewModel

class QuizViewModel : ViewModel() {

    private val answerListQuestion1 = listOf(
        Answer(R.string.answer_0_question_1, true),
        Answer(R.string.answer_1_question_1, false),
        Answer(R.string.answer_2_question_1, false),
        Answer(R.string.answer_3_question_1, false),
    )

    private val answerListQuestion2 = listOf(
        Answer(R.string.answer_0_question_2, false),
        Answer(R.string.answer_1_question_2, true),
        Answer(R.string.answer_2_question_2, false),
        Answer(R.string.answer_3_question_2, false),
    )

    private val answerListQuestion3 = listOf(
        Answer(R.string.answer_0_question_3, false),
        Answer(R.string.answer_1_question_3, false),
        Answer(R.string.answer_2_question_3, true),
        Answer(R.string.answer_3_question_3, false),
    )

    private val answerListQuestion4 = listOf(
        Answer(R.string.answer_0_question_4, false),
        Answer(R.string.answer_1_question_4, false),
        Answer(R.string.answer_2_question_4, false),
        Answer(R.string.answer_3_question_4, true),
    )

    val questionBank = listOf(
        Question(R.string.question_1, answerListQuestion1),
        Question(R.string.question_2, answerListQuestion2),
        Question(R.string.question_3, answerListQuestion3),
        Question(R.string.question_4, answerListQuestion4)
    )

    var currentQuestionIndex = 0

    val isLastQuestion
        get() = questionBank.size - 1 == currentQuestionIndex

    val questionResId
        get() = questionBank[currentQuestionIndex].questionResId

    val answerList
        get() = questionBank[currentQuestionIndex].answerList

    val score: Int
        get() = questionBank.flatMap { it.answerList }.let { answers ->
            (answers.count { it.isCorrect && it.isSelected } * 25) - (answers.count { !it.isEnabled } * 7)
        }
}