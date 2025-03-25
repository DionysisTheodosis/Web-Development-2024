import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App";
import "bootstrap/dist/css/bootstrap.min.css";
import "@fortawesome/fontawesome-free/css/all.css";
import { BrowserRouter } from "react-router";
import "./index.css";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import ThemeContextProvider from "./context/ThemeContext";
import {AuthProvider} from "./context/AuthContext.tsx";
const queryClient = new QueryClient();
ReactDOM.createRoot(document.getElementById("root") as HTMLElement).render(
	<React.StrictMode>	<BrowserRouter>
		<QueryClientProvider client={queryClient}>{/* Wrap your app with both BrowserRouter and QueryClientProvider */}

			<ThemeContextProvider>
				<AuthProvider>
					<App />
				</AuthProvider>
		</ThemeContextProvider>
		</QueryClientProvider>

		</BrowserRouter>
	</React.StrictMode>
);
