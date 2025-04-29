// URL base de la API
const API_URL = "http://localhost:8085/api/v1/professor"; 
const DEPARTMENT_API_URL = "http://localhost:8085/api/v1/department"; 

let allProfessors = [];

document.addEventListener('DOMContentLoaded', function() {
    loadProfessors();
    
    const professorForm = document.getElementById("professor-form");
    if (professorForm) {
        professorForm.addEventListener('submit', registerProfessor);
    }

    const searchInput = document.getElementById('search-professor');
    if (searchInput) {
        searchInput.addEventListener('input', filterProfessors);
    }
});

// Cargar todos los profesores
async function loadProfessors() {
    try {
        const response = await fetch(API_URL + "/");
        if (!response.ok) {
            throw new Error(await response.text());
        }
        allProfessors = await response.json();
        console.log("Profesores cargados:", allProfessors); // Ver qué IDs tienen los profesores
        renderProfessors(allProfessors);
    } catch (error) {
        console.error(error);
        showModal('Error', "Ocurrió un error al obtener los profesores: " + error.message, 'error');
    }
}

// Cargar departamentos para el formulario de registro
async function loadDepartments() {
    try {
        const response = await fetch(DEPARTMENT_API_URL + "/");
        if (!response.ok) {
            throw new Error(await response.text());
        }
        const departments = await response.json();
        const departmentSelect = document.getElementById("professor-department");
        
        if (departmentSelect) {
            departmentSelect.innerHTML = ''; // Limpiar opciones existentes
            departments.forEach(department => {
                const option = document.createElement("option");
                option.value = department.departmentId; // Corregido: usar departmentId
                option.textContent = department.departmentName; // Corregido: usar departmentName
                departmentSelect.appendChild(option);
            });
        }
    } catch (error) {
        console.error(error);
        showModal('Error', "Ocurrió un error al obtener los departamentos: " + error.message, 'error');
    }
}
// Cargar departamentos para el modal de edición
async function loadDepartmentsForEdit(selectedDepartmentId) {
    try {
        const response = await fetch(DEPARTMENT_API_URL + "/");
        if (!response.ok) {
            throw new Error(await response.text());
        }
        
        const departments = await response.json();
        const editDepartmentSelect = document.getElementById("editDepartmentId");
        
        if (editDepartmentSelect) {
            // Limpiar las opciones existentes
            editDepartmentSelect.innerHTML = '';
            
            departments.forEach(department => {
                const option = document.createElement("option");
                option.value = department.departmentId;
                option.textContent = department.departmentName; // Corregido: usar departmentName
                
                // Verificar si este es el departamento seleccionado
                if (department.departmentId === selectedDepartmentId) {
                    option.selected = true;
                }
                editDepartmentSelect.appendChild(option);
            });
        } else {
            console.error("El elemento editDepartmentId no se encontró en el DOM");
        }
    } catch (error) {
        console.error(error);
        showModal('Error', "Ocurrió un error al obtener los departamentos: " + error.message, 'error');
    }
}

// Filtrar profesores por nombre o apellido
function filterProfessors() {
    const searchValue = document.getElementById('search-professor').value.toLowerCase();
    const filteredProfessors = allProfessors.filter(professor => 
        professor.firstName.toLowerCase().includes(searchValue) ||
        professor.lastName.toLowerCase().includes(searchValue)
    );
    renderProfessors(filteredProfessors);
}

// Renderizar los profesores en la página
function renderProfessors(professors) {
    const professorContainer = document.getElementById("professors-list");
    if (!professorContainer) {
        console.error("El elemento professors-list no se encontró en el DOM");
        return;
    }
    
    professorContainer.innerHTML = '';

    professors.forEach(professor => {
        if (!professor || !professor.professorId) return;

        // Obtener el nombre del departamento correctamente
        const departmentName = professor.departmentId ? professor.departmentId.departmentName : 'Sin asignar';

        const professorCard = document.createElement('div');
        professorCard.className = 'card';
        professorCard.dataset.id = professor.professorId;

        professorCard.innerHTML = `
            <div class="card-content">
                <h3 class="card-title">${professor.firstName} ${professor.lastName}</h3>
                <p class="card-info"><strong>Especialidad:</strong> ${professor.specialty}</p>
                <p class="card-info"><strong>Fecha de Contratación:</strong> ${professor.hireDate}</p>
                <p class="card-info"><strong>Correo:</strong> ${professor.email}</p>
                <p class="card-info"><strong>Teléfono:</strong> ${professor.phone}</p>
                <p class="card-info"><strong>Salario:</strong> ${professor.salary}</p>
                <p class="card-info"><strong>Departamento:</strong> ${departmentName}</p>
                <div class="card-actions">
                    <button class="btn edit-btn" onclick="showEditModal(${professor.professorId})">
                        <i class="fas fa-edit"></i> Editar
                    </button>
                    <button class="btn delete-btn" onclick="deleteProfessor(${professor.professorId})">
                        <i class="fas fa-trash"></i> Eliminar
                    </button>
                </div>
            </div>
        `;
        
        professorContainer.appendChild(professorCard);
    });
}

// Registrar nuevo profesor
async function registerProfessor(event) {
    event.preventDefault();

    const firstName = document.getElementById("professor-firstname").value.trim();
    const lastName = document.getElementById("professor-lastname").value.trim();
    const specialty = document.getElementById("professor-specialty").value.trim();
    const hireDate = document.getElementById("professor-hiredate").value.trim();
    const email = document.getElementById("professor-email").value.trim();
    const phone = document.getElementById("professor-phone").value.trim();
    const salary = document.getElementById("professor-salary").value.trim();
    const departmentId = document.getElementById("professor-department").value;

    if (!firstName || !lastName || !specialty || !hireDate || !email || !phone || !salary) {
        showModal('Error', "Por favor, completa todos los campos.", 'error');
        return;
    }

    const professorData = {
        firstName,
        lastName,
        specialty,
        hireDate,
        email,
        phone,
        salary: parseFloat(salary),
        departmentId: departmentId ? { departmentId: parseInt(departmentId, 10) } : null  // Enviar objeto completo
    };
    
    try {
        const response = await fetch(API_URL + "/", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(professorData)
        });

        if (response.ok) {
            await loadProfessors();
            document.getElementById("professor-form").reset();
            showModal('Éxito', 'Profesor registrado correctamente', 'success');
        } else {
            throw new Error(await response.text());
        }
    } catch (error) {
        console.error(error);
        showModal('Error', "Ocurrió un error al registrar: " + error.message, 'error');
    }
}

// Eliminar un profesor
async function deleteProfessor(id) {
    try {
        const confirmed = confirm("¿Estás seguro de eliminar este profesor?");
        if (!confirmed) return;

        const response = await fetch(API_URL + "/" + id, {
            method: "DELETE",
            headers: { "Content-Type": "application/json" }
        });

        if (response.ok) {
            allProfessors = allProfessors.filter(professor => professor.professorId !== id);
            renderProfessors(allProfessors);
            showModal('Éxito', 'Profesor eliminado correctamente', 'success');
        } else {
            throw new Error(await response.text());
        }
    } catch (error) {
        console.error(error);
        showModal('Error', "Ocurrió un error al eliminar: " + error.message, 'error');
    }
}

// Mostrar el modal de edición de un profesor
async function showEditModal(professorId) {
    try {
        const response = await fetch(`${API_URL}/${professorId}`);
        if (!response.ok) {
            throw new Error(await response.text());
        }

        const professor = await response.json();
        const selectedDepartmentId = professor.departmentId ? professor.departmentId.departmentId : null;

        // Eliminar cualquier modal existente
        const existingModal = document.getElementById('edit-modal');
        if (existingModal) {
            existingModal.remove();
        }

        // Crear y agregar el modal al DOM
        const modal = document.createElement('div');
        modal.className = 'modal-overlay';
        modal.id = 'edit-modal';

        modal.innerHTML = `
            <div class="modal-content">
                <span class="close-modal" onclick="closeModal('edit-modal')">&times;</span>
                <h2>Editar Profesor</h2>
                <form id="edit-professor-form">
                    <input type="hidden" id="edit-professor-id" value="${professor.professorId}">
                    <div class="form-group">
                        <label>Nombre:</label>
                        <input type="text" id="edit-professor-firstname" value="${professor.firstName}" required>
                    </div>
                    <div class="form-group">
                        <label>Apellido:</label>
                        <input type="text" id="edit-professor-lastname" value="${professor.lastName}" required>
                    </div>
                    <div class="form-group">
                        <label>Especialidad:</label>
                        <input type="text" id="edit-professor-specialty" value="${professor.specialty}" required>
                    </div>
                    <div class="form-group">
                        <label>Fecha de Contratación:</label>
                        <input type="date" id="edit-professor-hiredate" value="${professor.hireDate}" required>
                    </div>
                    <div class="form-group">
                        <label>Correo:</label>
                        <input type="email" id="edit-professor-email" value="${professor.email}" required>
                    </div>
                    <div class="form-group">
                        <label>Teléfono:</label>
                        <input type="text" id="edit-professor-phone" value="${professor.phone}" required>
                    </div>
                    <div class="form-group">
                        <label>Salario:</label>
                        <input type="number" step="0.01" id="edit-professor-salary" value="${professor.salary}" required>
                    </div>
                    <div class="form-group">
                        <label>Departamento:</label>
                        <select id="editDepartmentId"></select>
                    </div>
                    <div class="form-actions">
                        <button type="button" class="btn cancel-btn" onclick="closeModal('edit-modal')">Cancelar</button>
                        <button type="submit" class="btn submit-btn">Actualizar</button>
                    </div>
                </form>
            </div>
        `;

        document.body.appendChild(modal);

        // Una vez que el modal está en el DOM, cargamos los departamentos
        await loadDepartmentsForEdit(selectedDepartmentId);

        // Agregar el event listener al formulario
        document.getElementById('edit-professor-form').addEventListener('submit', function(event) {
            event.preventDefault();
            updateProfessor();
        });

    } catch (error) {
        console.error(error);
        showModal('Error', `Error al cargar datos del profesor: ${error.message}`, 'error');
    }
}

// Actualizar un profesor
async function updateProfessor() {
    const id = document.getElementById("edit-professor-id").value;
    const firstName = document.getElementById("edit-professor-firstname").value.trim();
    const lastName = document.getElementById("edit-professor-lastname").value.trim();
    const specialty = document.getElementById("edit-professor-specialty").value.trim();
    const hireDate = document.getElementById("edit-professor-hiredate").value.trim();
    const email = document.getElementById("edit-professor-email").value.trim();
    const phone = document.getElementById("edit-professor-phone").value.trim();
    const salary = document.getElementById("edit-professor-salary").value.trim();
    const departmentId = document.getElementById("editDepartmentId").value;

    if (!firstName || !lastName || !specialty || !hireDate || !email || !phone || !salary) {
        showModal('Error', "Por favor, completa todos los campos.", 'error');
        return;
    }

    const updatedProfessor = {
        professorId: parseInt(id),
        firstName,
        lastName,
        specialty,
        hireDate,
        email,
        phone,
        salary: parseFloat(salary),
        departmentId: departmentId ? { departmentId: parseInt(departmentId, 10) } : null  // Enviar objeto completo
    };
    
    try {
        const response = await fetch(`${API_URL}/${id}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(updatedProfessor)
        });

        if (response.ok) {
            await loadProfessors();
            closeModal('edit-modal');
            showModal('Éxito', 'Profesor actualizado correctamente', 'success');
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

// Cargar departamentos al inicio si existe el formulario principal
document.addEventListener('DOMContentLoaded', function() {
    const professorForm = document.getElementById("professor-form");
    if (professorForm) {
        loadDepartments();
    }
});