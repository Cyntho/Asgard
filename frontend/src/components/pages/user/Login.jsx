import React, { useState } from "react";
import { Navigate, Link } from 'react-router-dom';
import { Loader2, AlertCircle } from 'lucide-react';
import { toast } from 'react-toastify';

import PageTitle from "../../layout/PageTitle";
import { useLogin } from "@/hooks/useAuth.jsx";
import { useAuthStore } from "@stores/auth.store.jsx";



/*
[
{
id: 1,
displayName: "My Teamspeak test server",
host: "localhost",
webQueryPort: 10080,
sshPort: 10022,
rawPort: 10011,
useHttps: true,
enabled: true,
queryUsername: "serveradmin",
queryPassword: "Kee2gSYo",
apiKey: "BADosgBU_JU5SmNKG6uV9HzzBW6ETHVJo6U_sx8",
createdByUsername: "Admin",
updatedByUsername: "Admin"
}
]

 */



export default function Login() {

  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const loginHook = useLogin();
  const isAuthenticated = useAuthStore((s) => s.isAuthenticated());

  if (isAuthenticated) {
    return <Navigate to="/" replace />
  }

  const handleSubmit = (e) => {
    e.preventDefault();
    loginHook.mutate({ username, password });
  };

  const labelStyle =
    "block text-lg font-semibold text-primary dark:text-light mb-2";
  const textFieldStyle =
    "w-full px-4 py-2 text-base border rounded-md transition border-primary dark:border-light focus:ring focus:ring-dark dark:focus:ring-lighter focus:outline-none text-gray-800 dark:text-lighter bg-white dark:bg-gray-600 placeholder-gray-400 dark:placeholder-gray-300";
  return (
    <div className="min-h-213 flex items-center justify-center font-primary dark:bg-darkbg">
      <div className="bg-white dark:bg-gray-700 shadow-md rounded-lg max-w-md w-full px-8 py-6">
        {/* Title */}
        <PageTitle title="Login" />
        {/* Form */}
        <form onSubmit={handleSubmit} className="space-y-6">
          {/* Email Field */}
          <div>
            <label htmlFor="username" className={labelStyle}>
              Username
            </label>
            <input
              id="username"
              type="text"
              name="username"
              placeholder="Your Username"
              onChange={(e) => setUsername(e.target.value)}
              required
              className={textFieldStyle}
            />
          </div>

          {/* Password Field */}
          <div>
            <label htmlFor="password" className={labelStyle}>
              Password
            </label>
            <input
              id="password"
              type="password"
              name="password"
              placeholder="Your Password"
              onChange={(e) => setPassword(e.target.value)}
              required
              minLength={8}
              maxLength={20}
              className={textFieldStyle}
            />
          </div>

          {/* Submit Button */}
          <div>
            <button
              type="submit"
              className="w-full px-6 py-2 text-white dark:text-black text-xl rounded-md transition duration-200 bg-primary dark:bg-light hover:bg-dark dark:hover:bg-lighter"
              disabled={loginHook.isPending || !username || !password}
            >
              {loginHook.isPending ? (
                  <>
                    <Loader2 className="h-4 w-4 animate-spin" />
                    Signing in...
                  </>
                ) : (
                  'Sign In'
                )}
            </button>
          </div>

          {loginHook.isError && (
            <div className="flex items-center gap-2 text-destructive text-xs bg-destructive/10 rounded-md px-3 py-2">
              <AlertCircle className="h-3.5 w-3.5 shrink-0 text-red-500" />
              <span className="flex items-center text-red-500 font-semibold">Invalid credentials. Please try again.</span>
            </div>
          )}
        </form>

        {/* Register Link */}
        <p className="text-center text-gray-600 dark:text-gray-400 mt-4">
          Don't have an account?{" "}
          <Link
            to="/register"
            className="text-primary dark:text-light hover:text-dark dark:hover:text-lighter transition duration-200"
          >
            Register Here
          </Link>
        </p>
      </div>
    </div>
  );
}