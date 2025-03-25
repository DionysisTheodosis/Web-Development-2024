export type PatientInfoDto = {
    patientID: number;
    firstName: string;
    lastName: string;
    email: string;
    amka: string;
    personalID: string;
    medicalHistoryID: number;
    registrationDate: string;
};

export interface PatientSearchParams {
    lastName?: string;
    amka?: string;
    page?: number;
    sizePerPage?: number;
    sortBy?: string;
    sortOrder?: "asc" | "desc"; // ✅ Fix: Explicitly restrict to "asc" | "desc"
}

export type PagedResponse<T> = {
    content: T[];
    totalElements: number;
    totalPages: number;
    size: number;
    number: number;
};
