import React, {
	createContext,
	useState,
	useContext,
	ReactNode,
	useEffect,
} from "react";

type ThemeContextProviderProps = {
	children: ReactNode;
};

type Theme = "light" | "dark";

type ThemeContextType = {
	theme: Theme;
	setTheme: React.Dispatch<React.SetStateAction<Theme>>;
};

export const ThemeContext : React.Context<ThemeContextType | null> 	 = createContext<ThemeContextType | null>(null);

export default function ThemeContextProvider({
	children,
}: ThemeContextProviderProps) {

	const [theme, setTheme] = useState<Theme>("dark");

	useEffect(():void => {
		const savedTheme = localStorage.getItem("theme") as Theme | null;
		if (savedTheme) {
			setTheme(savedTheme);
		}
	}, []);


	useEffect(():void => {
		document.documentElement.setAttribute("data-bs-theme", theme);
		localStorage.setItem("theme", theme);
	}, [theme]);

	return (
		<ThemeContext.Provider value={{ theme, setTheme }}>
			{children}
		</ThemeContext.Provider>
	);
}

export function useThemeContext(): ThemeContextType {
	const context: ThemeContextType | null = useContext(ThemeContext);
	if (!context) {
		throw new Error("useTheme must be used within a ThemeProvider");
	}
	return context;
}
