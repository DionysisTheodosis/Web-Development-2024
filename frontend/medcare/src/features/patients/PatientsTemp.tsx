// import { useQuery, UseQueryResult } from '@tanstack/react-query';
// import axios from 'axios';
// import PatientsTableBody from './PatientsTableBody';
// import React, { useEffect, useState } from 'react';
// import { FetchPatientsResponse, PatientFilters } from './types.ts';
// import { usePatientFilters } from './hooks/usePatientFilters.ts';
// import { useDebounce } from '../../hooks/useDebounce.ts';
//
// const fetchPatients = async (
//     page = 0,
//     sizePerPage = 10,
//     sortBy = 'lastName',
//     sortOrder = 'asc',
//     lastNameFilter = '',
//     amkaFilter = ''
// ): Promise<FetchPatientsResponse> => {
//     const params: any = {
//         page,
//         size: sizePerPage,
//         sort: `${sortBy},${sortOrder}`,
//     };
//
//     if (lastNameFilter) params.lastName = lastNameFilter;
//     if (amkaFilter) params.amka = amkaFilter;
//
//     const { data } = await axios.get('/api/v1/patients/pageable/search', { params });
//     return {
//         content: data.content,
//         sizePerPage: data.size,
//         totalPages: data.totalPages,
//         totalElements: data.totalElements,
//     };
// };
//
// const PatientsTable: React.FC = () => {
//     const { page = 0, sizePerPage = 10, sortBy = 'lastName', sortOrder = 'asc', lastNameFilter, amkaFilter, setFilters } = usePatientFilters();
//
//     // Local states for filters
//     const [localAmkaSearch, setLocalAmkaSearch] = useState<PatientFilters['amkaFilter']>(amkaFilter || '');
//     const [localLastNameSearch, setLocalLastNameSearch] = useState<PatientFilters['lastNameFilter']>(lastNameFilter || '');
//
//     // Apply debouncing
//     const debouncedAmkaSearch = useDebounce(localAmkaSearch);
//     const debouncedLastNameSearch = useDebounce(localLastNameSearch);
//
//     useEffect(() => {
//         setFilters({
//             page, // Keep the current page value
//             sizePerPage, // Keep the current sizePerPage value
//             sortBy, // Keep the current sortBy value
//             sortOrder, // Keep the current sortOrder value
//             amkaFilter: debouncedAmkaSearch, // Set the new debounced AMKA filter
//             lastNameFilter: debouncedLastNameSearch, // Set the new debounced Last Name filter
//         });
//     }, [debouncedAmkaSearch, debouncedLastNameSearch]);
//
//
//     const { data, error, isLoading }: UseQueryResult<FetchPatientsResponse, Error> = useQuery({
//         queryKey: ['patients', page, sizePerPage, sortBy, sortOrder, debouncedLastNameSearch, debouncedAmkaSearch],
//         queryFn: () => fetchPatients(page, sizePerPage, sortBy, sortOrder, debouncedLastNameSearch, debouncedAmkaSearch),
//         refetchOnWindowFocus: false,
//         placeholderData: { content: [], totalPages: 0, totalElements: 0, sizePerPage: 10 },
//     });
//
//     const handleSort = (column: string) => {
//         const newSortOrder = sortBy === column && sortOrder === 'asc' ? 'desc' : 'asc';
//         setFilters({
//             page: 0, // Reset to the first page on sorting change
//             sizePerPage, // Keep the same sizePerPage
//             sortBy: column, // Update the sorting column
//             sortOrder: newSortOrder, // Update the sort order
//             lastNameFilter, // Keep the existing filter
//             amkaFilter, // Keep the existing filter
//         });
//     };
//
//     const handlePageChange = (newPage: number) => {
//         setFilters({
//             page: newPage, // Update the page
//             sizePerPage, // Keep the existing sizePerPage
//             sortBy, // Keep the existing sortBy
//             sortOrder, // Keep the existing sortOrder
//             lastNameFilter, // Keep the existing filter
//             amkaFilter, // Keep the existing filter
//         });
//     };
//
//     const handleSizePerPageChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
//         const newSizePerPage = parseInt(event.target.value, 10);
//         setFilters({
//             page: 0, // Reset to the first page when size changes
//             sizePerPage: newSizePerPage, // Update the entries per page
//             sortBy, // Keep the existing sortBy
//             sortOrder, // Keep the existing sortOrder
//             lastNameFilter, // Keep the existing filter
//             amkaFilter, // Keep the existing filter
//         });
//     };
//
//
//     const handleDelete = async (patientID: string) => {
//         try {
//             await axios.delete(`/api/v1/patients/${patientID}`);
//             alert('Patient deleted successfully.');
//         } catch {
//             alert('Failed to delete patient.');
//         }
//     };
//
//     if (isLoading) return <div>Loading...</div>;
//     if (error) return <div>Error loading data</div>;
//
//     return (
//         <div className="container my-4">
//             <div className="card p-2">
//                 <div className="card-header">
//                     <h2 className="h4">Patients</h2>
//                 </div>
//                 <div className="row mt-2 justify-content-between">
//                     <div className="col-md-auto">
//                         <label>
//                             <select
//                                 className="form-select form-select-sm"
//                                 value={sizePerPage}
//                                 onChange={handleSizePerPageChange}
//                             >
//                                 {[5, 10, 25, 50, 100].map((size) => (
//                                     <option key={size} value={size}>
//                                         {size}
//                                     </option>
//                                 ))}
//                             </select>
//                             Entries per page
//                         </label>
//                     </div>
//                     <div className="col-md-auto">
//                         <label>
//                             Filter by AMKA:
//                             <input
//                                 type="text"
//                                 className="form-control form-control-sm"
//                                 value={localAmkaSearch}
//                                 onChange={(e) => setLocalAmkaSearch(e.target.value)}
//                                 placeholder="Enter AMKA"
//                             />
//                         </label>
//                     </div>
//                     <div className="col-md-auto">
//                         <label>
//                             Filter by Last Name:
//                             <input
//                                 type="text"
//                                 className="form-control form-control-sm"
//                                 value={localLastNameSearch}
//                                 onChange={(e) => setLocalLastNameSearch(e.target.value)}
//                                 placeholder="Last Name"
//                             />
//                         </label>
//                     </div>
//                 </div>
//                 <div className="table-responsive mt-2">
//                     <table className="table table-hover table-sm">
//                         <thead>
//                         <tr>
//                             {['lastName', 'firstName', 'email', 'amka', 'personalID', 'registrationDate'].map((col) => (
//                                 <th key={col} className="sortable" onClick={() => handleSort(col)}>
//                                     {col.replace(/([A-Z])/g, ' $1')}{' '}
//                                     <i
//                                         className={`fa ${sortBy === col ? (sortOrder === 'asc' ? 'fa-sort-up' : 'fa-sort-down') : 'fa-sort'}`}
//                                     />
//                                 </th>
//                             ))}
//                             <th>Actions</th>
//                         </tr>
//                         </thead>
//                         <tbody>
//                         <PatientsTableBody
//                             key={`${page}-${sizePerPage}-${sortBy}-${sortOrder}`}
//                             patients={data?.content || []}
//                             handleDelete={handleDelete}
//                         />
//                         </tbody>
//                     </table>
//                 </div>
//                 <div className="row mt-2 justify-content-between">
//                     <div className="col-md-auto">
//                         Showing {Math.min(page * sizePerPage + 1, data?.totalElements || 0)} to{' '}
//                         {Math.min((page + 1) * sizePerPage, data?.totalElements || 0)} of {data?.totalElements} entries
//                     </div>
//                     <div className="col-md-auto">
//                         <nav>
//                             <ul className="pagination">
//                                 <li className={`page-item ${page === 0 ? 'disabled' : ''}`}>
//                                     <button className="page-link" onClick={() => handlePageChange(0)}>
//                                         &laquo;
//                                     </button>
//                                 </li>
//                                 <li className={`page-item ${page === 0 ? 'disabled' : ''}`}>
//                                     <button className="page-link" onClick={() => handlePageChange(page - 1)}>
//                                         &lsaquo;
//                                     </button>
//                                 </li>
//                                 {Array.from({ length: data?.totalPages || 1 }, (_, i) => (
//                                     <li key={i} className={`page-item ${i === page ? 'active' : ''}`}>
//                                         <button className="page-link" onClick={() => handlePageChange(i)}>
//                                             {i + 1}
//                                         </button>
//                                     </li>
//                                 ))}
//                                 <li className={`page-item ${page === (data?.totalPages || 1) - 1 ? 'disabled' : ''}`}>
//                                     <button className="page-link" onClick={() => handlePageChange(page + 1)}>
//                                         &rsaquo;
//                                     </button>
//                                 </li>
//                                 <li className={`page-item ${page === (data?.totalPages || 1) - 1 ? 'disabled' : ''}`}>
//                                     <button className="page-link" onClick={() => handlePageChange((data?.totalPages || 1) - 1)}>
//                                         &raquo;
//                                     </button>
//                                 </li>
//                             </ul>
//                         </nav>
//                     </div>
//                 </div>
//             </div>
//         </div>
//     );
// };
//
// export default PatientsTable;
