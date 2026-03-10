import { useMutation } from '@tanstack/react-query';
import { useAuthStore } from "../stores/auth.store";
import { useNavigate } from 'react-router-dom';
import { authApi } from '../api/auth.api';

export function useLogin() {
  const { setAuth } = useAuthStore();
  const navigate = useNavigate();

  return useMutation({
    mutationFn: ({ username, password } ) =>
      authApi.login(username, password),
    onSuccess: (data) => {
      setAuth(data.accessToken, data.refreshToken, data.user);
      navigate('/home');
    },
  });
}

export function useLogout() {
  const { refreshToken, logout } = useAuthStore();
  const navigate = useNavigate();

  return () => {
    if (refreshToken) authApi.logout(refreshToken).catch(() => {});
    logout();
    navigate('/login');
  };
}