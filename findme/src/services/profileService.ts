import api from "../api/axiosInstance";

export interface Profile {
  regDate: string;
  gameNum: number;
  score: number;
}

export const getProfile = async (): Promise<Profile> => {
  try {
    const token = localStorage.getItem("token");
    if (!token) throw new Error("Нет токена");

    const { data } = await api.get<Profile>("/profiles/me", {
      headers: { Authorization: `Bearer ${token}` },
    });

    return data;
  } catch (error: any) {
    const status = error?.response?.status;
    let message = "Ошибка загрузки профиля";

    if (status === 401) message = "Неавторизован. Войдите заново.";
    if (status === 500) message = "Ошибка на сервере.";
    if (status === 0) message = "Сервер недоступен.";

    throw { status, message };
  }
};
