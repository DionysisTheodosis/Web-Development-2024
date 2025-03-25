import { Nav } from "react-bootstrap";
import {NavLink} from "react-router";

export type NavLinkItemProps = {
	to: string;
	text: string;
	active?: boolean;
};
export default function NavLinkItem({
	to,
	text,
	active = false,
}: NavLinkItemProps) {
	return (
		<li className="nav-item mx-2">
			<Nav.Link as={NavLink}
				className={`nav-link ${active ? "active" : ""}`}
				to={to}
				aria-current={active ? "page" : undefined}
			>
				{text}
			</Nav.Link>
		</li>
	);
}
