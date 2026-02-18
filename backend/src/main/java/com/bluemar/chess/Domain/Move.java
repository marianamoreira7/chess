package com.bluemar.chess.Domain;

import com.bluemar.chess.Exception.InvalidMoveException;

import java.util.ArrayList;
import java.util.List;

public class Move {

    private ArrayList<int[][]> moves;

    public ArrayList<int[][]> getMoves() {
        return moves;
    }
    public void setMoves(ArrayList<int[][]> moves) {
        this.moves = moves;
    }

    public Move() {
        this.moves = new ArrayList<>();
    }

    public int[] chessNotationToIndice(String indice) {
        indice = indice.toUpperCase();

        char colChar = indice.charAt(0);
        int rowChar = Character.getNumericValue(indice.charAt(1));

        int col = colChar - 'A';
        int row = 8 - rowChar;

        return new int[]{row, col};
    }

    public int[][] chessNotationToIndices(String from, String to) {
        return new int[][]{
                chessNotationToIndice(from),
                chessNotationToIndice(to)
        };
    }

    private boolean isInsideBoard(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    private boolean hasEnemyPiece(Board board, int[] from, int[] to) {
        Piece target = board.getPiece(to[0], to[1]);
        Piece source = board.getPiece(from[0], from[1]);

        if (target == null || source == null) return false;

        return target.getColor() != source.getColor();
    }

    private boolean validatePawnMove(Color color, int[] from, int[] to, Board board) {

        int direction = (color == Color.WHITE) ? -1 : 1;
        int startRow = (color == Color.WHITE) ? 6 : 1;

        int rowDiff = to[0] - from[0];
        int colDiff = Math.abs(to[1] - from[1]);

        Piece target = board.getPiece(to[0], to[1]);

        if (colDiff == 0 && rowDiff == direction) {
            return target == null;
        }

        if (colDiff == 0 && from[0] == startRow && rowDiff == 2 * direction) {
            int intermediateRow = from[0] + direction;
            return target == null && board.getPiece(intermediateRow, from[1]) == null;
        }

        if (colDiff == 1 && rowDiff == direction) {
            return target != null && hasEnemyPiece(board, from, to);
        }

        return false;
    }

    private boolean isPathClear(Board board, int[] from, int[] to) {

        int stepRow = Integer.compare(to[0], from[0]);
        int stepCol = Integer.compare(to[1], from[1]);

        int currentRow = from[0] + stepRow;
        int currentCol = from[1] + stepCol;

        while (currentRow != to[0] || currentCol != to[1]) {
            if (board.getPiece(currentRow, currentCol) != null)
                return false;

            currentRow += stepRow;
            currentCol += stepCol;
        }

        return true;
    }

    public boolean validInput(Game game, int[][] indices) {

        int fromRow = indices[0][0];
        int fromCol = indices[0][1];
        int toRow = indices[1][0];
        int toCol = indices[1][1];

        if (!isInsideBoard(fromRow, fromCol) || !isInsideBoard(toRow, toCol))
            return false;

        Piece piece = game.getBoard().getPiece(fromRow, fromCol);
        Piece target = game.getBoard().getPiece(toRow, toCol);

        if (piece == null)
            return false;

        if (piece.getColor() != game.getCurrentTurn())
            return false;

        if (target != null && target.getColor() == piece.getColor())
            return false;

        return true;
    }

    public boolean isValid(Game game, Piece piece, int[][] indices) {

        int fromRow = indices[0][0];
        int fromCol = indices[0][1];
        int toRow = indices[1][0];
        int toCol = indices[1][1];

        int dx = Math.abs(toRow - fromRow);
        int dy = Math.abs(toCol - fromCol);

        return switch (piece.getType()) {

            case KNIGHT -> (dx == 2 && dy == 1) || (dx == 1 && dy == 2);

            case BISHOP -> (dx == dy) && isPathClear(game.getBoard(), indices[0], indices[1]);

            case ROOK -> (dx == 0 || dy == 0) && isPathClear(game.getBoard(), indices[0], indices[1]);

            case QUEEN -> (dx == dy || dx == 0 || dy == 0) && isPathClear(game.getBoard(), indices[0], indices[1]);

            case KING -> dx <= 1 && dy <= 1;

            case PAWN -> validatePawnMove(piece.getColor(), indices[0], indices[1], game.getBoard());

            default -> false;
        };
    }

    public Boolean movePiece(Game game, String from, String to) {

        int[][] moveIndices = chessNotationToIndices(from, to);

        Board board = game.getBoard();
        Piece piece = board.getPiece(moveIndices[0][0], moveIndices[0][1]);

        if (!validInput(game, moveIndices) || !isValid(game, piece, moveIndices))
            return false;

        board.setPiece(moveIndices[1], piece);
        board.setPiece(moveIndices[0], null);
        moves.add(moveIndices);

        game.switchTurn();

        return true;
    }

    public List<int[]> validMoves(Game game, String fromString) {
        int[] from = chessNotationToIndice(fromString);
        Piece piece = game.getBoard().getPiece(from[0], from[1]);
        if (piece == null || piece.getColor() != game.getCurrentTurn()) {
            throw new InvalidMoveException("Invalid move from " + fromString + " to " + game.getCurrentTurn());
        }

        List<int []> moves = new  ArrayList<>();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int[][] moveIndices = new int[][]{from, new int[]{i, j}};
                if (validInput(game, moveIndices) && isValid(game, piece, moveIndices)) {
                    moves.add(new int[] {i,j});
                }
            }
        }
        return moves;
    }
}
