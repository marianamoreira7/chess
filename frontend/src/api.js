const API_URL = "/api/games";

export async function createGame() {
    const res = await fetch(API_URL, { method: "POST" });
    if (!res.ok) throw new Error("Erro ao criar jogo");
    return await res.json();
}

export async function movePiece(gameId, from, to) {
    const res = await fetch(`${API_URL}/${gameId}/move`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ from, to })
    });

    if (!res.ok) {
        const errorData = await res.json();
        throw new Error(errorData.error || "Movimento inválido");
    }

    return await res.json();
}

export async function getPossibleMoves(gameId, from) {
    const res = await fetch(`${API_URL}/${gameId}/moves?from=${from}`);

    if (!res.ok) {
        throw new Error("Erro ao buscar movimentos possíveis");
    }

    return await res.json();
}
