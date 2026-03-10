import { useParams } from "react-router-dom";
import { useQuery } from "@tanstack/react-query";
import { PageLoader } from "../../layout/PageLoader";

import { newsApi } from "../../../api/news.api";

import ErrorPage from "@pages/ErrorPage";
import NewsListing from "./NewsListing";
import NewsCardLong from "./NewsCardLong";

export default function News() {

  const { id } = useParams(); // kommt aus /news/:id

  const { isLoading, error, data } = useQuery({
    queryKey: ['news', id],
    queryFn: () => newsApi.loadById(id),
    enabled: !!id, // nur ausführen, wenn id vorhanden ist
  });

  if (isLoading) return <PageLoader />;
  if (error) return <ErrorPage code={"404"} message={"News entry not found."} />;
  if (!data) return (
    <div className="max-w-6xl mx-auto px-6 py-8 bg-normalbg dark:bg-darkbg">
      <NewsListing />
    </div>
  );

  // data ist der einzelne News-Eintrag
  return (
    <div className="max-w-6xl mx-auto px-6 py-8 bg-normalbg dark:bg-darkbg">
      <NewsCardLong news={data} />
    </div>
  );

  
}
