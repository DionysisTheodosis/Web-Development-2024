import { useState} from "react";
import Form from "../../components/Form";
import Footer from "../../components/Footer";
import { ErrorResponse } from "../../types.ts";
import { registerSchema, RegisterSchema} from "./validations.ts";// Import the validation schema
import { useForm } from 'react-hook-form';
import {zodResolver} from "@hookform/resolvers/zod";
import { useNavigate } from "react-router";
import {registerUser} from "./api.ts";

const Register = () => {
    const [responseMessage, setResponseMessage] = useState("");
    const navigate = useNavigate();

    // Use React Hook Form with Zod validation
    const { register, handleSubmit, formState: { errors, isSubmitting } } = useForm<RegisterSchema>({
        resolver: zodResolver(registerSchema),
        mode: "onChange",
    });

    // Handle the form submit
    const onSubmit = async (data: RegisterSchema) => {
        try {
            await registerUser(data);
            navigate("/");
        } catch (error: unknown) {
            const errorMessage = (error as ErrorResponse)?.message || "An error occurred during registration.";
            setResponseMessage(errorMessage);
        }
    };


    // Form fields
    const fields = [
        {
            id: "firstName",
            label: "First Name",
            type: "text",
            placeholder: "John",
            required: true,
            icon: "fa fa-user",
            register: register("firstName"),
            error: errors.firstName?.message,

        },
        {
            id: "lastName",
            label: "Last Name",
            type: "text",
            placeholder: "Doe",
            required: true,
            icon: "fa fa-user",
            register: register("lastName"),
            error: errors.lastName?.message,
        },
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
        {
            id: "confirmPassword",
            label: "confirm Password",
            type: "password",
            placeholder: "Enter the same password as above",
            required: true,
            icon: "fas fa-lock",
            register: register("confirmPassword"),
            error: errors.confirmPassword?.message,
        },
        {
            id: "personalID",
            label: "Personal ID",
            type: "text",
            placeholder: "AZ123456",
            required: true,
            icon: "fa fa-id-card",
            register: register("personalID"),
            error: errors.personalID?.message,

        },
        {
            id: "amka",
            label: "AMKA",
            type: "text",
            placeholder: "12345678912",
            required: true,
            icon: "fa fa-id-card",
            register: register("amka"),
            error: errors.amka?.message,

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
                                    title="Register"
                                    fields={fields}
                                    buttonText={isSubmitting ? "Registering..." : "Register"}
                                    onSubmit={handleSubmit(onSubmit)}
                                    responseMessage={responseMessage}
                                    redirectLinkText="Do you have an account? Login here"
                                    redirectLinkHref="./login"
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

export default Register;
