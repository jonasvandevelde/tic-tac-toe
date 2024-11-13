package com.test.tictactoe.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import com.test.tictactoe.R
import com.test.tictactoe.objects.SquareState
import com.test.tictactoe.ui.theme.TicTacToeTheme
import com.test.tictactoe.utils.Spacing.x0_5
import com.test.tictactoe.utils.Spacing.x1

@Composable
internal fun RowScope.GameSquare(
    row: Int,
    col: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    state: SquareState = SquareState.NONE,
) {
    val description = when (state) {
        SquareState.NONE -> stringResource(
            R.string.tictactoe_semantics_empty_square,
            row + 1,
            col + 1
        )

        SquareState.O -> stringResource(R.string.tictactoe_semantics_square_O, row + 1, col + 1)
        SquareState.X -> stringResource(R.string.tictactoe_semantics_square_X, row + 1, col + 1)
    }

    val clickableModifier = if (state == SquareState.NONE) {
        Modifier.clickable(onClick = onClick)
    } else {
        Modifier
    }

    Box(
        modifier = modifier
            .testTag("square_${row}_${col}")
            .weight(1f)
            .fillMaxSize()
            .padding(x0_5)
            .background(Color.LightGray, shape = RoundedCornerShape(x1))
            .clip(RoundedCornerShape(x1))
            .then(clickableModifier)
            .semantics(mergeDescendants = true) { contentDescription = description },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = when (state) {
                SquareState.NONE -> ""
                SquareState.O -> "O"
                SquareState.X -> "X"
            },
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Preview
@Composable
private fun preview() {
    TicTacToeTheme {
        Row {
            GameSquare(state = SquareState.O, row = 0, col = 0, onClick = {})
        }
    }
}
