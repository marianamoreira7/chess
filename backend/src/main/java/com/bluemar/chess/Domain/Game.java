package com.bluemar.chess.Domain;

import java.util.ArrayList;

public class Game {
    private int id;
    private Board board;
    private Color currentTurn;
    private ArrayList<int[][]> movements;

    public Game(int id) {
        this.id = id;
        this.board = new Board();
        this.currentTurn = Color.WHITE;
        this.movements = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {this.id = id;}

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Color getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(Color currentTurn) {
        this.currentTurn = currentTurn;
    }

    public Color getTurn() {
        return this.currentTurn;
    }

    public void switchTurn() {setCurrentTurn((currentTurn == Color.WHITE) ? Color.BLACK : Color.WHITE);}

    public Boolean movePiece(Game game, String from, String to) {
        Move move = new Move();
        return move.movePiece(game, from, to);
    }

    public ArrayList<int[][]> getMovements() {
        return movements;
    }

    public void setMovements(ArrayList<int[][]> movements) {
        this.movements = movements;
    }
}
