import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faHeadset, faMoon, faSun } from "@fortawesome/free-solid-svg-icons";
import { Link, NavLink } from "react-router-dom";
import { useState, useEffect } from "react";
import { useUiStore } from "@/stores/ui.store.jsx"


export default function Header() {
	const { theme, toggleTheme } = useUiStore();

  const navLinkClass =
    "text-center text-lg font-primary font-semibold text-primary py-2 dark:text-light hover:text-dark dark:hover:text-lighter";
  return (
    <header className="border-b border-gray-300 sticky top-0 z-20 bg-gray-100 bg-normalbg dark:bg-darkbg">
      <div className="flex items-center justify-between mx-auto max-w-[1152px] px-6 py-4">
        <Link to="/" className={navLinkClass}>
          <FontAwesomeIcon icon={faHeadset} className="h-8 w-8"  />
          <span className="font-bold">Asgard</span>
        </Link>
        <nav className="flex items-center py-2 z-10">
          <button
            className="flex items-center justify-center mx-3 w-8 h-8 rounded-full border border-primary dark:border-light transition duration-300 hover:bg-gray-300 dark:hover:bg-gray-600"
            aria-label="Toggle theme"
            onClick={toggleTheme}
          >
            <FontAwesomeIcon
              icon={theme === 'dark' ? faMoon : faSun}
              className="w-4 h-4 dark:text-light text-primary"
            />
          </button>
          <ul className="flex space-x-6">
            <li>
              <NavLink to="/home" className={({ isActive }) => {
                return isActive ? `underline ${navLinkClass}` : `${navLinkClass}`
              }}>
                Home
              </NavLink>
            </li>
            <li>
              <NavLink to="/about" className={({ isActive }) => {
                return isActive ? `underline ${navLinkClass}` : `${navLinkClass}`
              }}>
                About
              </NavLink>
            </li>            
            <li>
              <NavLink to="/login" className={({ isActive }) => {
                return isActive ? `underline ${navLinkClass}` : `${navLinkClass}`
              }}>
                Login
              </NavLink>
            </li>            
          </ul>
        </nav>
      </div>
    </header>
  );
}

// export default Header;


