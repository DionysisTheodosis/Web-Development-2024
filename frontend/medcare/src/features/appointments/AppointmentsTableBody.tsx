import React from 'react';
import {Patient} from "./types.ts";
import {UserRole} from "../auth/types.ts";
import {useAuthContext} from "../../context/AuthContext.tsx";


interface AppointmentsTableBodyProps {
    appointments: Appointment[];
    handleDelete: (appointmentID: string) => Promise<void>;
}

const AppointmentsTableBody: React.FC<AppointmentsTableBodyProps> = ({ appointments, handleDelete }) => {
    const { userRole } = useAuthContext();
    if (!appointments.length) {
        return (
            <tr>
                <td colSpan={7} className="text-center">
                    No results found
                </td>
            </tr>
        );
    }

    return (
        <>
            {appointments.map((appointment) => (
                <tr key={appointment.appointmentID}>
                    <td>{appointment.date}</td>
                    <td>{appointment.time}</td>
                    <td>{appointment.reason}</td>
                    <td>{appointment.creationDate}</td>
                    <td>{appointment.appointmentStatus}</td>
                    <td>
                        <button className="btn btn-info btn-sm">
                            <i className="fa fa-user"></i>
                        </button>
                        <button className="btn btn-warning btn-sm">
                            <i className="fa fa-calendar-check"></i>
                        </button>
                        {userRole===UserRole.DOCTOR &&  <button className="btn btn-warning btn-sm">
                            <i className="fa fa-history"></i>
                        </button>}
                        <button
                            className="btn btn-danger btn-sm"
                            onClick={async () => {
                                if (window.confirm('Are you sure you want to delete this patient?')) {
                                    await handleDelete(patient.patientID);
                                }
                            }}
                        >
                            <i className="fa fa-trash"></i>
                        </button>
                    </td>
                </tr>
            ))}
        </>
    );
};

export default PatientsTableBody;
