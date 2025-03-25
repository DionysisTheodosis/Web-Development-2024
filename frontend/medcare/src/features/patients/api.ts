import api from "../../api.ts";
import {PagedResponse, Patient, PatientSearchParams} from "./types.ts";



export const fetchPatients = async (params: PatientSearchParams): Promise<PagedResponse<Patient>> => {
    const response = await api.get<PagedResponse<Patient>>("/pageable/search", { params });
    return response.data;
};
