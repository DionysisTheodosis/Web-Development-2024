import React, { useState } from "react";
import { useQuery, useQueryClient} from '@tanstack/react-query';
import axios from 'axios';
import PatientsTableBody from './PatientsTableBody';
import { FetchPatientsResponse } from './types';

// Modified fetchPatients to accept a single object of parameters
const fetchPatients = async (params: {
    page: number;
    sizePerPage: number;
    sortBy: string;
    sortOrder: string;
    lastNameFilter: string;
    amkaFilter: string;
}): Promise<FetchPatientsResponse> => {
    const queryParams: any = {
        page: params.page,
        size: params.sizePerPage,
        sort: `${params.sortBy},${params.sortOrder}`,
    };
    if (params.lastNameFilter) queryParams.lastName = params.lastNameFilter;
    if (params.amkaFilter) queryParams.amka = params.amkaFilter;
    if(params.sizePerPage) queryParams.sizePerPage = params.sizePerPage;
    if(params.sortBy) queryParams.sortBy = params.sortBy;
    if(params.sortOrder) queryParams.sortOrder = params.sortOrder;
    if(params.page) queryParams.page = params.page;
    const { data } = await axios.get('/api/v1/patients/pageable/search', { params: queryParams });
    console.log(data);
    return {
        content: data.content,
        sizePerPage: data.size,
        totalPages: data.totalPages,
        totalElements: data.totalElements,
    };
};

// Custom hook that encapsulates the query parameters and fetching logic
const usePatientsQuery = (initialParams: {
    page: number;
    sizePerPage: number;
    sortBy: string;
    sortOrder: string;
    lastNameFilter: string;
    amkaFilter: string;
}) => {
    // The UI state (params) is handled inside this hook
    const [params, setParams] = useState(initialParams);
    const query = useQuery({
        queryKey: ['patients', params],
        queryFn: () => fetchPatients(params),
        placeholderData: {
            content: [],
            totalPages: 0,
            totalElements: 0,
            sizePerPage: initialParams.sizePerPage
        },
    });
    return { ...query, params, setParams };
};

const PatientsTable: React.FC = () => {
    const queryClient = useQueryClient();
    // Use the custom hook without worrying about URL search params or component-level state
    const { data, error, isLoading, params, setParams } = usePatientsQuery({
        page: 0,
        sizePerPage: 10,
        sortBy: 'lastName',
        sortOrder: 'asc',
        lastNameFilter: '',
        amkaFilter: '',
    });

    // Handlers update the parameters via setParams; the queryKey changes and triggers a refetch
    const handleSort = (column: string) => {
        setParams(prev => {
            const newSortOrder = prev.sortBy === column && prev.sortOrder === 'asc' ? 'desc' : 'asc';
            return { ...prev, sortBy: column, sortOrder: newSortOrder, page: 0 };
        });
        queryClient.invalidateQueries(({queryKey:['patients']}));
    };

    const handlePageChange = (newPage: number) => {
        setParams(prev => ({ ...prev, page: newPage }));
        queryClient.invalidateQueries({queryKey:['patients']});
    };

    const handleSizePerPageChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
        const size = parseInt(event.target.value, 10);
        setParams(prev => ({ ...prev, sizePerPage: size, page: 0 }));
        queryClient.invalidateQueries({queryKey:['patients']});
    };

    const handleLastNameFilterChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setParams(prev => ({ ...prev, lastNameFilter: event.target.value, page: 0 }));
        queryClient.invalidateQueries({queryKey:['patients']});
    };

    const handleAmkaFilterChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setParams(prev => ({ ...prev, amkaFilter: event.target.value, page: 0 }));
        queryClient.invalidateQueries({queryKey:['patients']});
    };

    const handleDelete = async (patientID: string) => {
        try {
            await axios.delete(`/api/v1/patients/${patientID}`);
            alert('Patient deleted successfully.');
            queryClient.invalidateQueries({queryKey:['patients']});
        } catch {
            alert('Failed to delete patient.');
        }
    };

    if (isLoading) return <div>Loading...</div>;
    if (error) return <div>Error loading data</div>;

    return (
        <div className="container my-4">
            <div className="card p-2">
                <div className="card-header">
                    <h2 className="h4">Patients</h2>
                </div>
                <div className="row mt-2 justify-content-between">
                    <div className="col-md-auto">
                        <label>
                            <select
                                className="form-select form-select-sm"
                                value={params.sizePerPage}
                                onChange={handleSizePerPageChange}
                            >
                                {[2,5, 10, 25, 50, 100].map((size) => (
                                    <option key={size} value={size}>
                                        {size}
                                    </option>
                                ))}
                            </select>
                            Entries per page
                        </label>
                    </div>
                    <div className="col-md-auto">
                        <label>
                            Filter by AMKA:
                            <input
                                type="text"
                                className="form-control form-control-sm"
                                value={params.amkaFilter}
                                onChange={handleAmkaFilterChange}
                                placeholder="Enter AMKA"
                            />
                        </label>
                    </div>
                    <div className="col-md-auto">
                        <label>
                            Filter by Last Name:
                            <input
                                type="text"
                                className="form-control form-control-sm"
                                value={params.lastNameFilter}
                                onChange={handleLastNameFilterChange}
                                placeholder="Last Name"
                            />
                        </label>
                    </div>
                </div>
                <div className="table-responsive mt-2">
                    <table className="table table-hover table-sm">
                        <thead>
                        <tr>
                            {['lastName', 'firstName', 'email', 'amka', 'personalID', 'registrationDate'].map((col) => (
                                <th key={col} className="sortable" onClick={() => handleSort(col)}>
                                    {col.replace(/([A-Z])/g, ' $1')}{' '}
                                    <i
                                        className={`fa ${
                                            params.sortBy === col
                                                ? params.sortOrder === 'asc'
                                                    ? 'fa-sort-up'
                                                    : 'fa-sort-down'
                                                : 'fa-sort'
                                        }`}
                                    />
                                </th>
                            ))}
                            <th>Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        <PatientsTableBody
                            key={`${params.page}-${params.sizePerPage}-${params.sortBy}-${params.sortOrder}`}
                            patients={data?.content || []}
                            handleDelete={handleDelete}
                        />
                        </tbody>
                    </table>
                </div>
                <div className="row mt-2 justify-content-between">
                    <div className="col-md-auto">
                        Showing {Math.min(params.page * params.sizePerPage + 1, data?.totalElements || 0)} to{' '}
                        {Math.min((params.page + 1) * params.sizePerPage, data?.totalElements || 0)} of {data?.totalElements} entries
                    </div>
                    <div className="col-md-auto">
                        <nav>
                            <ul className="pagination">
                                <li className={`page-item ${params.page === 0 ? 'disabled' : ''}`}>
                                    <button className="page-link" onClick={() => handlePageChange(0)}>
                                        &laquo;
                                    </button>
                                </li>
                                <li className={`page-item ${params.page === 0 ? 'disabled' : ''}`}>
                                    <button className="page-link" onClick={() => handlePageChange(params.page - 1)}>
                                        &lsaquo;
                                    </button>
                                </li>
                                {Array.from({ length: data?.totalPages || 1 }, (_, i) => (
                                    <li key={i} className={`page-item ${i === params.page ? 'active' : ''}`}>
                                        <button className="page-link" onClick={() => handlePageChange(i)}>
                                            {i + 1}
                                        </button>
                                    </li>
                                ))}
                                <li className={`page-item ${params.page === (data?.totalPages || 1) - 1 ? 'disabled' : ''}`}>
                                    <button className="page-link" onClick={() => handlePageChange(params.page + 1)}>
                                        &rsaquo;
                                    </button>
                                </li>
                                <li className={`page-item ${params.page === (data?.totalPages || 1) - 1 ? 'disabled' : ''}`}>
                                    <button className="page-link" onClick={() => handlePageChange((data?.totalPages || 1) - 1)}>
                                        &raquo;
                                    </button>
                                </li>
                            </ul>
                        </nav>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default PatientsTable;
