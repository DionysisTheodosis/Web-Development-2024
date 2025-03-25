//
// <div class="container mt-4">
//     <section class="modal fade" id="availabilityModal" tabindex="-1">
//         <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
//             <div class="modal-content">
//                 <div class="modal-header">
//                     <h5 class="modal-title"></h5>
//                     <button
//                         type="button"
//                         class="btn-close"
//                         data-bs-dismiss="modal"
//                         aria-label="Close"
//                     ></button>
//                 </div>
//                 <div class="modal-body">
//                     <!-- dynamically load modal-body -->
//                 </div>
//             </div>
//         </div>
//     </section>
//     <section class="slot-table">
//         <div class="card justify-content-center m-2 p-1">
//             <div class="card-header">
//                 <h2 class="h4 text-center">Doctor Availability Slots</h2>
//             </div>
//             <div class="row mt-2 justify-content-between">
//                 <div class="column dt-length col-md-auto me-auto">
//                     <div class="input-group date-picker-container">
//                         <button id="todayBtn" class="btn btn-primary">Today</button>
//                         <input
//                             type="text"
//                             id="datePicker"
//                             class="form-control flatpickr-input"
//                             placeholder="Select a date"
//                         />
//                     </div>
//                 </div>
//                 <div
//                     class="d-md-flex justify-content-between align-items-center dt-layout-start col-md-auto ms-auto"
//                 >
//                     <button
//                         class="btn btn-success"
//                         data-bs-toggle="modal"
//                         data-bs-target="#availabilityModal"
//                     >
//                         <i class="fa fa-plus"></i>
//                         <i class="fa fa-calendar"></i>
//                     </button>
//                 </div>
//             </div>
//             <div class="row mt-2 justify-content-between">
//                 <div class="table-responsive" id="availability-table">
//                     <table class="table table-hover table-sm">
//                         <thead>
//                         <tr>
//                             <th data-column="slotDate" data-title="Slot Date">
//                                 <div class="column-header" style="white-space: nowrap">
//                                     Date
//                                 </div>
//                             </th>
//                             <th data-column="slotTime" data-title="Slot Time">
//                                 <div class="column-header" style="white-space: nowrap">
//                                     Time
//                                 </div>
//                             </th>
//                             <th data-column="slotDuration" data-title="Slot Duration">
//                                 <div class="column-header" style="white-space: nowrap">
//                                     Duration (Seconds)
//                                 </div>
//                             </th>
//                             <th data-column="slotActions" data-title="Slot Actions">
//                                 <div class="column-header" style="white-space: nowrap">
//                                     Actions
//                                 </div>
//                             </th>
//                         </tr>
//                         </thead>
//                         <tbody id="availability-table-body">
//                         <!-- Data will be loaded dynamically -->
//                         </tbody>
//                     </table>
//                 </div>
//             </div>
//         </div>
//     </section>
// </div>
// <footer class="text-center py-4" id="footer"></footer>
// <script type="module">
//     let slots;
//     import { DateUtil } from "../scripts/util.js";
//     import {
//     Slot,
//     updateSlot,
//     deleteSlot,
//     getSlotsByDate,
// } from "../Doctor/scripts/availability.js";
//
//     const API_BASE = "/api/v1/doctor-availability";
//
//     async function fetchDoctorAvailability(
//     date = DateUtil.toGreekDate(new Date())
//     ) {
//     try {
//     const formattedDate = DateUtil.toGreekDate(date);
//     slots = await getSlotsByDate(formattedDate);
//     populateTable(slots);
// } catch (error) {
//     console.error("Error fetching doctor availability:", error);
// }
// }
//
//     function populateTable(slots) {
//     const tableBody = $("#availability-table-body");
//     tableBody.empty();
//
//     if (!slots || slots.length === 0) {
//     tableBody.append(
//     `<tr><td colspan="4" class="text-center">No availability found</td></tr>`
//     );
//     return;
// }
//
//     slots.forEach((slot) => {
//     tableBody.append(`
// 											<tr>
// 											<td data-title="slot Date">${DateUtil.toGreekDate(slot.slotDateTime)}</td>
// 											<td data-title="slot Time">${DateUtil.toGreekTime(slot.slotDateTime)}</td>
// 											<td data-title="slot Duration">${slot.duration}</td>
// 											<td data-title="slot Actions">
// 												<button class="btn btn-sm btn-danger delete-slot-btn"  data-id="${slot.id}">
// 												<i class="fa fa-trash"></i>
// 												</button>
// 												<button class="btn btn-sm btn-warning edit-slot-btn" data-bs-toggle="modal" data-bs-target="#availabilityModal" id="edit-slot-btn" data-id="${
//     slot.id
// }" data-type="slot">
// 			   									 <i class="fa fa-edit"></i>
// 												</button>
// 											</td>
// 											</tr>
// 			     				 `);
// });
// }
//
//     $(document).ready(function () {
//     $("#datePicker").flatpickr({
//         dateFormat: "Y-m-d",
//         minDate: "today",
//         defaultDate: DateUtil.toGreekDate(new Date()),
//         onChange: function (selectedDates, dateStr) {
//             fetchDoctorAvailability(dateStr);
//         },
//     });
//
//     $("#todayBtn").click(function () {
//     const today = DateUtil.toGreekDate(new Date());
//     $("#datePicker").val(today);
//     fetchDoctorAvailability(today);
// });
//
//     fetchDoctorAvailability(DateUtil.toGreekDate(new Date()));
// });
//
//     $(document).on(
//     "click",
//     "[data-bs-target='#availabilityModal']",
//     function () {
//     let slotID = $(this).data("id");
//     let slot = slots.find((slot) => slot.id === slotID);
//     console.log(slot);
//     let $modalBody = $("#availabilityModal .modal-body");
//     $modalBody.empty();
//     $("#availabilityModal .modal-title").text("Update availability slot");
//     let actionType = $(this).data("type");
//
//     if (actionType === "slot") {
//     let $slotUpdateForm = `
//             <div class="card justify-content-center m-2 p-3">
//                 <h4 class="text-center">Update Slot</h4>
//                 <div class="row">
//                     <div class="col-md-6">
//                         <label for="slotDateTime" class="form-label">Slot Date & Time:</label>
//                         <input
//                             id="slotDateTime"
//                             type="datetime-local"
//                             class="form-control datetime-picker"
//                             placeholder="Pick a date and time"
//                             required
//                         />
//                     </div>
//                     <div class="col-md-6">
//                         <label for="slotDuration" class="form-label">Duration (Seconds):</label>
//                         <input
//                             type="number"
//                             id="slotDuration"
//                             class="form-control"
//                             value="${slot.duration}"
//                             min="1"
//                             required
//                         />
//                     </div>
//                     <div class="col-md-12 mt-3">
//                         <button class="btn btn-success w-100" data-id="${slot.id}" id="updateSlotBtn">
//                             <i class="fas fa-save"></i> Save Changes
//                         </button>
//                     </div>
//                 </div>
//             </div>
//         `;
//     $modalBody.append($slotUpdateForm);
//
//     $(".datetime-picker").flatpickr({
//     enableTime: true,
//     dateFormat: "Y-m-d H:i",
//     time_24hr: true,
//     minDate: "today",
//     minTime: getMinTime(),
//     defaultDate: DateUtil.toGreekISODateTime(slot.slotDateTime),
// });
// } else {
//     const defaultModalContent = `
// 											<!-- Options to choose how to add availabilty slots -->
// 											<div id="availabilityOptions" class="text-center">
// 												<h3>Pick the method to add Availability slot/slots?</h3>
// 												<button id="addOneSlotBtn" class="btn btn-primary m-2">Add 1 Availability Slot</button>
// 												<button id="addMultipleSlotsBtn" class="btn btn-secondary m-2">Add Multiple Availability Slots via File</button>
// 											</div>
// 									 			`;
//     $("#availabilityModal .modal-title").text(
//     "Add New Availability Date And Time"
//     );
//
//     $(".modal-body").empty().append(defaultModalContent);
// }
//
//     $("#availabilityModal").modal("show");
// }
//     );
//
//     $(document).on("click", "#addOneSlotBtn", function () {
//     let $modalBody = $(".modal-body");
//     $modalBody.empty();
//     $("#availabilityModal .modal-title").text(
//     "Add New Availability Date And Time"
//     );
//     let $slotForm = $(`
// 			        <div class="card justify-content-center m-2 p-3">
// 			            <h4 class="text-center">Add New Availability Slot</h4>
// 			            <div class="row">
// 			                <div class="col-md-6">
// 			                    <label for="slotDateTime" class="form-label">Slot Date & Time:</label>
// 			                    <input
// 			                        id="slotDateTime"
// 			                        type="datetime-local"
// 			                        class="form-control datetime-picker"
// 			                        placeholder="Pick a date and time"
// 			                        required
// 			                    />
// 			                </div>
// 			                <div class="col-md-6">
// 			                    <label for="slotDuration" class="form-label">Duration (Seconds):</label>
// 			                    <input
// 			                        type="number"
// 			                        id="slotDuration"
// 			                        class="form-control"
// 			                        value="30"
// 			                        min="1"
// 			                        required
// 			                    />
// 			                </div>
// 			                <div class="col-md-12 mt-3">
// 			                    <button class="btn btn-success w-100" id="saveSlotBtn">
// 			                        <i class="fas fa-save"></i> Save Slot
// 			                    </button>
// 			                </div>
// 			            </div>
// 			        </div>
// 			    `);
//
//     $modalBody.append($slotForm);
//     $(".datetime-picker").flatpickr({
//     enableTime: true,
//     dateFormat: "Y-m-d H:i",
//     time_24hr: true,
//     minDate: "today",
//     minTime: getMinTime(),
//     onChange: function (selectedDates, dateStr, instance) {
//     if (
//     selectedDates.length > 0 &&
//     selectedDates[0].toDateString() === new Date().toDateString()
//     ) {
//     instance.set("minTime", getMinTime());
// } else {
//     instance.set("minTime", null);
// }
// },
// });
// });
//     $(document).on("click", "#addMultipleSlotsBtn", function () {
//     let $modalBody = $(".modal-body");
//
//     $modalBody.empty();
//     $("#availabilityModal .modal-title").text(
//     "Add New Availability Date And Time"
//     );
//     let $formContainer = $("<div>", {
//     id: "addMultipleSlotsForm",
//     class: "add-slots-form",
// });
//
//     $formContainer.append(`
// 													<h5>Upload a File with Slot Information</h5>
// 													<p class="text-muted">Use this form to upload a CSV or Excel file containing slot details. The uploaded file will be used to update the slot records in the system.</p>
//
// 													<!-- File input section (top section for quick access) -->
// 													<div class="mb-3">
// 														<label for="slotFile" class="form-label">Slot Information File</label>
// 														<div class="input-group">
// 															<input type="file" id="slotFile" class="form-control" accept=".csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel" />
// 															<button class="btn btn-primary" id="uploadFileBtn">
// 																<i class="fas fa-upload"></i>
// 															</button>
// 														</div>
// 														<div class="form-text">
// 															<strong>Accepted file formats:</strong> CSV or Excel (XLSX or XLS).
// 														</div>
// 													</div>
//
// 													<!-- Response message area -->
// 													<div id="fileResponseMessage" class="mt-3 text-center"></div>
//
// 													<!-- Description section (move to the bottom for clarity) -->
// 													<hr> <!-- Optional divider between upload and description -->
// 													<h6>File Structure:</h6>
// 													<p class="text-muted">Please ensure the file has the following columns:</p>
//
// 													<!-- Structure example as a table -->
// 													<div class="table-responsive">
// 														<table class="table table-bordered">
// 															<thead>
// 																<tr>
// 																	<th>Column Name</th>
// 																	<th>Example Data</th>
// 																	<th>Description</th>
// 																</tr>
// 															</thead>
// 															<tbody>
// 																<tr>
// 																	<td>Date</td>
// 																	<td>2025-02-01</td>
// 																	<td>Date when the slot is available (format: YYYY-MM-DD)</td>
// 																</tr>
// 																<tr>
// 																	<td>Time</td>
// 																	<td>14:40:52</td>
// 																	<td>Time when the slot is available (format: HH:mm:ss)</td>
// 																</tr>
// 																<tr>
// 																	<td>Duration</td>
// 																	<td>30</td>
// 																	<td>Duration in minutes for the slot (optional)</td>
// 																</tr>
// 															</tbody>
// 														</table>
// 													</div>
//
// 													<p class="text-muted">Ensure that your file has these columns in the order specified. Each row should represent a single slot.</p>
//
// 													<!-- Link to download sample files -->
// 													<h6>Download Sample Files:</h6>
// 													<p>Download a sample CSV or Excel file with the correct structure:</p>
// 													<ul>
// 														<li><a href="../doctor/doctor-availability/files/slots_demo.csv" download class="btn btn-link">Download Sample CSV</a></li>
// 														<li><a href="../doctor/doctor-availability/files/slots_demo.xlsx" download class="btn btn-link">Download Sample Excel</a></li>
// 													</ul>
// 												`);
//
//     $modalBody.append($formContainer);
// });
//
//     $(document).on("click", "#saveSlotBtn", function () {
//     const dateTime = $("#slotDateTime").val();
//     const duration = parseInt($("#slotDuration").val()) || 1800;
//     const slotDateTime = DateUtil.toGreekISODateTime(dateTime);
//     const payload = {
//     slot: {
//     slotDateTime: slotDateTime,
//     duration: duration,
// },
// };
//     axios
//     .post(`${API_BASE}/slot`, payload)
//     .then((response) => {
//     alert("Availability slot added successfully!");
//     fetchDoctorAvailability(date);
// })
//     .catch((error) => {
//     console.error(error);
// });
// });
//
//     $("#availability-table-body").on("click", "button", async function () {
//     const button = $(this);
//     const slotId = button.data("id");
//
//     if (button.hasClass("delete-slot-btn")) {
//     if (confirm("Are you sure you want to delete this slot?")) {
//     try {
//     await deleteSlot(slotId);
//     alert("Slot deleted successfully.");
//     button.closest("tr").remove();
//     fetchDoctorAvailability($("#datePicker").val());
// } catch (error) {
//     console.error("Error deleting slot:", error);
//     alert("Failed to delete slot. Please try again.");
// }
// }
// } else if (button.hasClass("edit-slot-btn")) {
//     console.log(`Edit slot with ID ${slotId}`);
// }
// });
//
//     $(document).on("click", "#uploadFileBtn", function () {
//     let file = $("#slotFile")[0].files[0];
//
//     if (!file) {
//     $("#fileResponseMessage")
//     .empty()
//     .append(
//     '<span class="text-danger">Please select a file first.</span>'
//     );
//     return;
// }
//
//     const allowedTypes = [
//     "text/csv",
//     "application/vnd.ms-excel",
//     "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
//     ];
//
//     if (!allowedTypes.includes(file.type)) {
//     $("#fileResponseMessage")
//     .empty()
//     .append(
//     '<span class="text-danger">Invalid file type. Please upload a CSV or Excel file.</span>'
//     );
//     return;
// }
//
//     let fileUrl = "";
//     let fileExtension = file.name.split(".").pop().toLowerCase();
//     switch (file.type) {
//     case "text/csv":
//     case "application/vnd.ms-excel":
//     if (fileExtension === "csv") {
//     fileUrl = "/api/v1/doctor-availability/csv/upload";
// } else {
//     fileUrl = "/api/v1/doctor-availability/excel/upload";
// }
//     break;
//     case "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet":
//     fileUrl = "/api/v1/doctor-availability/excel/upload";
//     break;
//     default:
//     $("#fileResponseMessage")
//     .empty()
//     .append(
//     '<span class="text-danger">Invalid file type. Please upload a CSV or Excel file.</span>'
//     );
//     return;
// }
//
//     let formData = new FormData();
//     formData.append("file", file);
//
//     axios
//     .post(fileUrl, formData, {
//     headers: {
//     "Content-Type": "multipart/form-data",
// },
// })
//     .then(function (response) {
//     $("#fileResponseMessage")
//     .empty()
//     .append(`<span class="text-success">${response.data}</span>`);
// })
//     .catch(function (error) {
//     $("#fileResponseMessage")
//     .empty()
//     .append('<span class="text-danger">Error uploading file.</span>');
// });
// });
//
//     $(document).on("click", "#updateSlotBtn", function () {
//     let slotID = $(this).data("id");
//     let slot = slots.find((slot) => slot.id === slotID);
//     console.log("after update clicked " + slotID);
//     updateAvailability(slot);
// });
//
//     function deleteAvailability(slotId) {
//     if (!confirm("Are you sure you want to delete this slot?")) return;
//
//     axios
//     .delete(`${API_BASE}/by-slot-id/${slotId}`)
//     .then((response) => {
//     alert("Slot deleted successfully!");
//     fetchDoctorAvailability($("#datePicker").val());
// })
//     .catch((error) => {
//     console.error(error);
//     alert("Error deleting slot.");
// });
// }
//
//     async function updateAvailability(slot) {
//     const dateTime = $("#slotDateTime").val();
//     const duration = parseInt($("#slotDuration").val()) || 30;
//     slot = new Slot(
//     slot.id,
//     DateUtil.toGreekISODateTime(dateTime),
//     duration
//     );
//     console.log("slot after update " + slot);
//     console.log(dateTime);
//     console.log(duration);
//     console.log(slot);
//
//     try {
//     slots = await updateSlot(slot);
//     populateTable(slots);
// } catch (error) {
//     console.error("Error updating slot:", error);
// }
// }
//
//     function getMinTime() {
//     const now = new Date();
//     return now.toLocaleTimeString("en-GB", {
//     hour: "2-digit",
//     minute: "2-digit",
// });
// }
// </script>
// </body>
// </html>
