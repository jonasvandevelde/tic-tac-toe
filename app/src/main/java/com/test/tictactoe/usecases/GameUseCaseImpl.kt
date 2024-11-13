package com.test.tictactoe.usecases

import com.test.tictactoe.objects.Player
import com.test.tictactoe.objects.SquareState
import com.test.tictactoe.objects.WinnerState
import com.test.tictactoe.states.GameState

class GameUseCaseImpl : GameUseCase {
    override fun makeMove(state: GameState, row: Int, col: Int): GameState {
        if (state.winner != null || state.board[row][col] != SquareState.NONE) {
            return state
        }
        val newBoard = state.board.mapIndexed { r, rowList ->
            rowList.mapIndexed { c, cell ->
                if (r == row && c == col) {
                    if (state.currentPlayer == Player.X) SquareState.X else SquareState.O
                } else {
                    cell
                }
            }
        }
        val newPlayer = if (state.currentPlayer == Player.X) Player.O else Player.X
        val winner = checkWinner(newBoard)

        return state.copy(
            board = newBoard,
            currentPlayer = newPlayer,
            winner = winner
        )
    }

    override fun resetGame(): GameState {
        return GameState()
    }

    private fun checkWinner(board: List<List<SquareState>>): WinnerState? {
        // Check rows
        for (row in board) {
            checkConsecutive(row)?.let { return WinnerState.valueOf(it.name) }
        }

        // Check columns
        for (col in board[0].indices) {
            val column = board.map { it[col] }
            checkConsecutive(column)?.let { return WinnerState.valueOf(it.name) }
        }

        // Check diagonals
        val diagonal1 = board.indices.map { board[it][it] }
        checkConsecutive(diagonal1)?.let { return WinnerState.valueOf(it.name) }

        val diagonal2 = board.indices.map { board[it][board.size - 1 - it] }
        checkConsecutive(diagonal2)?.let { return WinnerState.valueOf(it.name) }

        // Check for draw
        val isDraw = board.all { row -> row.all { it == SquareState.X || it == SquareState.O } }
        return if (isDraw) {
            WinnerState.DRAW
        } else {
            null // Game ongoing
        }
    }

    private fun checkConsecutive(list: List<SquareState>): SquareState? {
        return if (list.contains(SquareState.NONE)) {
            null
        } else {
            list.windowed(size = 3)
                .find { window -> window.all { it == window.first() } }
                ?.first()
        }
    }
}
