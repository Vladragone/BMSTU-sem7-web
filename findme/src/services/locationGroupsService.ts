import api from "../api/axiosInstance";

export interface LocationGroup {
  id: number;
  name: string;
}

export const getLocationGroups = async (): Promise<LocationGroup[]> => {
  const token = localStorage.getItem("token");
  const { data } = await api.get<LocationGroup[]>("/location-groups", {
    headers: { Authorization: `Bearer ${token}` },
  });
  return data;
};
