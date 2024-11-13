package com.test.tictactoe

import com.test.tictactoe.objects.Player
import com.test.tictactoe.objects.SquareState
import com.test.tictactoe.objects.WinnerState
import com.test.tictactoe.states.GameState
import com.test.tictactoe.usecases.GameUseCaseImpl
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

class GameUnitTest {

    private lateinit var gameUseCase: GameUseCaseImpl

    @Before
    fun setup() {
        gameUseCase = GameUseCaseImpl()
    }

    @Test
    fun playerXGoesFirst() {
        val gameUseCase = GameUseCaseImpl()
        val result = gameUseCase.resetGame()
        assertThat("Is PlayerX first", result.currentPlayer == Player.X)
    }

    @Test
    fun playerCannotPlayOnPlayedPosition() {
        val board = List(3) { MutableList(3) { SquareState.NONE } }
        board[1][1] = SquareState.X
        val initialState = GameState(board = board)

        val result = gameUseCase.makeMove(initialState, 1, 1)

        assertThat("Player cannot play on played position", initialState == result)
    }

    @Test
    fun PlayerXPlacesXOnBoard() {
        val initialState = GameState()
        val result = gameUseCase.makeMove(initialState, 0, 0)

        assertThat("Player X places X on Board", result.board[0][0] == SquareState.X)
        assertThat("Winner is still null", result.winner == null)
    }

    @Test
    fun PlayerOPlacesOOnBoard() {
        val initialState = GameState(currentPlayer = Player.O)
        val result = gameUseCase.makeMove(initialState, 1, 1)

        assertThat("Player O places O on Board", result.board[1][1] == SquareState.O)
        assertThat("Winner is still null", result.winner == null)
    }

    @Test
    fun playersAlternate() {
        var currentState = GameState() // Starts with Player X

        // First move - X at (0,0)
        currentState = gameUseCase.makeMove(currentState, 0, 0)
        assertThat("Board got X", currentState.board[0][0] == SquareState.X)
        assertThat("Player alternated to O", currentState.currentPlayer == Player.O)

        // Second move - O at (0,1)
        currentState = gameUseCase.makeMove(currentState, 0, 1)
        assertThat("Board got O", currentState.board[0][1] == SquareState.O)
        assertThat("Player alternated to X", currentState.currentPlayer == Player.X)

        // Third move - X at (1,0)
        currentState = gameUseCase.makeMove(currentState, 1, 0)
        assertThat("Board got X", currentState.board[1][0] == SquareState.X)
        assertThat("Player alternated to O", currentState.currentPlayer == Player.O)

        // Fourth move - O at (1,1)
        currentState = gameUseCase.makeMove(currentState, 1, 1)
        assertThat("Board got O", currentState.board[0][1] == SquareState.O)
        assertThat("Player alternated to X", currentState.currentPlayer == Player.X)
    }

    @Test
    fun winnerByDiagonal() {
        var board = List(3) { MutableList(3) { SquareState.NONE } }
        board[0][0] = SquareState.X
        board[1][1] = SquareState.X
        var state = GameState(board = board, currentPlayer = Player.X)

        var result = gameUseCase.makeMove(state, 2, 2)

        assertThat("Winner by diagonal", WinnerState.X == result.winner)

        board = List(3) { MutableList(3) { SquareState.NONE } }
        board[0][2] = SquareState.O
        board[1][1] = SquareState.O
        state = GameState(board = board, currentPlayer = Player.O)

        result = gameUseCase.makeMove(state, 2, 0)

        assertThat("Winner by anti diagonal", WinnerState.O == result.winner)
    }

    @Test
    fun winnerByHorizontal() {
        val board = List(3) { MutableList(3) { SquareState.NONE } }
        board[0][0] = SquareState.X
        board[0][1] = SquareState.X
        val state = GameState(board = board, currentPlayer = Player.X)

        val result = gameUseCase.makeMove(state, 0, 2)

        assertThat("Winner by horizontal", WinnerState.X == result.winner)
    }

    @Test
    fun winnerByVertical() {
        val board = List(3) { MutableList(3) { SquareState.NONE } }
        board[0][0] = SquareState.O
        board[1][0] = SquareState.O
        val state = GameState(board = board, currentPlayer = Player.O)

        val result = gameUseCase.makeMove(state, 2, 0)

        assertThat("Winner by vertical", WinnerState.O == result.winner)
    }

    @Test
    fun winnerByVerticalBug() {
        val board = List(3) { MutableList(3) { SquareState.NONE } }
        board[0][0] = SquareState.X
        board[0][1] = SquareState.O
        board[1][0] = SquareState.X
        board[1][1] = SquareState.O
        // board[1][1] = SquareState.O Move was not noticed as winning
        val state = GameState(board = board, currentPlayer = Player.X)

        val result = gameUseCase.makeMove(state, 2, 0)

        assertThat("X is winner by vertical", WinnerState.X == result.winner)
    }

    @Test
    fun draw() {
        val board = List(3) { MutableList(3) { SquareState.NONE } }

//        board[0][0] = SquareState.X This is the last move
        board[0][1] = SquareState.X
        board[0][2] = SquareState.O
        board[1][0] = SquareState.O
        board[1][1] = SquareState.X
        board[1][2] = SquareState.X
        board[2][0] = SquareState.X
        board[2][1] = SquareState.O
        board[2][2] = SquareState.O

        val state = GameState(board = board, currentPlayer = Player.X)

        val result = gameUseCase.makeMove(state, 0, 0)

        assertThat("draw when squares are filled", WinnerState.DRAW == result.winner)
    }

    @Test
    fun resetGameWhenFinished() {
        val gameUseCase = GameUseCaseImpl()

        val result = gameUseCase.resetGame()

        assertThat("ResetGame gives a new instance of GameState", result == GameState())
        assertThat("Player X begins", result.currentPlayer == Player.X)
        assertThat("result contains no winner", result.winner == null)
        result.board.forEach { row ->
            row.forEach { cell ->
                assertThat("All cells are empty", cell == SquareState.NONE)
            }
        }
    }
}
