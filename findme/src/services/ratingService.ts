import api from "../api/axiosInstance";

export interface RatingUser {
  username: string;
}

export interface RatingProfile {
  regDate: string;
  score: number;
  gameNum: number;
  user?: RatingUser;
}

export interface RatingResponse {
  top: RatingProfile[];
  yourRank: number;
  sortBy: "points" | "games";
}

export const getRating = async (
  sortBy: "points" | "games",
  limit: number = 3
): Promise<RatingResponse> => {
  try {
    const token = localStorage.getItem("token");
    if (!token) throw new Error("Нет токена");

    const { data } = await api.get<RatingResponse>("/ratings", {
      headers: { Authorization: `Bearer ${token}` },
      params: { sortBy, limit },
    });

    return data;
  } catch (error: any) {
    const status = error?.response?.status;
    let message = "Ошибка при загрузке рейтинга";

    if (status === 401) message = "Неавторизован. Войдите заново.";
    if (status === 500) message = "Ошибка на сервере.";
    if (status === 0) message = "Сервер недоступен.";

    throw { status, message };
  }
};
