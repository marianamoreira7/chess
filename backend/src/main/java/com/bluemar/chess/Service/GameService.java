package com.bluemar.chess.Service;

import com.bluemar.chess.Domain.Game;
import com.bluemar.chess.Domain.Move;
import com.bluemar.chess.Exception.InvalidMoveException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class GameService {

    private final HashMap<Integer, Game> games = new HashMap<>();

    public int createGame(){
        UUID id = UUID.randomUUID();
        int i = 1;
        Game game = new Game(i);
        game.setId(i);
        games.put(i, game);
        return i;
    }

    public Game getGame(int id){
        return games.get(id);
    }

    public Game movePiece(int gameId, String from, String to) {
        Game game = games.get(gameId);
        if (game == null) throw new InvalidMoveException("Game not found with ID " + gameId);
        if(!game.movePiece(game, from, to))throw new InvalidMoveException("Invalid Movement");
        return getGame(gameId);
    }

    public List<int[]> getPossibleMovements(int id, String from) {
        Move move = new Move();
        return move.validMoves(getGame(id), from);
    }
}
