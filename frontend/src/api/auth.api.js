import apiClient from "./apiClient";



export const authApi = {
  login: (username, password) =>
    apiClient.post('/auth/login', { username, password }).then((r) => r.data),

  refresh: (refreshToken) =>
    apiClient.post('/auth/refresh', { refreshToken }).then((r) => r.data),

  logout: (refreshToken) =>
    apiClient.post('/auth/logout', { refreshToken }),

  me: () => apiClient.get('/auth/me').then((r) => r.data),

  changePassword: (currentPassword, newPassword) =>
    apiClient.put('/auth/password', { currentPassword, newPassword }),
};