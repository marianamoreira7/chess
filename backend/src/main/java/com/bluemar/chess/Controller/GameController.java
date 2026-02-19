package com.bluemar.chess.Controller;

import com.bluemar.chess.Domain.Game;
import com.bluemar.chess.Service.GameService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public Game createGame() {
        int id = gameService.createGame();
        return gameService.getGame(id);
    }

    @GetMapping("/{id}")
    public Game getGame(@PathVariable String id) {
        return gameService.getGame(Integer.parseInt(id));
    }

    public record MoveRequest(String from, String to) {}
    @PostMapping("/{id}/move")
    public Game movePiece(@PathVariable("id") String id, @RequestBody MoveRequest request) {
        return gameService.movePiece(Integer.parseInt(id), request.from(), request.to());
    }

    @GetMapping("/{id}/moves")
    public List<int[]> getPossibleMovements(@PathVariable String id, @RequestParam String from) {
        return gameService.getPossibleMovements(Integer.parseInt(id), from);
    }

    @GetMapping("/{id}/moves/back")
        public Game getGamePast(@PathVariable String id) {
        return gameService.getGame(Integer.parseInt(id));
    }
}
