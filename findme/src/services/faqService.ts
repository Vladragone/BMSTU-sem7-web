import api from "../api/axiosInstance";

export interface Faq {
  id: number;
  question: string;
  answer: string;
  userId: number;
}

export const getFaqs = async (): Promise<Faq[]> => {
  const { data } = await api.get<Faq[]>("/faqs");
  return data;
};

export const addFaq = async (faq: Omit<Faq, "id">): Promise<Faq> => {
  const { data } = await api.post<Faq>("/faqs", faq);
  return data;
};

export const getFaqById = async (id: number): Promise<Faq> => {
  const { data } = await api.get<Faq>(`/faqs/${id}`);
  return data;
};

export const updateFaq = async (
  id: number,
  updates: Partial<Faq>
): Promise<Faq> => {
  const { data } = await api.patch<Faq>(`/faqs/${id}`, updates);
  return data;
};

export const deleteFaq = async (id: number): Promise<void> => {
  await api.delete(`/faqs/${id}`);
};
