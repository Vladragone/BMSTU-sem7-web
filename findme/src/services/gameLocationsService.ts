import api from "../api/axiosInstance";

export interface GameLocation {
  id: number;
  lat: number;
  lng: number;
  groupId: number;
}

export const getLocationsByGroup = async (groupId: number): Promise<GameLocation[]> => {
  const token = localStorage.getItem("token");
  const { data } = await api.get<GameLocation[]>(
    `/locations/group/${groupId}`,
    { headers: { Authorization: `Bearer ${token}` } }
  );
  return data;
};
