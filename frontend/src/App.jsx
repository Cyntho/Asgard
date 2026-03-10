import { lazy, Suspense } from 'react';
import {
  BrowserRouter,
  Routes,
  Route,
  Navigate,
  createRoutesFromElements,
  RouterProvider,
  createBrowserRouter
} from 'react-router-dom';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { ToastContainer, Bounce} from 'react-toastify';


import { useAuthStore } from '@stores/auth.store';
import { PageLoader } from '@layout/PageLoader.jsx'
import { PageLayout } from '@layout/PageLayout.jsx';
import ErrorPage from "@pages/ErrorPage.jsx";


function AdminRoute({ children }) {
  const isAdmin = useAuthStore((s) => s.isAdmin());
  const isAuthenticated = useAuthStore((s) => s.isAuthenticated());
  if (!isAuthenticated) return <Navigate to="/News" replace />;
  return <>{children}</>;
}

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      retry: 1,
      refetchOnWindowFocus: false,
      staleTime: 30_000,
    },
  },
});

const Login = lazy(() => import('@pages/user/Login'));
const News = lazy(() => import('@pages/news/News'));
const About = lazy(() => import('@pages/About'));
const Dashboard = lazy(() => import('@pages/dashboard/Dashboard'));


const router = createBrowserRouter([
  {
    path: "/",
    element: (
      <Suspense fallback={<PageLoader />}>
        <PageLayout />
      </Suspense>
    ),
    children: [
      // entspricht deinem <Route path="/" element={<Navigate ... />} />
      {
        index: true,
        element: <Navigate to="/news" replace />,
      },
      {
        path: "news",
        element: <News />,
      },
      {
        path: "news/:id",
        element: <News />,
      },
      {
        path: "dashboard",
        element: (
          <AdminRoute>
            <Dashboard />
          </AdminRoute>
        ),
      },
      {
        path: "about",
        element: <About />,
      },
      {
        path: "login",
        element: <Login />,
      },
      // Catch-all: entspricht deinem <Route path="/*" ... />
      {
        path: "*",
        element: <Navigate to="/news" replace />,
      },

      // Beispiel: Teamspeak Config Route mit action (optional, falls du sie brauchst)
      /*
      {
        path: "teamspeak-config",
        element: <TeamspeakServerConfig />,
        action: async ({ request }) => {
          const formData = await request.formData();
          const displayName = formData.get("displayName");
          // TODO: API-Call / Server-Logik
          return null;
        },
      },
      */
    ],
  },
]);

export function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <RouterProvider router={router} />
      <ToastContainer
        position="top-center"
        autoClose={3000}
        hideProgressBar={false}
        newestOnTop={false}
        draggable
        pauseOnHover
        theme={localStorage.getItem("theme") === "dark" ? "dark" : "light"}
        transition={Bounce}
      />
    </QueryClientProvider>
  );
}