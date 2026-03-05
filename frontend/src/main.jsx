import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import "./index.css";
import App from "./App.jsx";
import Home from "./components/pages/Home.jsx";

import {createBrowserRouter, RouterProvider} from "react-router-dom"
import { createRoutesFromElements, Route } from "react-router-dom";

import { newsLoader } from "./components/news/NewsListing.jsx";
import { registerAction } from "./components/user/Register.jsx";
import { loginAction } from "./components/user/Login.jsx";

import About from "./components/about/About.jsx";
import ErrorPage from "./components/reusable/ErrorPage.jsx";
import Login from "./components/user/Login.jsx";
import Register from "./components/user/Register.jsx";



const routingDefinitions = createRoutesFromElements(
  <Route path="/" errorElement={<ErrorPage />} element={<App />}>
    <Route index element={<Home />} loader={newsLoader} />
    <Route path="/home" element={<Home />} loader={newsLoader} />
    <Route path="/about" element={<About />} />
    <Route path="/login" element={<Login />} action={loginAction} />
    <Route path="/register" element={<Register />} action={registerAction} />
  </Route>
);

const appRouter = createBrowserRouter(routingDefinitions);

createRoot(document.getElementById("root")).render(
  <StrictMode>
    <RouterProvider router={appRouter} />
  </StrictMode>
);
