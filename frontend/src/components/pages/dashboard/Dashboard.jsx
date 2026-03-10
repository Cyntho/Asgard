import { useAuthStore } from "@stores/auth.store";
import { Navigate } from "react-router-dom";
import { useQuery} from "@tanstack/react-query";
import {tsApi} from "@/api/ts.api.js";
import {PageLoader} from "@layout/PageLoader.jsx";
import ErrorPage from "@pages/ErrorPage.jsx";

import DropDown from "@components/ui/Dropdown.jsx";
import NewsCardShort from "@pages/news/NewsCardShort.jsx";
import {TeamspeakServerConfig} from "@components/teamspeak/TeamspeakServerConfig.jsx";

export default function Dashboard() {
  const auth = useAuthStore();

  const { isLoading, error, data} = useQuery({
    queryKey: ['dashboard'],
    queryFn: () => tsApi.loadAll(),
  })

  if (!auth.isAuthenticated()) {
    return <Navigate to="/" replace />
  }

  if (isLoading) return <PageLoader />;

  if (error) return <ErrorPage code={error.name ?? ""} message={error.message ?? ""} />;


  return (
    <div className="max-w-6xl mx-auto">
      <div className="grid grid-cols-1 sm:grid-cols-1 lg:grid-cols-1 gap-y-8 py-12">
        <TeamspeakServerConfig key={0} config={data[0]} />
      </div>
    </div>
  );
}
