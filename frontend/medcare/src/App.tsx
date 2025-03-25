import "./App.css";
import { Routes, Route, useLocation, Navigate } from "react-router";
import { UserRole } from "./features/auth/types.ts";
import { JSX } from "react";
import Login from "./features/auth/Login.tsx";
import Unauthorized from "./pages/Unauthorized.tsx";
import Home from "./pages/Home.tsx";
import { AppNavbar } from "./components/AppNavbar.tsx";
import Register from "./features/auth/Register.tsx";
import {useAuthContext} from "./context/AuthContext.tsx";
import PatientsTable from "./features/patients/PatientsTable.tsx";





function App() {
	const location = useLocation();
	const { userRole } = useAuthContext();
	// ProtectedRoute component to protect specific routes based on the user role
	interface ProtectedRouteProps {
		requiredRoles: UserRole[];
		role: UserRole;
		element: JSX.Element;
	}

	function ProtectedRoute({ requiredRoles, role, element }: ProtectedRouteProps) {
		if (!requiredRoles.includes(role)) {
			return <Navigate to="/Unauthorized" replace />;
		}
		return element;
	}

	// Define routes where the navbar should not be shown
	const hideNavbarRoutes = ["/login", "/register"];
	const shouldShowNavbar = !hideNavbarRoutes.includes(location.pathname);

	return (
		<div className="app">
			{shouldShowNavbar && <AppNavbar />}
			<Routes>
				{/* Home Page Route */}
				<Route path="/" element={<Home />} />

				{/* Login and Register Routes */}
				<Route path="/login" element={<Login />} />
				<Route path="/register" element={<Register />} />

				{/* Protected Route for Patients */}
				<Route
					path="/patients"
					element={
						<ProtectedRoute
							requiredRoles={[UserRole.PATIENT, UserRole.DOCTOR, UserRole.SECRETARY]}
							role={userRole} // Use the fetched user role here
							element={<PatientsTable />}
						/>
					}
				/>

				{/* Unauthorized Route */}
				<Route path="/Unauthorized" element={<Unauthorized />} />
			</Routes>
		</div>
	);
}

export default App;
