import api from "../api/axiosInstance";

export interface RatingUser {
  username: string;
  score: number;
  gameNum: number;
  rank: number;
}

export interface RatingResponse {
  topUsers: RatingUser[];
  currentUserRank: number;
  sortBy: "score" | "games";
}

export const getRating = async (
  sortBy: "score" | "games",
  limit: number = 3
): Promise<RatingResponse> => {
  try {
    const token = localStorage.getItem("token");
    if (!token) throw new Error("Нет токена");

    const { data } = await api.get<RatingResponse>("/ratings", {
      headers: { Authorization: `Bearer ${token}` },
      params: { sortBy, limit },
    });

    return {
      topUsers: Array.isArray(data?.topUsers) ? data.topUsers : [],
      currentUserRank: data?.currentUserRank ?? 0,
      sortBy: data?.sortBy === "score" || data?.sortBy === "games" ? data.sortBy : sortBy,
    };
  } catch (error: any) {
    const status = error?.response?.status;
    let message = "Ошибка при загрузке рейтинга";

    if (status === 401) message = "Неавторизован. Войдите заново.";
    if (status === 500) message = "Ошибка на сервере.";
    if (status === 0) message = "Сервер недоступен.";

    throw { status, message };
  }
};
