import apiClient from "./apiClient";

export const newsApi = {
  loadAll: () =>
    apiClient.get('/news').then((r) => r.data ?? []),

  loadById: (id) =>
    apiClient.get(`/news/${id}`).then((r) => r.data),

};