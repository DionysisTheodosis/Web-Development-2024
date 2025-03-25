// import { keepPreviousData, useQuery } from '@tanstack/react-query'
// import { PatientSearchParams, PatientInfoDto, PagedResponse } from "./types";
//
// const [page, setPage] = React.useState(0)
//
// const fetchProjects = (page = 0) =>
//     fetch('/api/projects?page=' + page).then((res) => res.json())
//
// const { isPending, isError, error, data, isFetching, isPlaceholderData } =
//     useQuery({
//       queryKey: ['projects', page],
//       queryFn: () => fetchProjects(page),
//       placeholderData: keepPreviousData,
//     })
//
// export default usePatients;
