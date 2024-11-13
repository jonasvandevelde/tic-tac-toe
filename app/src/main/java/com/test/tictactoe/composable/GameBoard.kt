package com.test.tictactoe.composable

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.test.tictactoe.R
import com.test.tictactoe.intents.GameIntent
import com.test.tictactoe.objects.WinnerState
import com.test.tictactoe.ui.theme.TicTacToeTheme
import com.test.tictactoe.utils.Spacing.x0_125
import com.test.tictactoe.viewmodel.GameViewModel

@Composable
fun GameBoard(
    viewModel: GameViewModel = viewModel(),
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val state = viewModel.state.collectAsState().value

    val winnerMessageTemplate = stringResource(R.string.tictactoe_winner_message)
    val drawMessageTemplate = stringResource(R.string.tictactoe_draw_message)

    LaunchedEffect(state) {
        state.winner?.let { winnerState ->
            val toastMessage = when (winnerState) {
                WinnerState.DRAW -> drawMessageTemplate
                WinnerState.X,
                WinnerState.O -> String.format(winnerMessageTemplate, winnerState.name)
            }
            Toast.makeText(
                context,
                toastMessage, Toast.LENGTH_SHORT
            ).show()
            viewModel.handleIntent(GameIntent.ResetGame)
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier) {
            state.board.forEachIndexed { row, rowList ->
                Row(modifier = Modifier.weight(1f)) {
                    rowList.forEachIndexed { col, squareState ->
                        GameSquare(
                            state = squareState,
                            row = row,
                            col = col,
                            modifier = Modifier.padding(
                                x0_125
                            ),
                            onClick = {
                                viewModel.handleIntent(GameIntent.MakeMove(row, col))
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun preview() {
    TicTacToeTheme {
        GameBoard()
    }
}
