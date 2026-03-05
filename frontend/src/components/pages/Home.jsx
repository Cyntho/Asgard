import PageHeading from "../layout/PageHeading";
import NewsListing from "../news/NewsListing";
import apiClient from "../../api/apiClient";
import { useState, useEffect } from "react";

// Hooks
export default function Home() {
  return (
    <div className="max-w-6xl mx-auto px-6 py-8 bg-normalbg dark:bg-darkbg">
      <NewsListing />
    </div>
  );
}
