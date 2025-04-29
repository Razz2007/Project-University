// URL base de la API
const API_URL_ENROLLMENT = "http://localhost:8085/api/v1/enrollment";

// Variable global para almacenar las inscripciones
let allEnrollments = [];

// Cargar todas las inscripciones cuando la página esté lista
document.addEventListener('DOMContentLoaded', function () {
    const isEnrollmentPage = document.getElementById('active-enrollments') ||
        document.getElementById('completed-enrollments') ||
        document.getElementById('canceled-enrollments');

    if (isEnrollmentPage) {
        loadEnrollments();

        const enrollmentForm = document.getElementById('enrollment-form');
        if (enrollmentForm) {
            enrollmentForm.addEventListener('submit', registerEnrollment);
        }

        const searchInput = document.getElementById('search-enrollment');
        if (searchInput) {
            searchInput.addEventListener('input', filterEnrollments);
        }
    }
});

// Cargar todas las inscripciones
async function loadEnrollments() {
    try {
        const response = await fetch(API_URL_ENROLLMENT + "/");
        if (!response.ok) {
            const errorText = await response.text();
            showModal('Error', `Error al cargar las inscripciones: ${errorText}`, 'error');
            return;
        }

        allEnrollments = await response.json();
        renderEnrollments(allEnrollments);
    } catch (error) {
        console.error("Error al cargar inscripciones:", error);
        showModal('Error', "Ocurrió un error al cargar inscripciones: " + error.message, 'error');
    }
}

// Renderizar las inscripciones
function renderEnrollments(enrollments) {
    const activeContainer = document.getElementById('active-enrollments');
    const completedContainer = document.getElementById('completed-enrollments');
    const canceledContainer = document.getElementById('canceled-enrollments');

    if (activeContainer) activeContainer.innerHTML = '';
    if (completedContainer) completedContainer.innerHTML = '';
    if (canceledContainer) canceledContainer.innerHTML = '';

    enrollments.forEach(enrollment => {
        const card = createEnrollmentCard(enrollment);

        if (enrollment.statusEnrollment === 'ACTIVE' || enrollment.statusEnrollment === 'IN_PROGRESS') {
            activeContainer.appendChild(card);
        } else if (enrollment.statusEnrollment === 'COMPLETED') {
            completedContainer.appendChild(card);
        } else if (enrollment.statusEnrollment === 'CANCELED') {
            canceledContainer.appendChild(card);
        }
    });
}

// Crear tarjeta de inscripción
function createEnrollmentCard(enrollment) {
    const card = document.createElement('div');
    card.className = 'card';
    card.dataset.enrollmentId = enrollment.enrollmentId;
    console.log('Enrollment para renderizar:', enrollment);
    card.innerHTML = `
        <div class="card-content">
            <h3 class="card-title">Semestre: ${enrollment.semester}</h3>
            <p class="card-info"><strong>Fecha:</strong> ${enrollment.enrollmentDate}</p>
            <p class="card-info"><strong>Calificación:</strong> ${enrollment.grade ?? 'N/A'}</p>
            <p class="card-info"><strong>Estado:</strong> ${enrollment.statusEnrollment}</p>
            <div class="card-actions">
                <button class="btn edit-btn" onclick="showEditEnrollmentModal(${enrollment.enrollmentId})">
                    <i class="fas fa-edit"></i> Editar
                </button>
                <button class="btn delete-btn" onclick="deleteEnrollment(${enrollment.enrollmentId})">
                    <i class="fas fa-trash"></i> Eliminar
                </button>
            </div>
        </div>
    `;

    return card;
}

// Registrar nueva inscripción
async function registerEnrollment(event) {
    event.preventDefault();

    const semester = document.getElementById('enrollment-semester').value.trim();
    const enrollmentDate = document.getElementById('enrollment-date').value;
    const grade = document.getElementById('enrollment-grade').value.trim();
    const status = document.getElementById('enrollment-status').value;

    if (!semester || !enrollmentDate || !status) {
        showModal('Error', "Por favor, completa todos los campos.", 'error');
        return;
    }

    const enrollmentData = {
        semester,
        enrollmentDate,
        grade: grade ? parseFloat(grade) : null,
        statusEnrollment: status,
        status: true
    };

    try {
        const response = await fetch(API_URL_ENROLLMENT + "/", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(enrollmentData)
        });

        if (response.ok) {
            const data = await response.json();
            enrollmentData.enrollmentId = data.enrollmentId; // ⚡ CORREGIDO
            allEnrollments.push(enrollmentData);
            renderEnrollments(allEnrollments);

            document.getElementById('enrollment-form').reset();
            showModal('Éxito', 'Inscripción registrada correctamente.', 'success');
        } else {
            const errorText = await response.text();
            showModal('Error', `Error al registrar la inscripción: ${errorText}`, 'error');
        }
    } catch (error) {
        console.error("Error al registrar inscripción:", error);
        showModal('Error', "Ocurrió un error durante el registro: " + error.message, 'error');
    }
}

// Buscar inscripciones
function filterEnrollments() {
    const searchValue = document.getElementById('search-enrollment').value.toLowerCase();

    const filtered = allEnrollments.filter(enrollment =>
        enrollment.semester.toLowerCase().includes(searchValue) ||
        enrollment.statusEnrollment.toLowerCase().includes(searchValue)
    );

    renderEnrollments(filtered);
}

// Mostrar modal de edición
async function showEditEnrollmentModal(id) {
    try {
        const response = await fetch(`${API_URL_ENROLLMENT}/${id}`);
        if (response.ok) {
            const enrollment = await response.json();

            const modal = document.createElement('div');
            modal.className = 'modal-overlay';
            modal.id = 'edit-enrollment-modal';

            modal.innerHTML = `
                <div class="modal-content">
                    <span class="close-modal" onclick="closeModal('edit-enrollment-modal')">&times;</span>
                    <h2>Editar Inscripción</h2>
                    <form id="edit-enrollment-form">
                        <input type="hidden" id="edit-enrollment-id" value="${enrollment.enrollmentId}">
                        <div class="form-group">
                            <label for="edit-enrollment-semester">Semestre</label>
                            <input type="text" id="edit-enrollment-semester" value="${enrollment.semester}" required>
                        </div>
                        <div class="form-group">
                            <label for="edit-enrollment-date">Fecha de Inscripción</label>
                            <input type="date" id="edit-enrollment-date" value="${enrollment.enrollmentDate}" required>
                        </div>
                        <div class="form-group">
                            <label for="edit-enrollment-grade">Calificación</label>
                            <input type="number" id="edit-enrollment-grade" value="${enrollment.grade ?? ''}" required step="0.1" min="0" max="5">
                        </div>
                        <div class="form-group">
                            <label for="edit-enrollment-status">Estado</label>
                            <select id="edit-enrollment-status" required>
                                <option value="ACTIVE" ${enrollment.statusEnrollment === 'ACTIVE' ? 'selected' : ''}>Activo</option>
                                <option value="IN_PROGRESS" ${enrollment.statusEnrollment === 'IN_PROGRESS' ? 'selected' : ''}>En progreso</option>
                                <option value="COMPLETED" ${enrollment.statusEnrollment === 'COMPLETED' ? 'selected' : ''}>Completado</option>
                                <option value="CANCELED" ${enrollment.statusEnrollment === 'CANCELED' ? 'selected' : ''}>Cancelado</option>
                            </select>
                        </div>
                        <div class="form-actions">
                            <button type="button" class="btn cancel-btn" onclick="closeModal('edit-enrollment-modal')">Cancelar</button>
                            <button type="submit" class="btn submit-btn">Actualizar</button>
                        </div>
                    </form>
                </div>
            `;

            document.body.appendChild(modal);

            document.getElementById('edit-enrollment-form').addEventListener('submit', function (event) {
                event.preventDefault();
                updateEnrollment();
            });
        } else {
            const errorText = await response.text();
            showModal('Error', `Error al obtener inscripción: ${errorText}`, 'error');
        }
    } catch (error) {
        console.error("Error al cargar inscripción:", error);
        showModal('Error', "Ocurrió un error al cargar inscripción: " + error.message, 'error');
    }
}

// Actualizar inscripción
async function updateEnrollment() {
    const id = document.getElementById('edit-enrollment-id').value;
    const semester = document.getElementById('edit-enrollment-semester').value.trim();
    const enrollmentDate = document.getElementById('edit-enrollment-date').value;
    const grade = parseFloat(document.getElementById('edit-enrollment-grade').value);
    const status = document.getElementById('edit-enrollment-status').value;

    if (!semester || !enrollmentDate || !status || isNaN(grade)) {
        showModal('Error', "Por favor, completa todos los campos.", 'error');
        return;
    }

    const enrollmentData = {
        enrollmentId: parseInt(id),
        semester,
        enrollmentDate,
        grade,
        statusEnrollment: status
    };

    try {
        const response = await fetch(`${API_URL_ENROLLMENT}/${id}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(enrollmentData)
        });

        if (response.ok) {
            allEnrollments = allEnrollments.map(e => e.enrollmentId === parseInt(id) ? enrollmentData : e);
            renderEnrollments(allEnrollments);
            closeModal('edit-enrollment-modal');
            showModal('Éxito', 'Inscripción actualizada correctamente.', 'success');
        } else {
            const errorText = await response.text();
            showModal('Error', `Error al actualizar inscripción: ${errorText}`, 'error');
        }
    } catch (error) {
        console.error("Error al actualizar inscripción:", error);
        showModal('Error', "Ocurrió un error durante la actualización: " + error.message, 'error');
    }
}

// Eliminar inscripción
async function deleteEnrollment(id) {
    try {
        const confirmed = confirm("¿Estás seguro que deseas eliminar esta inscripción?");
        if (!confirmed) return;

        const response = await fetch(`${API_URL_ENROLLMENT}/${id}`, {
            method: "DELETE"
        });

        if (response.ok) {
            allEnrollments = allEnrollments.filter(enrollment => enrollment.enrollmentId !== id);
            renderEnrollments(allEnrollments);
            showModal('Éxito', 'Inscripción eliminada correctamente.', 'success');
        } else {
            const errorText = await response.text();
            showModal('Error', `Error al eliminar inscripción: ${errorText}`, 'error');
        }
    } catch (error) {
        console.error("Error al eliminar inscripción:", error);
        showModal('Error', "Ocurrió un error durante la eliminación: " + error.message, 'error');
    }
}

// Mostrar modal de mensajes
function showModal(title, message, type) {
    const existingModals = document.querySelectorAll('.modal-overlay');
    existingModals.forEach(modal => modal.remove());

    const modal = document.createElement('div');
    modal.className = 'modal-overlay';
    modal.id = 'message-modal';

    modal.innerHTML = `
        <div class="modal-content ${type}">
            <span class="close-modal" onclick="closeModal('message-modal')">&times;</span>
            <h2>${title}</h2>
            <p>${message}</p>
            <div class="form-actions">
                <button class="btn ok-btn" onclick="closeModal('message-modal')">Aceptar</button>
            </div>
        </div>
    `;

    document.body.appendChild(modal);

    if (type === 'success') {
        setTimeout(() => closeModal('message-modal'), 3000);
    }
}

// Cerrar modal
function closeModal(modalId) {
    const modal = document.getElementById(modalId);
    if (modal) modal.remove();
}
