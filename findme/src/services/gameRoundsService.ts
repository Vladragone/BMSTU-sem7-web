import api from "../api/axiosInstance";

export interface GameRoundRequest {
  sessionId: number;
  locationId: number;
  guessLat: number;
  guessLng: number;
  score: number;
  roundNumber: number;
}

export const createGameRound = async (round: GameRoundRequest): Promise<void> => {
  const token = localStorage.getItem("token");
  await api.post("/gamerounds", round, {
    headers: { Authorization: `Bearer ${token}` },
  });
};
