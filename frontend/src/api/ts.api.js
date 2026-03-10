import apiClient from "./apiClient";

export const tsApi = {
  loadAll: () =>
    apiClient.get('/ts/configurations').then((r) => r.data ?? []),

  loadById: (id) =>
    apiClient.get(`/ts/configurations/${id}`).then((r) => r.data),

};