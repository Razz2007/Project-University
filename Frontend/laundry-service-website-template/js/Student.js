// URL base de la API
const API_URL = "http://localhost:8085/api/v1/student"; 
const DEGREE_API_URL = "http://localhost:8085/api/v1/degree"; 

let allStudents = [];

document.addEventListener('DOMContentLoaded', function() {
    loadStudents();
    loadDegrees('student-degree');

    const studentForm = document.getElementById("student-form");
    if (studentForm) {
        studentForm.addEventListener('submit', registerStudent);
    }

    const searchInput = document.getElementById('search-student');
    if (searchInput) {
        searchInput.addEventListener('input', filterStudents);
    }
});

// Cargar todos los estudiantes
async function loadStudents() {
    try {
        const response = await fetch(API_URL + "/");
        if (!response.ok) {
            throw new Error(await response.text());
        }
        allStudents = await response.json();
        renderStudents(allStudents);
    } catch (error) {
        console.error(error);
        showModal('Error', "Ocurrió un error al obtener los estudiantes: " + error.message, 'error');
    }
}

// Cargar grados dinámicamente en un select
async function loadDegrees(targetSelectId) {
    try {
        const response = await fetch(DEGREE_API_URL + "/");
        if (!response.ok) {
            throw new Error(await response.text());
        }
        const degrees = await response.json();
        const degreeSelect = document.getElementById(targetSelectId);
        if (!degreeSelect) {
            console.error(`El elemento ${targetSelectId} no se encontró en el DOM`);
            return;
        }

        degreeSelect.innerHTML = '<option value="">Seleccione un grado</option>'; // Opción por defecto

        degrees.forEach(degree => {
            const option = document.createElement("option");
            option.value = degree.id;
            option.textContent = degree.name;
            degreeSelect.appendChild(option);
        });
    } catch (error) {
        console.error(error);
        showModal('Error', "Ocurrió un error al obtener los grados académicos: " + error.message, 'error');
    }
}

// Filtrar estudiantes por nombre o apellido
function filterStudents() {
    const searchValue = document.getElementById('search-student').value.toLowerCase();
    const filteredStudents = allStudents.filter(student => 
        student.firstName.toLowerCase().includes(searchValue) ||
        student.lastName.toLowerCase().includes(searchValue)
    );
    renderStudents(filteredStudents);
}

// Renderizar los estudiantes en la página
function renderStudents(students) {
    const studentContainer = document.getElementById("students-list");
    studentContainer.innerHTML = '';

    students.forEach(student => {
        if (!student || !student.studentId) return;

        const studentCard = document.createElement('div');
        studentCard.className = 'card';
        studentCard.dataset.id = student.studentId;

        studentCard.innerHTML = `
            <div class="card-content">
                <h3 class="card-title">${student.firstName} ${student.lastName}</h3>
                <p class="card-info"><strong>Fecha de Nacimiento:</strong> ${student.birthDate}</p>
                <p class="card-info"><strong>Correo:</strong> ${student.email}</p>
                <p class="card-info"><strong>Teléfono:</strong> ${student.phone}</p>
                <p class="card-info"><strong>Dirección:</strong> ${student.address}</p>
                <p class="card-info"><strong>Grado:</strong> ${student.degreeId?.name || 'Sin asignar'}</p>
                <div class="card-actions">
                    <button class="btn edit-btn" onclick="showEditModal(${student.studentId})">
                        <i class="fas fa-edit"></i> Editar
                    </button>
                    <button class="btn delete-btn" onclick="deleteStudent(${student.studentId})">
                        <i class="fas fa-trash"></i> Eliminar
                    </button>
                </div>
            </div>
        `;
        
        studentContainer.appendChild(studentCard);
    });
}

// Registrar nuevo estudiante
async function registerStudent(event) {
    event.preventDefault();

    const firstName = document.getElementById("student-firstname").value.trim();
    const lastName = document.getElementById("student-lastname").value.trim();
    const birthDate = document.getElementById("student-birthdate").value.trim();
    const email = document.getElementById("student-email").value.trim();
    const phone = document.getElementById("student-phone").value.trim();
    const address = document.getElementById("student-address").value.trim();
    const degreeId = document.getElementById("student-degree").value;

    if (!firstName || !lastName || !birthDate || !email || !phone || !address || !degreeId) {
        showModal('Error', "Por favor, completa todos los campos.", 'error');
        return;
    }

    const studentData = {
        firstName,
        lastName,
        birthDate,
        email,
        phone,
        address,
        degreeId: { id: parseInt(degreeId, 10) }
    };

    try {
        const response = await fetch(API_URL + "/", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(studentData)
        });

        if (response.ok) {
            await loadStudents();
            document.getElementById("student-form").reset();
            showModal('Éxito', 'Estudiante registrado correctamente', 'success');
        } else {
            throw new Error(await response.text());
        }
    } catch (error) {
        console.error(error);
        showModal('Error', "Ocurrió un error al registrar: " + error.message, 'error');
    }
}

// Eliminar un estudiante
async function deleteStudent(id) {
    try {
        const confirmed = confirm("¿Estás seguro de eliminar este estudiante?");
        if (!confirmed) return;

        const response = await fetch(API_URL + "/" + id, {
            method: "DELETE",
            headers: { "Content-Type": "application/json" }
        });

        if (response.ok) {
            allStudents = allStudents.filter(student => student.studentId !== id);
            renderStudents(allStudents);
            showModal('Éxito', 'Estudiante eliminado correctamente', 'success');
        } else {
            throw new Error(await response.text());
        }
    } catch (error) {
        console.error(error);
        showModal('Error', "Ocurrió un error al eliminar: " + error.message, 'error');
    }
}

// Mostrar el modal de edición de un estudiante
async function showEditModal(studentId) {
    try {
        const response = await fetch(`${API_URL}/${studentId}`);
        if (!response.ok) {
            throw new Error(await response.text());
        }

        const student = await response.json();

        // Crear y agregar el modal al DOM
        const modal = document.createElement('div');
        modal.className = 'modal-overlay';
        modal.id = 'edit-modal';

        modal.innerHTML = `
            <div class="modal-content">
                <span class="close-modal" onclick="closeModal('edit-modal')">&times;</span>
                <h2>Editar Estudiante</h2>
                <form id="edit-student-form">
                    <input type="hidden" id="edit-student-id" value="${student.studentId}">
                    <div class="form-group">
                        <label>Nombre:</label>
                        <input type="text" id="edit-student-firstname" value="${student.firstName}" required>
                    </div>
                    <div class="form-group">
                        <label>Apellido:</label>
                        <input type="text" id="edit-student-lastname" value="${student.lastName}" required>
                    </div>
                    <div class="form-group">
                        <label>Fecha de Nacimiento:</label>
                        <input type="date" id="edit-student-birthdate" value="${student.birthDate}" required>
                    </div>
                    <div class="form-group">
                        <label>Correo:</label>
                        <input type="email" id="edit-student-email" value="${student.email}" required>
                    </div>
                    <div class="form-group">
                        <label>Teléfono:</label>
                        <input type="text" id="edit-student-phone" value="${student.phone}" required>
                    </div>
                    <div class="form-group">
                        <label>Dirección:</label>
                        <input type="text" id="edit-student-address" value="${student.address}" required>
                    </div>
                    <div class="form-group">
                        <label>Grado:</label>
                        <select id="editDegreeId"></select>
                    </div>
                    <div class="form-actions">
                        <button type="button" class="btn cancel-btn" onclick="closeModal('edit-modal')">Cancelar</button>
                        <button type="submit" class="btn submit-btn">Actualizar</button>
                    </div>
                </form>
            </div>
        `;

        document.body.appendChild(modal);

        await loadDegrees('editDegreeId');

        document.getElementById('editDegreeId').value = student.degreeId?.id || '';

        document.getElementById('edit-student-form').addEventListener('submit', function(event) {
            event.preventDefault();
            updateStudent();
        });

    } catch (error) {
        console.error(error);
        showModal('Error', "Error al cargar datos del estudiante: " + error.message, 'error');
    }
}

// Actualizar un estudiante
async function updateStudent() {
    const id = document.getElementById("edit-student-id").value;
    const firstName = document.getElementById("edit-student-firstname").value.trim();
    const lastName = document.getElementById("edit-student-lastname").value.trim();
    const birthDate = document.getElementById("edit-student-birthdate").value.trim();
    const email = document.getElementById("edit-student-email").value.trim();
    const phone = document.getElementById("edit-student-phone").value.trim();
    const address = document.getElementById("edit-student-address").value.trim();
    const degreeId = document.getElementById("editDegreeId").value;

    if (!firstName || !lastName || !birthDate || !email || !phone || !address || !degreeId) {
        showModal('Error', "Por favor, completa todos los campos.", 'error');
        return;
    }

    const updatedStudent = {
        studentId: parseInt(id),
        firstName,
        lastName,
        birthDate,
        email,
        phone,
        address,
        degreeId: { id: parseInt(degreeId, 10) }
    };

    try {
        const response = await fetch(`${API_URL}/${id}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(updatedStudent)
        });

        if (response.ok) {
            await loadStudents();
            closeModal('edit-modal');
            showModal('Éxito', 'Estudiante actualizado correctamente', 'success');
        } else {
            throw new Error(await response.text());
        }
    } catch (error) {
        console.error(error);
        showModal('Error', "Ocurrió un error al actualizar: " + error.message, 'error');
    }
}

// Función para cerrar los modales
function closeModal(modalId) {
    const modal = document.getElementById(modalId);
    if (modal) modal.remove();
}

// Mostrar modales personalizados
function showModal(title, message, type) {
    const existingModals = document.querySelectorAll('.modal-overlay');
    existingModals.forEach(m => m.remove());

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
