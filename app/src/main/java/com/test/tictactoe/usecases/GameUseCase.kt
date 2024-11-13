package com.test.tictactoe.usecases

import com.test.tictactoe.states.GameState

interface GameUseCase {
    fun makeMove(state: GameState, row: Int, col: Int): GameState
    fun resetGame(): GameState
}
