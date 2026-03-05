import React, {useState, useMemo} from "react";
import apiClient from "../../api/apiClient";
import { useLoaderData } from "react-router-dom";
import NewsCardShort from "./NewsCardShort";

export default function NewsListing() {
	const newsData = useLoaderData();

	return (
	<div className="max-w-6xl mx-auto">      
      <div className="grid grid-cols-1 sm:grid-cols-1 lg:grid-cols-1 gap-y-8 py-12">
        {newsData.length > 0 ? (
          newsData.map((news) => (
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

export async function newsLoader(){
	try {
		const response = await apiClient("/news");
		return response.data;
	} catch (error) {
		throw new Response(error.message || "Failed to load news. Please try again later.")
	}
}