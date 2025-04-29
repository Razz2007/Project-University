// URL base de la API
const API_URL = "http://localhost:8085/classroom"; 

// Variable global para almacenar las aulas
let allClassrooms = [];

// Cargar todas las aulas académicas cuando el DOM esté listo
document.addEventListener('DOMContentLoaded', function() {
    const isClassroomPage = document.getElementById('classroom-list');

    if (isClassroomPage) {
        loadClassrooms();

        const classroomForm = document.getElementById("classroom-form");
        if (classroomForm) {
            classroomForm.addEventListener('submit', registerClassroom);
        }

        const searchInput = document.getElementById('search-classroom');
        if (searchInput) {
            searchInput.addEventListener('input', filterClassrooms);
        }
    }   
});

// Cargar todas las aulas
async function loadClassrooms() {
    try {
        const classroomContainer = document.getElementById('classroom-list');
        if (!classroomContainer) {
            console.log("No se encontró el contenedor de aulas.");
            return;
        }

        const response = await fetch(API_URL + "/");
        if (!response.ok) {
            const errorText = await response.text();
            showModal('Error', `Error al cargar las aulas: ${errorText}`, 'error');
            return;
        }

        allClassrooms = await response.json();
        renderClassrooms(allClassrooms);

    } catch (error) {
        console.error("Error al cargar las aulas:", error);
        showModal('Error', "Ocurrió un error al cargar las aulas: " + error.message, 'error');
    }
}

// Filtrar aulas por nombre
function filterClassrooms() {
    const searchValue = document.getElementById('search-classroom').value.toLowerCase();

    const filteredClassrooms = allClassrooms.filter(classroom => 
        classroom.name_classroom.toLowerCase().includes(searchValue)
    );

    renderClassrooms(filteredClassrooms);
}

// Renderizar las aulas en la página
function renderClassrooms(classrooms) {
    const classroomContainer = document.getElementById('classroom-list');
    classroomContainer.innerHTML = '';

    classrooms.forEach(classroom => {
        const classroomCard = createClassroomCard(classroom);
        classroomContainer.appendChild(classroomCard);
    });
}

// Crear una tarjeta para mostrar un aula académica
function createClassroomCard(classroom) {
    const card = document.createElement('div');
    card.className = 'card';
    card.dataset.classroomId = classroom.classroom_id; // Usar classroom_id

    card.innerHTML = `
    <div class="card-content">
        <h3 class="card-title">${classroom.name_classroom}</h3>
        <p class="card-info"><strong>Edificio:</strong> ${classroom.building}</p>
        <p class="card-info"><strong>Capacidad:</strong> ${classroom.capacity} estudiantes</p>
        <p class="card-info"><strong>Tipo:</strong> ${classroom.type}</p>
        <p class="card-info"><strong>Proyector:</strong> ${classroom.has_projector ? "Sí" : "No"}</p>
        <div class="card-actions">
            <button class="btn edit-btn" onclick="showEditModal(${classroom.classroom_id})"> <!-- Cambiado a classroom_id -->
                <i class="fas fa-edit"></i> Editar
            </button>
            <button class="btn delete-btn" onclick="deleteClassroom(${classroom.classroom_id})"> <!-- Cambiado a classroom_id -->
                <i class="fas fa-trash"></i> Eliminar
            </button>
        </div>
    </div>
    `;

    return card;
}

// Registrar nueva aula académica
async function registerClassroom(event) {
    event.preventDefault();

    const name_classroom = document.getElementById("classroom-name").value.trim();
    const building = document.getElementById("classroom-building").value.trim();
    const capacity = document.getElementById("classroom-capacity").value.trim();
    const type = document.getElementById("classroom-type").value.trim();
    const has_projector = document.getElementById("classroom-projector").value;

    if (!name_classroom || !building || !capacity || !type) {
        showModal('Error', "Por favor, completa todos los campos.", 'error');
        return;
    }

    const classroomData = {
        name_classroom,
        building: parseInt(building, 10),
        capacity: parseInt(capacity, 10),
        type,
        has_projector: parseInt(has_projector, 10)
    };

    try {
        const response = await fetch(API_URL + "/", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(classroomData)
        });

        if (response.ok) {
            await loadClassrooms();
            document.getElementById("classroom-form").reset();
            showModal('Éxito', 'Aula registrada correctamente', 'success');
        } else {
            const errorText = await response.text();
            showModal('Error', `Error al registrar el aula: ${errorText}`, 'error');
        }
    } catch (error) {
        console.error(error);
        showModal('Error', "Ocurrió un error al registrar el aula: " + error.message, 'error');
    }
}

// Mostrar el modal de edición
async function showEditModal(classroomId) { // Cambiado el parámetro a classroomId
    try {
        const response = await fetch(`${API_URL}/${classroomId}`); // Cambiado id a classroomId
        if (response.ok) {
            const classroom = await response.json();
            
            const modal = document.createElement('div');
            modal.className = 'modal-overlay';
            modal.id = 'edit-modal';
            
            modal.innerHTML = `
                <div class="modal-content">
                    <span class="close-modal" onclick="closeModal('edit-modal')">&times;</span>
                    <h2>Editar Aula Académica</h2>
                    <form id="edit-classroom-form">
                        <input type="hidden" id="edit-classroom-id" value="${classroom.classroom_id}"> <!-- Cambiado a classroom_id -->
                        <div class="form-group">
                            <label for="edit-classroom-name">Nombre:</label>
                            <input type="text" id="edit-classroom-name" value="${classroom.name_classroom}" required>
                        </div>
                        <div class="form-group">
                            <label for="edit-classroom-building">Edificio:</label>
                            <input type="number" id="edit-classroom-building" value="${classroom.building}" required>
                        </div>
                        <div class="form-group">
                            <label for="edit-classroom-capacity">Capacidad:</label>
                            <input type="number" id="edit-classroom-capacity" value="${classroom.capacity}" required>
                        </div>
                        <div class="form-group">
                            <label for="edit-classroom-type">Tipo:</label>
                            <input type="text" id="edit-classroom-type" value="${classroom.type}" required>
                        </div>
                        <div class="form-group">
                            <label for="edit-classroom-projector">Proyector:</label>
                            <select id="edit-classroom-projector">
                                <option value="1" ${classroom.has_projector === 1 ? 'selected' : ''}>Sí</option>
                                <option value="0" ${classroom.has_projector === 0 ? 'selected' : ''}>No</option>
                            </select>
                        </div>
                        <div class="form-actions">
                            <button type="button" class="btn cancel-btn" onclick="closeModal('edit-modal')">Cancelar</button>
                            <button type="submit" class="btn submit-btn">Actualizar</button>
                        </div>
                    </form>
                </div>
            `;
            
            document.body.appendChild(modal);

            document.getElementById('edit-classroom-form').addEventListener('submit', function(event) {
                event.preventDefault();
                updateClassroom(classroomId); // Asegúrate de llamar a la función con el classroomId
            });
        } else {
            showModal('Error', `Error al obtener el aula académica`, 'error');
        }
    } catch (error) {
        console.error(error);
        showModal('Error', "Error al cargar el aula: " + error.message, 'error');
    }
}

// Actualizar un aula académica
async function updateClassroom(classroomId) {
    const name_classroom = document.getElementById("edit-classroom-name").value.trim();
    const building = document.getElementById("edit-classroom-building").value.trim();
    const capacity = document.getElementById("edit-classroom-capacity").value.trim();
    const type = document.getElementById("edit-classroom-type").value.trim();
    const has_projector = document.getElementById("edit-classroom-projector").value;

    if (!name_classroom || !building || !capacity || !type) {
        showModal('Error', "Por favor, completa todos los campos.", 'error');
        return;
    }

    const classroomData = {
        classroom_id: parseInt(classroomId),
        name_classroom: name_classroom,
        building: parseInt(building, 10),
        capacity: parseInt(capacity, 10),
        type: type,
        has_projector: parseInt(has_projector, 10)
    };

    try {
        const response = await fetch(`${API_URL}/${classroomId}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(classroomData)
        });

        if (response.ok) {
            allClassrooms = allClassrooms.map(classroom => 
                classroom.classroom_id === parseInt(classroomId) ? classroomData : classroom
            );
            renderClassrooms(allClassrooms);
            closeModal('edit-modal');
            showModal('Éxito', 'Aula académica actualizada correctamente', 'success');
        } else {
            const errorText = await response.text();
            showModal('Error', `Error al actualizar el aula académica: ${errorText}`, 'error');
        }
    } catch (error) {
        console.error("Error al actualizar el aula académica:", error);
        showModal('Error', "Ocurrió un error durante la actualización: " + error.message, 'error');
    }
}

// Crear y mostrar un modal
function showModal(title, message, type) {
    if (!document.body) return;

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

// Cerrar modal por ID
function closeModal(modalId) {
    const modal = document.getElementById(modalId);
    if (modal) modal.remove();
}

// Eliminar aula
async function deleteClassroom(classroomId) { // Cambiado el parámetro a classroomId
    try {
        const confirmed = confirm("¿Estás seguro que deseas eliminar esta aula?");
        
        if (!confirmed) return;
        
        const response = await fetch(`${API_URL}/${classroomId}`, { method: "DELETE" });
        
        if (response.ok) {
            allClassrooms = allClassrooms.filter(classroom => classroom.classroom_id !== classroomId);
            renderClassrooms(allClassrooms);
            showModal("Éxito", "Aula eliminada correctamente", "success");
        } else {
            const errorText = await response.text();
            showModal("Error", `Error al eliminar el aula: ${errorText}`, "error");
        }
    } catch (error) {
        console.error("Error al eliminar el aula:", error);
        showModal("Error", "Ocurrió un error durante la eliminación: " + error.message, "error");
    }
}