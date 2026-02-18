package com.bluemar.chess.Domain;

public class Piece {
    private PieceType type;
    private Color color;

    public Piece(PieceType type, Color color) {
        this.type = type;
        this.color = color;
    }

    public PieceType getType() {
        return this.type;
    }

    public Color getColor() {
        return this.color;
    }
}
