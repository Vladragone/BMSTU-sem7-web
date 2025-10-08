import api from "../api/axiosInstance";

export const registerUser = async (
  username: string,
  email: string,
  password: string
): Promise<void> => {
  try {
    await api.post("/users", { username, email, password });
  } catch (error: any) {
    const status = error?.response?.status;
    let message = "Ошибка регистрации";

    switch (status) {
      case 400:
        message = "Некорректные данные — проверьте поля";
        break;
      case 409:
        message = "Имя пользователя или почта уже заняты";
        break;
      case 500:
        message = "Ошибка на сервере. Попробуйте позже";
        break;
      case 0:
        message = "Сервер недоступен. Проверьте соединение";
        break;
      default:
        message = error?.response?.data?.message || "Неизвестная ошибка";
    }

    throw { status, message };
  }
};
