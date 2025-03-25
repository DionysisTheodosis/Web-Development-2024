import axios from "axios";
import { useNavigate } from "react-router";
import {useEffect} from "react";

export const useAxiosInterceptor = () => {
    const navigate = useNavigate();

    useEffect(() => {
        const interceptor = axios.interceptors.response.use(
            (response) => response,
            (error) => {
                if (error.response?.status === 403) {
                    navigate("/login"); // Redirect to login page
                }
                return Promise.reject(error);
            }
        );
        return () => axios.interceptors.response.eject(interceptor);
    }, [navigate]);
};

const api = axios.create({
    baseURL: "/api/v1/", // Change based on your backend URL
    headers: {
        "Content-Type": "application/json",
    },
});

export default api;