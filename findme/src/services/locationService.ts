import api from "../api/axiosInstance";

export const getLocationNames = async (): Promise<string[]> => {
  try {
    const { data } = await api.get<string[]>("/locations/names");
    return data;
  } catch (error: any) {
    console.error("Ошибка при получении списка локаций:", error);
    throw error;
  }
};
