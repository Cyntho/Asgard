import NewsCardShort from "./NewsCardShort";
import { newsApi } from "../../../api/news.api";
import { useQuery } from '@tanstack/react-query';

import { PageLoader } from "../../layout/PageLoader";
import ErrorPage from "@pages/ErrorPage"

export default function NewsListing() {
  const { isLoading, error, data } = useQuery({
    queryKey: ['news'],
    queryFn: () => newsApi.loadAll(),
  })
  
  if (isLoading) return <PageLoader />;

  if (error) return <ErrorPage code={error.name} message={error.message} />;

  return (
	<div className="max-w-6xl mx-auto">      
      <div className="grid grid-cols-1 sm:grid-cols-1 lg:grid-cols-1 gap-y-8 py-12">
        {data.length > 0 ? (
          data.map((news) => (
            <NewsCardShort key={news.newsId} news={news} />
          ))
        ) : (
          <p className="text-center font-bold text-lg text-primary">
            No news found
          </p>
        )}
      </div>
    </div>
	)
}