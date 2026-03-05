import React from "react";
import errorImage from "../../assets/error.png"
import NotFound from "../../assets/404.jpg"
import { NavLink } from "react-router-dom";

export default function NewsCardShort({news}) {

    return (        
        <div className="grid grid-cols-[20%_80%] h-72 max-h-72 w-6xl gap-0.5">
          
          { /* First Column */}
          <div className="border border-gray-300 rounded-md">

            { /* Left Wrapper */}
            <div className="grid grid-rows-[80%_10%_10%] bg-gray-200 dark:bg-gray-600 h-full">

              {/* Image */}              
              <NavLink to={`/news/${news.newsId}`} className="py-0.5 px-2">
                <img
                src={NotFound}
                className="flex border border-gray-50 rounded-md w-auto w-20% h-20% scale-75 object-cover transition-transform duration-500 ease-in-out hover:scale-90"
                />
              </NavLink>
              
              

              {/* Author */}
              <span className="text-center font-bold text-lg text-primary dark:text-lighter">
                <NavLink to={`/users/${news.authorid ?? 0}`}>
                  {news.authorName ?? "Unknown User"}
                </NavLink>
              </span>

              {/* Date */}
              <span className="text-center text-sm text-primary dark:text-lighter">
                Created {news.createdAt}
              </span>

            </div>
          </div>           

          { /* Second Column */}
          <div className="bg-gray-200 dark:bg-gray-600 border border-gray-300 rounded-md">

            { /* Right Wrapper */}
            <div className="grid grid-rows-[10%_75%_15%] h-full">
              
              {/* Title */}
              <div className="text-primary dark:text-lighter text-xl font-bold text-left mx-2 p-2">
                {news.title ?? "Unknown title"}
              </div>

              {/* Content */}
              <div className="text-left text-sm text-primary dark:text-lighter mx-2 p-2 overflow-hidden">
                {news.description ?? ""}
              </div>

              {/* Footer */}
              <div className="text-sm text-primary w-full">
                <ul className="flex justify-end items-center space-x-2 mx-1.5 h-full text-white dark:text-primary text-sm text-semibold">
                  <li>
                    <NavLink to={`/news/${news.newsId}`} className="py-0.5 px-2 rounded-md transition duration-200 bg-primary dark:bg-light hover:bg-dark dark:hover:bg-lighter">
                      View Details
                    </NavLink>
                  </li>
                  <li>
                    <NavLink to={`/news/${news.newsId}`} className="py-0.5 px-2 rounded-md transition duration-200 bg-primary dark:bg-light hover:bg-dark dark:hover:bg-lighter">
                      Short
                    </NavLink>
                  </li>
                  <li>
                    <NavLink to={`/news/${news.newsId}`} className="py-0.5 px-2 rounded-md transition duration-200 bg-primary dark:bg-light hover:bg-dark dark:hover:bg-lighter">
                      Some very long link
                    </NavLink>
                  </li>
                  <li>
                    <NavLink to={`/news/${news.newsId}`} className="py-0.5 px-2 rounded-md transition duration-200 bg-primary dark:bg-light hover:bg-dark dark:hover:bg-lighter">
                      View Details
                    </NavLink>
                  </li>
                </ul>
              </div>

            </div>
          </div>

        </div>
      );

}