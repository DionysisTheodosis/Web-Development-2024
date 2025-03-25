export default function Footer() {
	return (
		<div className="footer container justify-content-center text-center">
			<p className="mb-0">
				&copy; {new Date().getFullYear()} MedCare. All rights reserved.
			</p>
			<p>
				Developed by <strong>Dionysis Theodosis</strong>
			</p>
		</div>
	);
}
