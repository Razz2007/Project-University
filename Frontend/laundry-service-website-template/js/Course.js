// ====== COURSE.JS ======

// URL base de la API
const API_URL = "http://localhost:8085/api/v1/course";

let allCourses = [];

// Función para listar todos los cursos
async function fetchCourses() {
    try {
        const response = await fetch(`${API_URL}/`);
        const courses = await response.json();
        allCourses = courses; 
        displayCourses(courses);
    } catch (error) {
        console.error("Error al obtener los cursos:", error);
    }
}

// Función para mostrar cursos en el HTML
function displayCourses(courses) {
    const coursesList = document.getElementById("courses-list");
    coursesList.innerHTML = ""; // Limpiar contenido anterior

    courses.forEach(course => {
        const courseCard = document.createElement("div");
        courseCard.className = "card";
        courseCard.innerHTML = `
            <div class="card-content">
                <h3>${course.name}</h3>
                <p class="card-info"><strong>Créditos:</strong> ${course.credits}</p>
                <p class="card-info"><strong>Nivel:</strong> ${course.level}</p>
                <p class="card-info"><strong>Prerrequisitos:</strong> ${course.prerequisites || "Ninguno"}</p>
                <p class="card-info"><strong>Estado:</strong> ${course.status ? "Disponible" : "No disponible"}</p>
                <button onclick="showEditModal(${course.courseId})" class="btn edit-btn">Editar</button>
                <button onclick="deleteCourse(${course.courseId})" class="btn delete-btn">Eliminar</button>
            </div>
        `;
        coursesList.appendChild(courseCard);
    });
}

// Función para filtrar cursos en tiempo real
function filterCourses() {
    const searchValue = document.getElementById("search-course").value.toLowerCase();

    const filteredCourses = allCourses.filter(course => 
        course.name.toLowerCase().includes(searchValue) ||
        course.level.toLowerCase().includes(searchValue) ||
        (course.prerequisites && course.prerequisites.toLowerCase().includes(searchValue))
    );

    displayCourses(filteredCourses);
}

// Función para mostrar el modal de edición de un curso
async function showEditModal(courseId) {
    try {
        const response = await fetch(`${API_URL}/${courseId}`);
        if (!response.ok) {
            alert(`Error al obtener el curso: ${await response.text()}`);
            return;
        }
        
        const course = await response.json();
        
        const modal = document.createElement('div');
        modal.className = 'modal-overlay';
        modal.id = 'edit-modal';
        
        modal.innerHTML = `
            <div class="modal-content">
                <span class="close-modal" onclick="closeModal('edit-modal')">&times;</span>
                <h2>Editar Curso</h2>
                <form id="edit-course-form">
                    <input type="hidden" id="edit-course-id" value="${course.courseId}">
                    <div class="form-group">
                        <label for="edit-course-name">Nombre:</label>
                        <input type="text" id="edit-course-name" value="${course.name}" required>
                    </div>
                    <div class="form-group">
                        <label for="edit-course-credits">Créditos:</label>
                        <input type="number" id="edit-course-credits" value="${course.credits}" required>
                    </div>
                    <div class="form-group">
                        <label for="edit-course-level">Nivel:</label>
                        <input type="text" id="edit-course-level" value="${course.level}" required>
                    </div>
                    <div class="form-group">
                        <label for="edit-course-prerequisites">Prerrequisitos:</label>
                        <input type="text" id="edit-course-prerequisites" value="${course.prerequisites || ''}">
                    </div>
                    <div class="form-group">
                        <label for="edit-course-status">Estado:</label>
                        <select id="edit-course-status">
                            <option value="true" ${course.status ? 'selected' : ''}>Disponible</option>
                            <option value="false" ${!course.status ? 'selected' : ''}>No disponible</option>
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

        document.getElementById('edit-course-form').addEventListener('submit', function(event) {
            event.preventDefault();
            updateCourse();
        });

    } catch (error) {
        console.error("Error al cargar el curso:", error);
        alert("Ocurrió un error al cargar el curso: " + error.message);
    }
}

// Función para actualizar un curso
async function updateCourse() {
    const courseId = document.getElementById("edit-course-id").value;
    const name = document.getElementById("edit-course-name").value;
    const credits = parseInt(document.getElementById("edit-course-credits").value);
    const level = document.getElementById("edit-course-level").value;
    const prerequisites = document.getElementById("edit-course-prerequisites").value;
    const status = document.getElementById("edit-course-status").value === "true";

    const updatedCourse = { courseId, name, credits, level, prerequisites, status };

    try {
        const response = await fetch(`${API_URL}/${courseId}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(updatedCourse)
        });

        if (response.ok) {
            alert("Curso actualizado exitosamente.");
            closeModal('edit-modal');
            fetchCourses();
        } else {
            alert("Error al actualizar el curso.");
        }
    } catch (error) {
        console.error("Error al actualizar el curso:", error);
        alert("Ocurrió un error al actualizar el curso: " + error.message);
    }
}

// Función para cerrar cualquier modal
function closeModal(modalId) {
    const modal = document.getElementById(modalId);
    if (modal) {
        modal.remove();
    }
}

// Función para agregar un nuevo curso
async function addCourse(event) {
    event.preventDefault();

    const name = document.getElementById("course-name").value;
    const credits = parseInt(document.getElementById("course-credits").value);
    const level = document.getElementById("course-level").value;
    const prerequisites = document.getElementById("course-prerequisites").value;
    const status = document.getElementById("course-status").value === "true";

    const course = { courseId: 0, name, credits, level, prerequisites, status };

    try {
        const response = await fetch(`${API_URL}/`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(course)
        });

        if (response.ok) {
            alert("Curso agregado exitosamente.");
            document.getElementById("course-form").reset();
            fetchCourses();
        } else {
            alert("Error al agregar el curso.");
        }
    } catch (error) {
        console.error("Error al agregar el curso:", error);
    }
}

// Función para eliminar un curso
async function deleteCourse(courseId) {
    if (!confirm("¿Estás seguro de eliminar este curso?")) return;

    try {
        const response = await fetch(`${API_URL}/${courseId}`, {
            method: "DELETE"
        });

        if (response.ok) {
            alert("Curso eliminado exitosamente.");
            fetchCourses();
        } else {
            alert("Error al eliminar el curso.");
        }
    } catch (error) {
        console.error("Error al eliminar el curso:", error);
    }
}

// Función para buscar cursos por nombre o nivel en el backend
async function searchCourses() {
    const filter = document.getElementById("search-course").value.trim();

    if (!filter) {
        fetchCourses(); // Si no hay filtro, mostrar todos
        return;
    }

    try {
        const response = await fetch(`${API_URL}/filter/${encodeURIComponent(filter)}`);
        const courses = await response.json();
        displayCourses(courses);
    } catch (error) {
        console.error("Error al buscar cursos:", error);
    }
}

// Eventos
document.getElementById("course-form").addEventListener("submit", addCourse);
document.getElementById("search-button").addEventListener("click", searchCourses);
document.getElementById("search-course").addEventListener("input", filterCourses);

// Cargar cursos al iniciar la página
document.addEventListener("DOMContentLoaded", fetchCourses);
