package com.example.tictaktoeproject2

import kotlin.random.Random

fun checkWinner(board: Array<Player?>): Pair<Player?, String> {
    // Checking rows
    for (i in 0..2) {
        if (board[i * 3] != null && 
            board[i * 3] == board[i * 3 + 1] && 
            board[i * 3 + 1] == board[i * 3 + 2]) {
            return Pair(board[i * 3], "Row ${i + 1}")
        }
    }
    
    // Checking columns
    for (i in 0..2) {
        if (board[i] != null && 
            board[i] == board[i + 3] && 
            board[i + 3] == board[i + 6]) {
            return Pair(board[i], "Column ${i + 1}")
        }
    }
    
    // Checking diagonals
    if (board[0] != null && board[0] == board[4] && board[4] == board[8]) {
        return Pair(board[0], "Diagonal")
    }
    if (board[2] != null && board[2] == board[4] && board[4] == board[6]) {
        return Pair(board[2], "Diagonal")
    }
    
    // Checking for draw
    if (board.all { it != null }) {
        return Pair(null, "Draw")
    }
    
    return Pair(null, "")
}

// Easy difficulty
fun getRandomMove(board: Array<Player?>): Int {
    val emptySpots = mutableListOf<Int>()
    for (i in board.indices) {
        if (board[i] == null) {
            emptySpots.add(i)
        }
    }
    return if (emptySpots.isNotEmpty()) {
        emptySpots[Random.nextInt(emptySpots.size)]
    } else {
        -1
    }
}

// Hard difficulty
fun getBestMove(board: Array<Player?>): Int {
    var bestScore = -1000
    var bestMove = -1
    
    for (i in board.indices) {
        if (board[i] == null) {
            board[i] = Player.O
            val score = minimax(board, 0, false, -1000, 1000)
            board[i] = null
            
            if (score > bestScore) {
                bestScore = score
                bestMove = i
            }
        }
    }
    return bestMove
}

fun minimax(board: Array<Player?>, depth: Int, isMaximizing: Boolean, alpha: Int, beta: Int): Int {
    val result = checkWinner(board)
    
    when (result.first) {
        Player.O -> return 10 - depth
        Player.X -> return depth - 10
        null -> if (result.second == "Draw") return 0
    }
    
    var alphaVar = alpha
    var betaVar = beta
    
    if (isMaximizing) {
        var bestScore = -1000
        for (i in board.indices) {
            if (board[i] == null) {
                board[i] = Player.O //Since Computer is always O
                val score = minimax(board, depth + 1, false, alphaVar, betaVar)
                board[i] = null
                bestScore = maxOf(bestScore, score)
                alphaVar = maxOf(alphaVar, score)
                if (betaVar <= alphaVar) break
            }
        }
        return bestScore
    } else {
        var bestScore = 1000
        for (i in board.indices) {
            if (board[i] == null) {
                board[i] = Player.X
                val score = minimax(board, depth + 1, true, alphaVar, betaVar)
                board[i] = null
                bestScore = minOf(bestScore, score)
                betaVar = minOf(betaVar, score)
                if (betaVar <= alphaVar) break
            }
        }
        return bestScore
    }
}

// Medium difficulty
fun getMediumMove(board: Array<Player?>): Int {
    //Implement medium difficulty logic
}

