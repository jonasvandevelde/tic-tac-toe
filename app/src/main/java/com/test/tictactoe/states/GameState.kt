package com.test.tictactoe.states

import com.test.tictactoe.objects.Player
import com.test.tictactoe.objects.SquareState
import com.test.tictactoe.objects.WinnerState

data class GameState(
    val board: List<List<SquareState>> = List(3) { List(3) { SquareState.NONE } },
    val currentPlayer: Player = Player.X,
    val winner: WinnerState? = null
)
