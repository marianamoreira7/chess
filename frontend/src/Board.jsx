import React, { useState } from "react";
import { createGame, movePiece, getPossibleMoves } from "./api";

const PIECES = {
    "PAWN_WHITE": "♙", "ROOK_WHITE": "♖", "KNIGHT_WHITE": "♘",
    "BISHOP_WHITE": "♗", "QUEEN_WHITE": "♕", "KING_WHITE": "♔",
    "PAWN_BLACK": "♟", "ROOK_BLACK": "♜", "KNIGHT_BLACK": "♞",
    "BISHOP_BLACK": "♝", "QUEEN_BLACK": "♛", "KING_BLACK": "♚"
};

const Board = () => {
    const [game, setGame] = useState(null);
    const [selectedSquare, setSelectedSquare] = useState(null);
    const [possibleMoves, setPossibleMoves] = useState([]);

    const handleCreateGame = async () => {
        const newGame = await createGame();
        setGame(newGame);
        setSelectedSquare(null);
        setPossibleMoves([]);
    };

    const convertToChessNotation = (row, col) => {
        const columns = ['A','B','C','D','E','F','G','H'];
        return `${columns[col]}${8 - row}`;
    };

    const isPossibleMove = (row, col) => {
        return possibleMoves.some(
            move => move[0] === row && move[1] === col
        );
    };

    const handleSquareClick = async (row, col) => {
        if (!game) return;

        const clickedPos = convertToChessNotation(row, col);
        const clickedPiece = game.board.board[row][col];

        // 🔵 NADA SELECIONADO AINDA
        if (!selectedSquare) {
            if (clickedPiece && clickedPiece.color === game.currentTurn) {
                try {
                    const moves = await getPossibleMoves(game.id, clickedPos);
                    setSelectedSquare(clickedPos);
                    setPossibleMoves(moves);
                } catch (err) {
                    alert(err.message);
                }
            }
            return;
        }

        // 🟡 CLICOU NA MESMA CASA → DESELECIONA
        if (selectedSquare === clickedPos) {
            setSelectedSquare(null);
            setPossibleMoves([]);
            return;
        }

        // 🟣 CLICOU EM OUTRA PEÇA DA MESMA COR → TROCA SELEÇÃO
        if (clickedPiece && clickedPiece.color === game.currentTurn) {
            try {
                const moves = await getPossibleMoves(game.id, clickedPos);
                setSelectedSquare(clickedPos);
                setPossibleMoves(moves);
            } catch (err) {
                alert(err.message);
            }
            return;
        }

        // 🟢 CLICOU EM CASA POSSÍVEL → MOVE
        const isMove = possibleMoves.some(
            move => move[0] === row && move[1] === col
        );

        if (isMove) {
            try {
                const updatedGame = await movePiece(game.id, selectedSquare, clickedPos);
                setGame(updatedGame);
            } catch (err) {
                alert(err.message);
            }
        }

        // 🔴 QUALQUER OUTRO CLIQUE → LIMPA SELEÇÃO
        setSelectedSquare(null);
        setPossibleMoves([]);
    };


    return (
        <div style={{ textAlign: "center", fontFamily: "Arial" }}>
            <button
                onClick={handleCreateGame}
                style={{ padding: "10px 20px", marginBottom: "20px" }}
            >
                Novo Jogo
            </button>

            {game && (
                <div>
                    <h3>
                        Turno: {game.currentTurn} | Selecionado: {selectedSquare || "Nenhum"}
                    </h3>

                    <div style={{ display: "inline-block", border: "5px solid #333" }}>
                        {game.board.board.map((row, rowIndex) => (
                            <div key={rowIndex} style={{ display: "flex" }}>
                                {row.map((cell, colIndex) => {

                                    const chessPos = convertToChessNotation(rowIndex, colIndex);
                                    const isSelected = selectedSquare === chessPos;
                                    const isMove = isPossibleMove(rowIndex, colIndex);

                                    const key = cell ? `${cell.type}_${cell.color}` : null;

                                    let backgroundColor =
                                        (rowIndex + colIndex) % 2 === 0
                                            ? "#f0d9b5"
                                            : "#b58863";

                                    if (isSelected) backgroundColor = "#ffeb3b";      // amarelo
                                    if (isMove) backgroundColor = "#4caf50";         // verde

                                    return (
                                        <div
                                            key={colIndex}
                                            onClick={() => handleSquareClick(rowIndex, colIndex)}
                                            style={{
                                                width: 60,
                                                height: 60,
                                                display: "flex",
                                                alignItems: "center",
                                                justifyContent: "center",
                                                fontSize: 40,
                                                cursor: "pointer",
                                                backgroundColor,
                                                color: cell?.color === "WHITE" ? "#fff" : "#000",
                                                textShadow: cell?.color === "WHITE"
                                                    ? "1px 1px 2px #000"
                                                    : "none",
                                                transition: "background-color 0.2s"
                                            }}
                                        >
                                            {key ? PIECES[key] : ""}
                                        </div>
                                    );
                                })}
                            </div>
                        ))}
                    </div>
                </div>
            )}
        </div>
    );
};

export default Board;
