import React, {createContext, ReactNode, useContext, useState} from "react";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { loginUser, fetchUserRole } from "../features/auth/api";
import { LoginData, UserRole } from "../features/auth/types";
import {useAxiosInterceptor} from "../api.ts";

// Define the context type
interface AuthContextType {
    userRole: UserRole;
    login: (data: LoginData) => Promise<void>;
    logout: () => void;
    isAuthenticated: boolean;
    isLoading: boolean;
    error: Error | null;
}

// Default context values
const defaultState: AuthContextType = {
    userRole: UserRole.ANONYMOUS,
    login: async () => {},
    logout: () => {},
    isAuthenticated: false,
    isLoading: false,
    error: null,
};

// Create the AuthContext
export const AuthContext = createContext<AuthContextType>(defaultState);

interface AuthProviderProps {
    children: ReactNode;
}

// AuthProvider component
export const AuthProvider: React.FC<AuthProviderProps> = ({ children }) => {
    const queryClient = useQueryClient();
    // useAxiosInterceptor();
    // Check for user role in localStorage on first render
    const cachedUserRole = localStorage.getItem("userRole");
    useAxiosInterceptor()

    const [userRole, setUserRole] = useState<UserRole>(cachedUserRole ? (cachedUserRole as UserRole) : UserRole.ANONYMOUS);

    const { isLoading, error } = useQuery<UserRole>({
        queryKey: ["userRole"],
        queryFn: fetchUserRole,
        enabled: !cachedUserRole,
        initialData: userRole,
        staleTime: 15 * 60 * 1000,
        refetchOnWindowFocus: true,
    });

    const loginMutation = useMutation<UserRole, Error, LoginData>({
        mutationFn: loginUser,
        onSuccess: (newRole) => {
            // Update the cached role after login
            queryClient.setQueryData(["userRole"], newRole);
            setUserRole(newRole);
            localStorage.setItem("userRole", newRole); // Persist to localStorage
            queryClient.invalidateQueries({ queryKey: ["userRole"] });

        },
        onError: (error) => {
            console.error("Login failed:", error);
        },
    });

    const login = async (data: LoginData) => {
        try {
            await loginMutation.mutateAsync(data);
        } catch{
            throw new Error("Login failed, please try again.");
        }
    };

    // Handle logout
    const logout = () => {
        queryClient.setQueryData(["userRole"], UserRole.ANONYMOUS); // Reset the cached role
        setUserRole(UserRole.ANONYMOUS); // Reset the role in state
        localStorage.removeItem("userRole"); // Remove from localStorage
    };

    const isAuthenticated = userRole !== UserRole.ANONYMOUS;


    return (
        <AuthContext.Provider value={{ userRole, login, logout, isAuthenticated, isLoading, error }}>
            {children}
        </AuthContext.Provider>
    );
};


export const useAuthContext = () => {
    const context = useContext(AuthContext);

    if (!context) {
        throw new Error("useAuthContext must be used within an AuthProvider");
    }

    return context;
};
