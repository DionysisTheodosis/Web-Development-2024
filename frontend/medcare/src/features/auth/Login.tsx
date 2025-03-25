import { useState} from "react";
import Form from "../../components/Form";
import Footer from "../../components/Footer";
import { ErrorResponse } from "../../types.ts";
import { loginSchema } from "./validations.ts";
import { LoginSchema } from "./types.ts"; // Import the validation schema
import { useForm } from 'react-hook-form';
import {useAuthContext} from "../../context/AuthContext.tsx";
import {zodResolver} from "@hookform/resolvers/zod";
import { useNavigate } from "react-router";
import {useQueryClient} from "@tanstack/react-query";

const Login = () => {
	const [responseMessage, setResponseMessage] = useState("");
	const navigate = useNavigate();
	const queryClient = useQueryClient();
	// Use React Hook Form with Zod validation
	const { register, handleSubmit, formState: { errors, isSubmitting } } = useForm<LoginSchema>({
		resolver: zodResolver(loginSchema),
	});

	const { login} = useAuthContext(); // Use context for login

	const onSubmit = async (data: LoginSchema) => {
		try {
			// Login using the context's login function
			await login(data);

			// Once logged in, trigger revalidation (refetch) of the user role
			// This can be done by using the queryClient directly or using the `refetch` method from the query
			await queryClient.invalidateQueries({queryKey: ['todos']});
			// Redirect to the homepage after successful login
			navigate("/");
		} catch (error: unknown) {
			// Handle the error, show the error message
			const errorMessage = (error as ErrorResponse)?.message || "An error occurred during login.";
			setResponseMessage(errorMessage);
		}
	};

	// Form fields
	const fields = [
		{
			id: "email",
			label: "Email",
			type: "email",
			placeholder: "Enter your email",
			required: true,
			icon: "fas fa-envelope",
			register: register("email"),
			error: errors.email?.message, // Show error message dynamically
		},
		{
			id: "password",
			label: "Password",
			type: "password",
			placeholder: "Enter your password",
			required: true,
			icon: "fas fa-lock",
			register: register("password"),
			error: errors.password?.message, // Show error message dynamically
		},
	];

	return (
		<>
			<div className="main-content">
				<div className="container py-5">
					<div className="row justify-content-center">
						<div className="col-12 col-md-8 col-lg-5">
							<div className="card shadow p-4">
								<Form
									title="Login"
									fields={fields}
									buttonText={isSubmitting ? "Logging in..." : "Login"}
									onSubmit={handleSubmit(onSubmit)}
									responseMessage={responseMessage}
									redirectLinkText="Don't have an account? Register here"
									redirectLinkHref="/register"
								/>
							</div>
						</div>
					</div>
				</div>
			</div>
			<Footer />
		</>
	);
};

export default Login;
