import PageTitle from "../layout/PageTitle";
import { Form } from "react-router-dom";
import apiClient from "../../api/apiClient";

export default function Register() {

    const handleChangeEvent = (e) => {
        console.log(e);
    }
    const labelStyle = "block text-lg font-semibold text-primary dark:text-light mb-2";
    const textFieldStyle = "w-full px-4 py-2 text-base border rounded-md transition border-primary dark:border-light focus:ring focus:ring-dark dark:focus:ring-lighter focus:outline-none text-gray-800 dark:text-lighter bg-white dark:bg-gray-600 placeholder-gray-400 dark:placeholder-gray-300";
    return (
        <div className="min-h-213 flex items-center justify-center font-primary dark:bg-darkbg">
            <div className="bg-white dark:bg-gray-700 shadow-md rounded-lg max-w-md w-full px-8 py-6">
                {/* Title */}
                <PageTitle title="Register" />
                {/* Form */}
                <Form method="POST" className="space-y-6">
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
                            required
                            className={textFieldStyle}
                        />
                    </div>
                    <div>
                        <label htmlFor="email" className={labelStyle}>
                            E-Mail
                        </label>
                        <input
                            id="email"
                            type="text"
                            name="email"
                            placeholder="Your E-Mail"
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
                            required
                            minLength={8}
                            maxLength={20}
                            className={textFieldStyle}
                        />
                    </div>
                    <div>
                        <label htmlFor="password2" className={labelStyle}>
                            Repeat Password
                        </label>
                        <input
                            id="password2"
                            type="password"
                            name="password2"
                            placeholder="Repeat Password"
                            required
                            minLength={8}
                            maxLength={20}
                            className={textFieldStyle}
                            onChange={handleChangeEvent}
                        />
                    </div>

                    {/* Submit Button */}
                    <div>
                        <button
                            type="submit"
                            className="w-full px-6 py-2 text-white dark:text-black text-xl rounded-md transition duration-200 bg-primary dark:bg-light hover:bg-dark dark:hover:bg-lighter"
                        >
                            Login
                        </button>
                    </div>
                </Form>
            </div>
        </div>
    )
}

export async function registerAction({request, params}){
    try {
        const data = await request.formData();

        const contactData = {
            username: data.get("username"),
            email: data.get("email"),
            password: data.get("password"),
            password2: data.get("password2")
        }

        const response = await apiClient.post("/auth/register", contactData);
        return {success: "true"}
    } catch (error) {
        throw new Response(
            error.message || "Failed to register. Please try again later",
            { status: error.status || 500}
        )
    }
}