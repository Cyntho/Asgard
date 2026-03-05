import { create } from 'zustand';
import { persist } from 'zustand/middleware';


function applyUITheme(theme) {
  if (theme === 'dark') {
    document.documentElement.classList.add('dark');
  } else {
    document.documentElement.classList.remove('dark');
  }
}

export const useUiStore = create(
  persist(
    (set, get) => ({
      menuCollapsed: false,
      theme: 'dark',
      toggleSidebar: () =>
        set({ menuCollapsed: !get().menuCollapsed }),
      setMenuCollapsed: (collapsed) =>
        set({ menuCollapsed: collapsed }),
      toggleTheme: () => {
        const next = get().theme === 'dark' ? 'light' : 'dark';
        set({ theme: next });
        applyUITheme(next);
      },
      setTheme: (theme) => {
        set({ theme });
        applyUITheme(theme);
      },
    }),
    {
      name: 'ts6-ui',
      onRehydrateStorage: () => (state) => {
        if (state?.theme) {
          applyUITheme(state.theme);
        }
      },
    },
  ),
);



