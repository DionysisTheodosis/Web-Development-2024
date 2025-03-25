import React from 'react';
import {Patient} from "./types.ts";
import {UserRole} from "../auth/types.ts";
import {useAuthContext} from "../../context/AuthContext.tsx";


interface PatientsTableBodyProps {
    patients: Patient[];
    handleDelete: (patientID: string) => Promise<void>;
}

const PatientsTableBody: React.FC<PatientsTableBodyProps> = ({ patients, handleDelete }) => {
    const { userRole } = useAuthContext();
    if (!patients.length) {
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
            {patients.map((patient) => (
                <tr key={patient.patientID}>
                    <td>{patient.lastName}</td>
                    <td>{patient.firstName}</td>
                    <td>{patient.email}</td>
                    <td>{patient.amka}</td>
                    <td>{patient.personalID}</td>
                    <td>{patient.registrationDate || 'N/A'}</td>
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
