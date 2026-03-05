import React from "react";
import Header from "../layout/Header";
import Footer from "../layout/Footer";
import PageTitle from "../layout/PageTitle";
import { Link, NavLink } from "react-router-dom";
import errorImage from "../../assets/error.png"
import { useRouteError } from "react-router-dom";
import { useNavigate } from "react-router-dom";

export default function ErrorPage() {
    const errorRoute = useRouteError();

    let errorCode = "Oops! Something went wrong";
    let errorMessage = "An unexpected error occurred. Please try again later."
    if (errorRoute){
      errorCode = errorRoute.status;
      errorMessage = errorRoute.data;
    }

    return (
    <div className="flex flex-col min-h-245">
      <Header />
      {/* Main Content */}
      <main className="grow">
        <div className="py-12 bg-normalbg dark:bg-darkbg font-primary">
          <div className="max-w-4xl mx-auto px-4">
            <PageTitle title={errorCode} />
          </div>
          <div className="text-center text-gray-600 dark:text-lighter flex flex-col items-center">
            <p className="max-w-xl px-2 mx-auto leading-6 mb-4">
              {errorMessage}
            </p>
            <img
              src={errorImage}
              alt="Error"
              className="w-full max-w-xl mx-auto mb-6"
            />
            <Link
              to="/home"
              className="py-3 px-6 text-white dark:text-black text-xl rounded-md transition duration-200 bg-primary dark:bg-light hover:bg-dark dark:hover:bg-lighter font-semibold"
            >
              Back to Home
            </Link>
          </div>
        </div>
      </main>
      <Footer />
    </div>
  );
}