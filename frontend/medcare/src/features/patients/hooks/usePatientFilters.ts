import { useCallback } from 'react';
import { useSearchParams } from 'react-router';
import { PatientFilters } from '../types';

export const usePatientFilters = () => {
    const [searchParams, setSearchParams] = useSearchParams();

    // Extract the current filter values from the search params
    const page = parseInt(searchParams.get('page') || '0', 10);
    const sizePerPage = parseInt(searchParams.get('sizePerPage') || '10', 10);
    const sortBy = searchParams.get('sortBy') || 'lastName';
    const sortOrder = searchParams.get('sortOrder') as PatientFilters['sortOrder'] || 'asc';
    const lastNameFilter = searchParams.get('lastName') || '';
    const amkaFilter = searchParams.get('amka') || '';

    // Callback to update filters and synchronize with URL
    const setFilters = useCallback((filters: PatientFilters) => {
        setSearchParams((params) => {
            // Only set parameters if the value is defined (not undefined)
            if (filters.page !== undefined) {
                params.set('page', filters.page.toString());
            }
            if (filters.sizePerPage !== undefined) {
                params.set('sizePerPage', filters.sizePerPage.toString());
            }
            if (filters.sortBy !== undefined) {
                params.set('sortBy', filters.sortBy);
            }
            if (filters.sortOrder !== undefined) {
                params.set('sortOrder', filters.sortOrder);
            }
            if (filters.lastNameFilter !== undefined) {
                params.set('lastName', filters.lastNameFilter);
            }
            if (filters.amkaFilter !== undefined) {
                params.set('amka', filters.amkaFilter);
            }

            return params;
        });
    }, []);

    return {
        page,
        sortOrder,
        sortBy,
        lastNameFilter,
        amkaFilter,
        sizePerPage,
        setFilters,
    };
};
