// import React, { useState } from "react";
// import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
// import axios from "axios";
// import ReusableModal from "../../components/ReusableModal";
// import DataTable.tsx from "./DataTable.tsx";
//
// interface Slot {
//     id: number;
//     slotDate: string;
//     slotTime: string;
//     duration: number;
// }
//
// const fetchSlots = async (): Promise<Slot[]> => {
//     const response = await axios.get<Slot[]>("/api/v1/doctor-availability");
//     return response.data;
// };
//
// const DoctorAvailabilityPage: React.FC = () => {
//     const queryClient = useQueryClient();
//     const [showModal, setShowModal] = useState(false);
//     const [modalContent, setModalContent] = useState<{ title: string; body: React.ReactNode | null }>({ title: "", body: null });
//     const [editingSlot, setEditingSlot] = useState<Partial<Slot> | null>(null);
//     const [newSlot, setNewSlot] = useState<Partial<Slot>>({});
//
//     // Fixed useQuery syntax
//     const { data: slots = [], isLoading, isError } = useQuery<Slot[]>({
//         queryKey: ["slots"],
//         queryFn: fetchSlots
//     });
//
//
//     const deleteSlotMutation = useMutation({
//         mutationFn: async (id: number) => {
//             await axios.delete(`/api/v1/doctor-availability/${id}`);
//         },
//         onSuccess: () => {
//             queryClient.invalidateQueries({ queryKey: ["slots"] });
//         },
//     });
//
//     const saveSlotMutation = useMutation({
//         mutationFn: async (slot: Partial<Slot>) => {
//             if (slot.id) {
//                 await axios.put(`/api/v1/doctor-availability/${slot.id}`, slot);
//             } else {
//                 await axios.post("/api/v1/doctor-availability", slot);
//             }
//         },
//         onSuccess: () => {
//             queryClient.invalidateQueries({ queryKey: ["slots"] });
//             setShowModal(false);
//         },
//     });
//
//     const handleEditSlot = (slot: Slot) => {
//         setEditingSlot(slot);
//         setModalContent({
//             title: "Edit Slot",
//             body: (
//                 <div>
//                     <label>Slot Date & Time</label>
//                     <input
//                         type="datetime-local"
//                         defaultValue={`${slot.slotDate}T${slot.slotTime}`}
//                         className="form-control"
//                         onChange={(e) => setEditingSlot(prev => ({
//                             ...prev,
//                             slotDate: e.target.value.split('T')[0],
//                             slotTime: e.target.value.split('T')[1]
//                         }))}
//                     />
//                     <label>Duration</label>
//                     <input
//                         type="number"
//                         defaultValue={slot.duration}
//                         className="form-control"
//                         onChange={(e) => setEditingSlot(prev => ({
//                             ...prev,
//                             duration: parseInt(e.target.value) || 0
//                         }))}
//                     />
//                     <button
//                         className="btn btn-success mt-3"
//                         onClick={() => editingSlot && saveSlotMutation.mutate(editingSlot)}
//                     >
//                         Save
//                     </button>
//                 </div>
//             ),
//         });
//         setShowModal(true);
//     };
//
//     const handleAddSlot = () => {
//         setNewSlot({});
//         setModalContent({
//             title: "Add New Slot",
//             body: (
//                 <div>
//                     <label>Slot Date & Time</label>
//                     <input
//                         type="datetime-local"
//                         className="form-control"
//                         onChange={(e) => setNewSlot(prev => ({
//                             ...prev,
//                             slotDate: e.target.value.split('T')[0],
//                             slotTime: e.target.value.split('T')[1]
//                         }))}
//                     />
//                     <label>Duration</label>
//                     <input
//                         type="number"
//                         className="form-control"
//                         onChange={(e) => setNewSlot(prev => ({
//                             ...prev,
//                             duration: parseInt(e.target.value) || 0
//                         }))}
//                     />
//                     <button
//                         className="btn btn-primary mt-3"
//                         onClick={() => saveSlotMutation.mutate(newSlot)}
//                     >
//                         Add Slot
//                     </button>
//                 </div>
//             ),
//         });
//         setShowModal(true);
//     };
//
//     const handleDeleteSlot = (id: number) => {
//         if (window.confirm("Are you sure you want to delete this slot?")) {
//             deleteSlotMutation.mutate(id);
//         }
//     };
//
//     if (isLoading) return <div>Loading...</div>;
//     if (isError) return <div>Error loading slots</div>;
//
//     return (
//         <div className="container mt-4">
//             <h2 className="text-center">Doctor Availability Slots</h2>
//             <button className="btn btn-primary mb-3" onClick={handleAddSlot}>
//                 Add Slot
//             </button>
//             <DataTable.tsx
//                 columns={["Date", "Time", "Duration (seconds)", "Actions"]}
//                 rows={[...(slots as Slot[])].sort((a, b) => a.slotDate.localeCompare(b.slotDate))}
//                 rowsPerPage={5}
//                 actions={(row: Slot) => (
//                     <>
//                         <button
//                             className="btn btn-warning btn-sm me-2"
//                             onClick={() => handleEditSlot(row)}
//                         >
//                             Edit
//                         </button>
//                         <button
//                             className="btn btn-danger btn-sm"
//                             onClick={() => handleDeleteSlot(row.id)}
//                         >
//                             Delete
//                         </button>
//                     </>
//                 )}
//             />
//             <ReusableModal
//                 show={showModal}
//                 title={modalContent.title}
//                 onClose={() => setShowModal(false)}
//                 body={modalContent.body}
//             />
//         </div>
//     );
// };
//
// export default DoctorAvailabilityPage;