import axios, {AxiosError} from "axios";
import {LoginData, RegisterData, UserRole} from "./types.ts";
import {BASE_URL} from "../../types.ts";




export async function fetchUserRole(): Promise<UserRole> {
    try {
        const response = await axios.get(BASE_URL + "auth/user-role");
        return response.data.role as UserRole;
    }
    catch (error) {
        // @ts-ignore
        throw new Error(error?.message||"An unknown error occurred");
    }
}

export async function loginUser(data: LoginData): Promise<UserRole> {
    try {
        console.log("Attempting login with data:", data);
        const response = await axios.post(BASE_URL + "auth/login", data);

        console.log("Login response received:", response.data);

        // If response is a plain string (e.g., "DOCTOR"), return it directly
        if (typeof response.data === "string") {
            console.log("Login successful, role:", response.data);
            return response.data as UserRole;
        }

        // If response is an object with `role`, return that
        if (response.data.role) {
            console.log("Login successful, role:", response.data.role);
            return response.data.role as UserRole;
        }

        // If neither, throw an error
        throw new Error("Unexpected response format");
    } catch (error) {
        console.error("Login request failed:", error);
        throw error;
    }
}
export async function registerUser(data: RegisterData): Promise<void> {
    try {
        const response = await axios.post("/api/v1/auth/register", data);
        if (response.status === 201) {
            return;
        }
    } catch (error: unknown) {
        if (error instanceof AxiosError) {
            const errorMessage = error.response?.data?.message || "An unknown error occurred during registration";
            throw new Error(errorMessage);
        }
        throw new Error("An unknown error occurred during registration");
    }
}

