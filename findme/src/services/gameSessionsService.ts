import api from "../api/axiosInstance";

export interface GameSession {
  id: number;
  userId: number;
  locationGroupId: number;
  totalScore: number;
  totalRounds: number;
}

export interface GameSessionRequest {
  userId: number;
  locationGroupId: number;
  totalRounds: number;
}

const getRoundsCount = async (locationGroupId: number): Promise<number> => {
  const token = localStorage.getItem("token");
  const { data } = await api.get(`/locations/group/${locationGroupId}`, {
    headers: { Authorization: `Bearer ${token}` },
  });
  return Array.isArray(data) ? data.length : 0;
};

export const createGameSession = async (
  request: Omit<GameSessionRequest, "totalRounds">
): Promise<GameSession> => {
  const token = localStorage.getItem("token");
  const totalRounds = await getRoundsCount(request.locationGroupId);
  const { data } = await api.post<GameSession>(
    "/gamesessions",
    { ...request, totalRounds },
    { headers: { Authorization: `Bearer ${token}` } }
  );
  return data;
};

export const getGameSessionById = async (id: number): Promise<GameSession> => {
  const token = localStorage.getItem("token");
  const { data } = await api.get<GameSession>(`/gamesessions/${id}`, {
    headers: { Authorization: `Bearer ${token}` },
  });
  return data;
};
