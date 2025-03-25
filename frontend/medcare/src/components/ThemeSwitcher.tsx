import { useThemeContext } from "../context/ThemeContext";

export default function ThemeSwitcher() {
	const { theme, setTheme } = useThemeContext();

	const toggleTheme = () => {
		const newTheme = theme === "dark" ? "light" : "dark";
		setTheme(newTheme);
	};

	return (
		<>
			<input
				type="checkbox"
				className="checkbox"
				id="checkbox"
				checked={theme === "dark"}
				onChange={toggleTheme}
			/>
			<label htmlFor="checkbox" className="checkbox-label">
				<i className="fas fa-moon"></i>
				<i className="fas fa-sun"></i>
				<span className="ball"></span>
			</label>
		</>
	);
}
