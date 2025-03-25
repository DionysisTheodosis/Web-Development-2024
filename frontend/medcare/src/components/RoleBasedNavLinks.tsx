import { UserRole } from '../features/auth/types';
import ThemeSwitcher from './ThemeSwitcher';
import NavLinkItem from "./NavLinkItem.tsx";
import { Nav } from 'react-bootstrap';
import {NavLink, useNavigate} from "react-router";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faSignOutAlt} from "@fortawesome/free-solid-svg-icons";
import {useAuthContext} from "../context/AuthContext.tsx";


type RoleBasedNavLinksProps = {
  role: UserRole;
}

export const RoleBasedNavLinks = ({ role }: RoleBasedNavLinksProps) => {
    const { logout } = useAuthContext();
    const navigate = useNavigate();
    const handleLogout = () => {
        logout();
        navigate("/login");
    };

    switch (role) {
    case UserRole.DOCTOR:
      return (
        <>
          <ul className="navbar-nav justify-content-center flex-lg-grow-1 pe-3 text-center">
            <NavLinkItem to="/" text="Home" />
            <NavLinkItem to="/appointments" text="Appointments" />
            <NavLinkItem to="availability" text="Availability"/>
            <NavLinkItem to="/patients" text="Patients" />
            <NavLinkItem to="/secretaries" text="Secretaries" />
            <NavLinkItem to="/settings" text="Settings" />
          </ul>
          <div className="justify-content-end align-items-center">
            <ThemeSwitcher />
              <Nav.Link onClick={handleLogout} className="d-flex flex-column justify-content-center align-items-center">
                  <FontAwesomeIcon icon={faSignOutAlt} size="lg" className="mb-1" />
                  <div className="text-center">Logout</div>
              </Nav.Link>
          </div>
        </>
      );
    
    case UserRole.PATIENT:
      return (
          <>
            <ul className="navbar-nav justify-content-center flex-lg-grow-1 pe-3 text-center">
              <NavLinkItem to="/" text="Home" />
              <NavLinkItem to="/appointments" text="Appointments" />
              <NavLinkItem to="/medical-history" text="Medical History"/>
              <NavLinkItem to="/settings" text="Settings" />
            </ul>
            <div className="justify-content-end align-items-center">
              <ThemeSwitcher />
                <Nav.Link onClick={handleLogout} className="d-flex align-items-center">
                    <FontAwesomeIcon icon={faSignOutAlt} size="lg" className="mr-2" />
                    Logout
                </Nav.Link>
            </div>
          </>
      );

    case UserRole.SECRETARY:
      return (
          <>
            <ul className="navbar-nav justify-content-center flex-lg-grow-1 pe-3 text-center">
              <NavLinkItem to="/" text="Home" />
              <NavLinkItem to="/appointments" text="Appointments" />
              <NavLinkItem to="/patients" text="Patients" />
              <NavLinkItem to="/settings" text="Settings" />
            </ul>
            <div className="justify-content-end align-items-center">
              <ThemeSwitcher />
                <Nav.Link onClick={handleLogout} className="d-flex align-items-center">
                    <FontAwesomeIcon icon={faSignOutAlt} size="lg" className="mr-2" />
                    <span className="text">Logout</span>
                </Nav.Link>
            </div>
          </>
      );
    case UserRole.ANONYMOUS:
    default:
      return (
        <>
          <ul className="navbar-nav justify-content-center flex-lg-grow-1 pe-3 text-center">
            <NavLinkItem to="/" text="Home" />
            <NavLinkItem to="/about" text="About" />
            <NavLinkItem to="/services" text="Services" />
            <NavLinkItem to="/contact" text="Contact" />
          </ul>
          <div className="justify-content-end align-items-center">
            <ThemeSwitcher />
            <div className="col-12 d-flex justify-content-center">
                <Nav.Link as={NavLink} to="/login" className="text me-3">Login</Nav.Link>
              <Nav.Link as={NavLink}
                to="/register"
                className="text text-decoration-none px-3 py-1 rounded-4"
                style={{ backgroundColor: '#f94ca4' }}
              >
                Register
              </Nav.Link>
            </div>
          </div>
        </>
      );
  }
};