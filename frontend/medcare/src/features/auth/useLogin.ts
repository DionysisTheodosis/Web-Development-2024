// import { UseMutateFunction, useMutation, useQueryClient } from "@tanstack/react-query";
// import { useNavigate } from "react-router-dom"; // Make sure this uses the correct import
// import { UserRole } from "./types.ts";
//
// // API to log in the user and return their user role
// async function login(email: string, password: string): Promise<UserRole> {
//     const response = await fetch("/api/auth/1/login", {
//         method: "POST",
//         headers: {
//             "Content-Type": "application/json",
//         },
//         body: JSON.stringify({ email, password }),
//     });
//
//     if (!response.ok) {
//         throw new Error("Failed to log in"); // Simple error handling for bad responses
//     }
//
//     const data = await response.json();
//     return data.userRole as UserRole; // Ensure `userRole` is returned and cast it properly
// }
//
// // Mutation hook type for `useLogin`
// type IUseLogin = UseMutateFunction<
//     UserRole, // The successful mutation result type
//     unknown,  // Type for any errors
//     { email: string; password: string }, // Variables sent to the mutation
//     unknown   // Context type
// >;
//
// export function useLogin(): IUseLogin {
//     const queryClient = useQueryClient(); // React Query cache client
//     const navigate = useNavigate(); // React Router navigation
//
//     // Define the mutation
//     const { mutate } = useMutation<
//         UserRole,                       // Successful result type (user role)
//         unknown,                        // Error type
//         { email: string; password: string }, // Variables accepted by mutation
//         unknown                         // Context
//     >(
//         // The mutation function to log in the user
//         ({ email, password }) => login(email, password),
//         {
//             // On successful login
//             onSuccess: (userRole) => {
//                 // Update the "userRole" value in the React Query cache
//                 queryClient.setQueryData(["userRole"], userRole);
//
//                 // Navigate to the home page (or any other route) after successful login
//                 navigate("/");
//             },
//             // On login failure
//             onError: (error) => {
//                 console.error("Login failed:", error);
//                 alert("Login failed. Please check your credentials.");
//             },
//         }
//     );
//
//     return mutate; // Return the mutate function for use
// }