package com.test.tictactoe

import androidx.compose.foundation.layout.Row
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.test.tictactoe.composable.GameSquare
import com.test.tictactoe.objects.SquareState
import com.test.tictactoe.ui.theme.TicTacToeTheme
import org.junit.Rule
import org.junit.Test

class GameSquareTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun gameSquareDisplaysCorrectState_O() {
        composeTestRule.setContent {
            TicTacToeTheme {
                Row {
                    GameSquare(
                        state = SquareState.O,
                        row = 0,
                        col = 0,
                        onClick = {})
                }
            }
        }
        composeTestRule.onNodeWithText("O").assertIsDisplayed()
        composeTestRule
            .onNodeWithContentDescription("Square with O at row 1, column 1")
            .assertIsDisplayed()
    }

    @Test
    fun gameSquareDisplaysCorrectState_X() {
        composeTestRule.setContent {
            TicTacToeTheme {
                Row {
                    GameSquare(
                        state = SquareState.X,
                        row = 0,
                        col = 0,
                        onClick = {})
                }
            }
        }
        composeTestRule.onNodeWithText("X").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Square with X at row 1, column 1")
            .assertIsDisplayed()
    }

    @Test
    fun gameSquareDisplaysEmptyState() {
        composeTestRule.setContent {
            TicTacToeTheme {
                Row {
                    GameSquare(
                        state = SquareState.NONE,
                        row = 0,
                        col = 0,
                        onClick = {})
                }
            }
        }
        composeTestRule.onNodeWithText("").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Empty square at row 1, column 1")
            .assertIsDisplayed()
    }

    @Test
    fun gameSquareClickable() {
        var clicked = false

        composeTestRule.setContent {
            TicTacToeTheme {
                Row {
                    GameSquare(
                        state = SquareState.NONE,
                        row = 0,
                        col = 0,
                        onClick = { clicked = true })
                }
            }
        }
        composeTestRule.onNodeWithText("").performClick()

        assert(clicked)
    }
}
