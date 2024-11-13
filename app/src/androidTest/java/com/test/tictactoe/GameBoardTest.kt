package com.test.tictactoe

import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.test.tictactoe.composable.GameBoard
import com.test.tictactoe.intents.GameIntent
import com.test.tictactoe.objects.WinnerState
import com.test.tictactoe.states.GameState
import com.test.tictactoe.viewmodel.GameViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GameBoardTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val mockViewModel = mockk<GameViewModel>(relaxed = true)

    @Test
    fun gameBoardInitialStateDisplaysEmptyBoard() {
        val initialState = GameState()
        every { mockViewModel.state } returns MutableStateFlow(initialState)

        composeTestRule.setContent {
            GameBoard(viewModel = mockViewModel)
        }

        for (row in 0..2) {
            for (col in 0..2) {
                composeTestRule
                    .onNodeWithTag("square_${row}_${col}")
                    .assertExists()
                    .assertIsEnabled()
            }
        }
    }

    @Test
    fun gameBoardWhenSquareClickedCallsHandleIntent() {
        val initialState = GameState()
        every { mockViewModel.state } returns MutableStateFlow(initialState)

        composeTestRule.setContent {
            GameBoard(viewModel = mockViewModel)
        }
        composeTestRule
            .onNodeWithTag("square_0_0")
            .performClick()

        verify { mockViewModel.handleIntent(GameIntent.MakeMove(0, 0)) }
    }

    @Test
    fun gameBoardWhenPlayerWinsShowsToastAndResetsGame() {
        val winningState = GameState(
            winner = WinnerState.X
        )
        every { mockViewModel.state } returns MutableStateFlow(winningState)

        composeTestRule.setContent {
            GameBoard(viewModel = mockViewModel)
        }

        verify { mockViewModel.handleIntent(GameIntent.ResetGame) }
    }

    @Test
    fun gameBoardWhenGameDrawsShowsDrawToastAndResetsGame() {
        val drawState = GameState(
            winner = WinnerState.DRAW
        )
        every { mockViewModel.state } returns MutableStateFlow(drawState)

        composeTestRule.setContent {
            GameBoard(viewModel = mockViewModel)
        }

        verify { mockViewModel.handleIntent(GameIntent.ResetGame) }
    }
}
