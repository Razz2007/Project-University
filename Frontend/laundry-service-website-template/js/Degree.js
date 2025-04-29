// URL base de la API
const API_URL = "http://localhost:8085/api/v1/degree"; // Cambia esta URL según tu configuración

// Variable global para almacenar los grados
let allDegrees = [];

// Cargar todos los grados académicos y mostrarlos en las secciones correspondientes
document.addEventListener('DOMContentLoaded', function() {
    // Verificar si estamos en la página de grados
    const isGradosPage = document.getElementById('pregrado') || 
                         document.getElementById('posgrado') || 
                         document.getElementById('especialidad');
    
    // Solo cargar los grados si estamos en la página correcta
    if (isGradosPage) {
        loadDegrees();
        
        // Configurar el formulario para agregar grados
        const degreeForm = document.getElementById("degree-form");
        if (degreeForm) {
            degreeForm.addEventListener('submit', registerDegree); 
        }

        // Configurar el input de búsqueda
        const searchInput = document.getElementById('search-degree');
        if (searchInput) {
            searchInput.addEventListener('input', filterDegrees);
        }
    }   
});

async function loadDegrees() {
    try {
        const pregradoContainer = document.getElementById('pregrado');
        const posgradoContainer = document.getElementById('posgrado');
        const especialidadContainer = document.getElementById('especialidad');

        if (!pregradoContainer && !posgradoContainer && !especialidadContainer) {
            console.log("No se encontraron los contenedores de grados académicos.");
            return;
        }

        const response = await fetch(API_URL + "/");
        if (!response.ok) {
            const errorText = await response.text();
            showModal('Error', `Error al cargar los grados académicos: ${errorText}`, 'error');
            return;
        }

        allDegrees = await response.json();
        renderDegrees(allDegrees);

    } catch (error) {
        console.error("Error al cargar los grados académicos:", error);
        showModal('Error', "Ocurrió un error al cargar los grados académicos: " + error.message, 'error');
    }
}

function renderDegrees(degrees) {
    const pregradoContainer = document.getElementById('pregrado');
    const posgradoContainer = document.getElementById('posgrado');
    const especialidadContainer = document.getElementById('especialidad');

    if (pregradoContainer) pregradoContainer.innerHTML = '';
    if (posgradoContainer) posgradoContainer.innerHTML = '';
    if (especialidadContainer) especialidadContainer.innerHTML = '';

    degrees.forEach(degree => {
        const degreeCard = createDegreeCard(degree);

        if (pregradoContainer) pregradoContainer.appendChild(degreeCard);
        // Aquí podrías mejorar la lógica para clasificar en posgrado o especialidad
    });
}

function filterDegrees() {
    const searchValue = document.getElementById('search-degree').value.toLowerCase();

    const filteredDegrees = allDegrees.filter(degree => 
        degree.name.toLowerCase().includes(searchValue) ||
        degree.coordinator.toLowerCase().includes(searchValue) ||
        degree.faculty.toLowerCase().includes(searchValue)
    );

    renderDegrees(filteredDegrees);
}

// Crear una tarjeta para mostrar un grado académico
function createDegreeCard(degree) {
    const card = document.createElement('div');
    card.className = 'card';
    card.dataset.degreeId = degree.id; // Almacenar el ID del grado en el elemento
    
    card.innerHTML = `
    <div class="card-content">
        <h3 class="card-title">${degree.name}</h3>
        <p class="card-info"><strong>Duración:</strong> ${degree.duration_years} semestres</p>
        <p class="card-info"><strong>Coordinador:</strong> ${degree.coordinator}</p>
        <p class="card-info"><strong>Facultad:</strong> ${degree.faculty}</p>
        <div class="card-actions">
            <button class="btn edit-btn" onclick="showEditModal(${degree.id})">
                <i class="fas fa-edit"></i> Editar
            </button>
            <button class="btn delete-btn" onclick="deleteDegree(${degree.id})">
                <i class="fas fa-trash"></i> Eliminar
            </button>
        </div>
    </div>
`;
    
    return card;
}

// Registrar un nuevo grado académico
async function registerDegree(event) {
    event.preventDefault();

    const name = document.getElementById("degree-name").value.trim();
    const durationYears = document.getElementById("degree-duration").value.trim();
    const coordinator = document.getElementById("degree-coordinator").value.trim();
    const faculty = document.getElementById("degree-faculty").value.trim();

    if (!name || !durationYears || !coordinator || !faculty) {
        showModal('Error', "Por favor, completa todos los campos.", 'error');
        return;
    }

    const degreeData = {
        name: name,
        duration_years: parseInt(durationYears, 10),
        coordinator: coordinator,
        faculty: faculty
    };

    try {
        const response = await fetch(API_URL + "/", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(degreeData)
        });

        if (response.ok) {
            const data = await response.json();
            const newDegree = {
                id: data.id,
                name: name,
                duration_years: parseInt(durationYears, 10),
                coordinator: coordinator,
                faculty: faculty
            };
            
            allDegrees.push(newDegree);
            renderDegrees(allDegrees);

            document.getElementById("degree-form").reset();
            showModal('Éxito', 'Grado académico registrado correctamente', 'success');
        } else {
            const errorText = await response.text();
            showModal('Error', `Error al registrar el grado académico: ${errorText}`, 'error');
        }
    } catch (error) {
        console.error("Error al registrar el grado académico:", error);
        showModal('Error', "Ocurrió un error durante el registro: " + error.message, 'error');
    }
}

// Mostrar el modal de edición
async function showEditModal(id) {
    try {
        const response = await fetch(`${API_URL}/${id}`);
        if (response.ok) {
            const degree = await response.json();
            
            const modal = document.createElement('div');
            modal.className = 'modal-overlay';
            modal.id = 'edit-modal';
            
            modal.innerHTML = `
                <div class="modal-content">
                    <span class="close-modal" onclick="closeModal('edit-modal')">&times;</span>
                    <h2>Editar Grado Académico</h2>
                    <form id="edit-degree-form">
                        <input type="hidden" id="edit-degree-id" value="${degree.id}">
                        <div class="form-group">
                            <label for="edit-degree-name">Nombre:</label>
                            <input type="text" id="edit-degree-name" value="${degree.name}" required>
                        </div>
                        <div class="form-group">
                            <label for="edit-degree-duration">Duración (semestres):</label>
                            <input type="number" id="edit-degree-duration" value="${degree.duration_years}" required>
                        </div>
                        <div class="form-group">
                            <label for="edit-degree-coordinator">Coordinador:</label>
                            <input type="text" id="edit-degree-coordinator" value="${degree.coordinator}" required>
                        </div>
                        <div class="form-group">
                            <label for="edit-degree-faculty">Facultad:</label>
                            <input type="text" id="edit-degree-faculty" value="${degree.faculty}" required>
                        </div>
                        <div class="form-actions">
                            <button type="button" class="btn cancel-btn" onclick="closeModal('edit-modal')">Cancelar</button>
                            <button type="submit" class="btn submit-btn">Actualizar</button>
                        </div>
                    </form>
                </div>
            `;
            
            document.body.appendChild(modal);

            document.getElementById('edit-degree-form').addEventListener('submit', function(event) {
                event.preventDefault();
                updateDegree();
            });
        } else {
            const errorText = await response.text();
            showModal('Error', `Error al obtener el grado académico: ${errorText}`, 'error');
        }
    } catch (error) {
        console.error("Error al cargar el grado académico:", error);
        showModal('Error', "Ocurrió un error al cargar el grado académico: " + error.message, 'error');
    }
}

async function deleteDegree(id) {
    try {
        const confirmed = confirm("¿Estás seguro que deseas eliminar este grado académico?");
        
        if (!confirmed) return;
        
        const response = await fetch(API_URL + "/" + id, {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json"
            }
        });
        
        if (response.ok) {
            allDegrees = allDegrees.filter(degree => degree.id !== id);
            renderDegrees(allDegrees);
            alert('Grado académico eliminado correctamente');
        } else {
            const errorText = await response.text();
            alert(`Error al eliminar el grado académico: ${errorText}`);
        }
    } catch (error) {
        console.error("Error al eliminar el grado académico:", error);
        alert("Ocurrió un error durante la eliminación: " + error.message);
    }
}

// Actualizar un grado académico
async function updateDegree() {
    const id = document.getElementById("edit-degree-id").value;
    const name = document.getElementById("edit-degree-name").value.trim();
    const durationYears = document.getElementById("edit-degree-duration").value.trim();
    const coordinator = document.getElementById("edit-degree-coordinator").value.trim();
    const faculty = document.getElementById("edit-degree-faculty").value.trim();

    if (!name || !durationYears || !coordinator || !faculty) {
        showModal('Error', "Por favor, completa todos los campos.", 'error');
        return;
    }

    const degreeData = {
        id: parseInt(id),
        name: name,
        duration_years: parseInt(durationYears, 10),
        coordinator: coordinator,
        faculty: faculty
    };

    try {
        const response = await fetch(`${API_URL}/${id}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(degreeData)
        });

        if (response.ok) {
            allDegrees = allDegrees.map(degree => 
                degree.id === parseInt(id) ? degreeData : degree
            );
            renderDegrees(allDegrees);
            closeModal('edit-modal');
            showModal('Éxito', 'Grado académico actualizado correctamente', 'success');
        } else {
            const errorText = await response.text();
            showModal('Error', `Error al actualizar el grado académico: ${errorText}`, 'error');
        }
    } catch (error) {
        console.error("Error al actualizar el grado académico:", error);
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

