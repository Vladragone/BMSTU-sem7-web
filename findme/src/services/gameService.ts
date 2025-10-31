import api from "../api/axiosInstance";

export interface GameLocation {
  id: number;
  lat: number;
  lng: number;
}

export const getRandomLocation = async (groupName: string): Promise<GameLocation> => {
  const token = localStorage.getItem("token");
  const { data } = await api.get<GameLocation>(
    `/locations/random`,
    {
      headers: { Authorization: `Bearer ${token}` },
      params: { groupName },
    }
  );
  return data;
};
