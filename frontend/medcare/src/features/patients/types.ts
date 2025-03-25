export type PatientSearchParams = {
    lastName?: string;
    amka?: string;
    page?: number;
    sizePerPage?: number;
    sortBy?: string;
    sortOrder?: "asc" | "desc";
};

export type PagedResponse<T> = {
    content: T[];
    totalElements: number;
    totalPages: number;
    size: number;
    number: number;
};
export type Patient = {
    patientID: string;
    lastName: string;
    firstName: string;
    email: string;
    amka: string;
    personalID: string;
    medicalHistoryID: number;
    registrationDate: string;
}

export type FetchPatientsResponse = {
    content: Patient[];
    totalPages: number;
    totalElements: number;
    sizePerPage: number;
};
export type PatientFilters = {
    page: number | undefined;
    sizePerPage: number | undefined;
    sortBy: string | undefined;
    sortOrder: 'asc' | 'desc'| undefined;
    lastNameFilter: string | undefined;
    amkaFilter: string | undefined;
};