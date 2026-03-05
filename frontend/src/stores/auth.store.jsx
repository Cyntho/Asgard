import { create } from 'zustand';
import { persist } from 'zustand/middleware';

export const useAuthStore = create(
  persist(
    (set, get) => ({
      accessToken: null,
      refreshToken: null,
      user: null,

      setAuth: (accessToken, refreshToken, user) =>
        set({ accessToken, refreshToken, user }),

      setTokens: (accessToken, refreshToken) =>
        set({ accessToken, refreshToken }),

      logout: () =>
        set({ accessToken: null, refreshToken: null, user: null }),

      isAuthenticated: () => !!get().accessToken,

      isAdmin: () => get().user?.role === 'admin',

      canWrite: () => get().user?.role === 'admin',
    }),
    { name: 'ts6-auth' },
  ),
);