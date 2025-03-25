import { useNavigate } from "react-router";

export default function Unauthorized() {
	const navigate = useNavigate();

	const handleRedirect = () => {
		navigate("/login");
	};

	return (
		<div className="unauthorized-page container py-5 justify-content-center text-center">
			<h1>Unauthorized Access</h1>
			<p>You do not have permission to access this page.</p>
			<button onClick={handleRedirect} className="btn btn-primary">
				Go to Login Page
			</button>
		</div>
	);
}
