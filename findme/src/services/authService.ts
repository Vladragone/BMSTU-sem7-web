import api from "../api/axiosInstance";

interface LoginResponse {
  token: string;
}

export const loginUser = async (
  username: string,
  password: string
): Promise<LoginResponse> => {
  try {
    const { data } = await api.post<LoginResponse>("/tokens", {
      username,
      password,
    });
    return data;
  } catch (error: any) {
    const status = error?.response?.status;
    let message = "Ошибка входа";

    if (status === 401) message = "Неверный логин или пароль";
    if (status === 500) message = "Ошибка на сервере. Попробуйте позже.";
    if (status === 0) message = "Сервер недоступен";

    throw { status, message };
  }
};
