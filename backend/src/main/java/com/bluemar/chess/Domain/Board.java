package com.bluemar.chess.Domain;

public class Board {

    private Piece[][] board;

    public Board() {
        this.board = new Piece[8][8];
        initialize();
    }

    public void initialize() {
        board = new Piece[8][8];

        //Set all black Pawns
        for (int i = 0; i < board.length; i++) {
            board[1][i]=new Piece(PieceType.PAWN, Color.BLACK);
        }

        //Set all white Pawns
        for (int i = 0; i < board.length; i++) {
            board[6][i]=new Piece(PieceType.PAWN, Color.WHITE);
        }

        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j]=null;
            }
        }

        board[0][0] = new Piece(PieceType.ROOK, Color.BLACK);
        board[0][7] = new Piece(PieceType.ROOK, Color.BLACK);

        board[0][1] = new Piece(PieceType.KNIGHT, Color.BLACK);
        board[0][6] = new Piece(PieceType.KNIGHT, Color.BLACK);

        board[0][2] = new Piece(PieceType.BISHOP, Color.BLACK);
        board[0][5] = new Piece(PieceType.BISHOP, Color.BLACK);

        board[0][3] = new Piece(PieceType.QUEEN, Color.BLACK);
        board[0][4] = new Piece(PieceType.KING, Color.BLACK);


        board[7][0] = new Piece(PieceType.ROOK, Color.WHITE);
        board[7][7] = new Piece(PieceType.ROOK, Color.WHITE);

        board[7][1] = new Piece(PieceType.KNIGHT, Color.WHITE);
        board[7][6] = new Piece(PieceType.KNIGHT, Color.WHITE);

        board[7][2] = new Piece(PieceType.BISHOP, Color.WHITE);
        board[7][5] = new Piece(PieceType.BISHOP, Color.WHITE);

        board[7][3] = new Piece(PieceType.QUEEN, Color.WHITE);
        board[7][4] = new Piece(PieceType.KING, Color.WHITE);

    }

        //   0  1
        //8  ♜ ♞ ♝ ♛ ♚ ♝ ♞ ♜  0 ← Pretas
        //7  ♟ ♟ ♟ ♟ ♟ ♟ ♟ ♟  1
        //6  . . . . . . . .
        //5  . . . . . . . .
        //4  . . . . . . . .
        //3  . . . . . . . .
        //2  ♙ ♙ ♙ ♙ ♙ ♙ ♙ ♙
        //1  ♖ ♘ ♗ ♕ ♔ ♗ ♘ ♖   ← Brancas
        //   a b c d e f g h
        //   [linha][coluna]


    public Piece[][] getBoard() {
        return board;
    }

    public Piece getPiece(int row, int col) {
        return board[row][col];
    }

    public Piece getPiece(int[] indices) {
        return board[indices[0]][indices[1]];
    }

    public void setPiece(int row, int col, Piece piece) {
        board[row][col]=piece;
    }

    public void setPiece(int[] indices, Piece piece) {
       board[indices[0]][indices[1]]=piece;
    }
}
