import { useState } from 'react';
import { RoleBasedNavLinks } from './RoleBasedNavLinks';
import Container from 'react-bootstrap/Container';
import Navbar from 'react-bootstrap/Navbar';
import {Nav, Offcanvas} from 'react-bootstrap';
import darkThemeLogo from "../assets/MedCare-logo-dark-Theme.svg";
import whiteThemeLogo from "../assets/MedCare-logo-white-Theme.svg";
import {useThemeContext} from "../context/ThemeContext.tsx";
import {useAuthContext} from "../context/AuthContext.tsx";
export const AppNavbar = () => {
    const [showOffcanvas, setShowOffcanvas] = useState(false);

    const { theme } = useThemeContext();
    const {userRole} = useAuthContext();

    const logoSrc = theme === "dark" ? darkThemeLogo : whiteThemeLogo;

    const handleCloseOffcanvas = () => setShowOffcanvas(false);
    const handleShowOffcanvas = () => setShowOffcanvas(true);

    return (
        <Navbar className="navbar" expand="lg" sticky="top">
            <Container fluid>
                <Navbar.Brand className="navbar-brand d-flex align-items-center" href="/">
                    <img
                        className="logoImage img"
                        src={logoSrc}
                        alt="Doctor Logo"
                    />
                </Navbar.Brand>

                <Navbar.Toggle
                    aria-controls="offcanvasNavbar"
                    className="shadow-none border-0"
                    onClick={handleShowOffcanvas}
                />

                <Offcanvas
                    show={showOffcanvas}
                    onHide={handleCloseOffcanvas}
                    placement="start"
                    className="sidebar"
                >
                    <Offcanvas.Header closeButton className="text-white border-bottom">
                        <Offcanvas.Title>
                            <Navbar.Brand href="#">
                                <img
                                    className="logoImage"
                                    src={logoSrc}
                                    alt="Doctor Logo"
                                />
                            </Navbar.Brand>
                        </Offcanvas.Title>
                    </Offcanvas.Header>

                    <Offcanvas.Body className="justify-content-center align-items-center flex-column flex-lg-row p-4 p-lg-0">
                        <RoleBasedNavLinks role={userRole} />
                    </Offcanvas.Body>
                </Offcanvas>
                <Navbar.Collapse className="d-none d-lg-flex justify-content-center">
                    <Nav className="navbar-nav justify-content-center flex-grow-1 text-center">
                        <RoleBasedNavLinks role={userRole} />
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
};