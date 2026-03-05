import axios from "axios";
import { useAuthStore } from "@stores/auth.store.jsx"


const apiBaseUrl = import.meta.env.VITE_API_BASE_URL ?? "http://localhost:8080/api/v1";

const apiClient = axios.create({
  baseURL: apiBaseUrl,
  timeout: 10000,
});

apiClient.interceptors.request.use((config) => {
  const token = useAuthStore.getState().accessToken;
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

apiClient.interceptors.response.use(
  (response) => response,
  async (error) => {
    const original = error.config;
    if (error.response?.status === 401 && !original._retry) {
      original._retry = true;
      const { refreshToken, setTokens, logout } = useAuthStore.getState();
      if (refreshToken) {
        try {
          const res = await axios.post('/api/auth/refresh', { refreshToken });
          setTokens(res.data.accessToken, res.data.refreshToken);
          original.headers.Authorization = `Bearer ${res.data.accessToken}`;
          return apiClient(original);
        } catch {
          logout();
        }
      } else {
        logout();
      }
    }
    return Promise.reject(error);
  },
);

export default apiClient;
