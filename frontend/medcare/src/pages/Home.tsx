// Assuming you have a ThemeSwitcher component

import {useAuthContext} from "../context/AuthContext.tsx";
import {UserRole} from "../features/auth/types.ts";
import Footer from "../components/Footer.tsx";
export default function Home() {
	const { userRole } = useAuthContext();
	return (
		<>
			<div className="main-content">
				<div>
					{userRole === UserRole.ANONYMOUS && (
						<>
						<section id="hero"  >
							<div className="hero-content container py-5 justify-content-center text-center align-items-center">
								<h1 className="text-center">Welcome to MedCare</h1>
								<p className="text-center">Your health, our priority</p>
							</div>
						</section>
						<section id="about justify-content-center text-center align-items-center">
							<h2>About MedCare</h2>
							<p>Learn more about our services.</p>
						</section>
						</>
					)}

					{userRole === UserRole.DOCTOR && (
						<section id="doctor-dashboard">
							<h2>Welcome Doctor</h2>
							<p>View patient records and appointments here.</p>
						</section>
					)}

					{userRole === UserRole.PATIENT && (
						<section id="patient-dashboard">
							<h2>Welcome Patient</h2>
							<p>View your health records, appointments, and more.</p>
						</section>
					)}

					{userRole === UserRole.SECRETARY && (
						<section id="admin-dashboard">
							<h2>Welcome Back Secretary</h2>
							<p>Manage users informations and their appointments.</p>
						</section>
					)}

				</div>
			</div>
			<Footer />
		</>
	);
}
