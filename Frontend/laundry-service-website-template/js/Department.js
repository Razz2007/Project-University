// URL base de la API para departamentos
const API_URL = "http://localhost:8085/api/v1/department"; // Ajusta la URL según tu configuración

// Variable global para almacenar todos los departamentos
let allDepartments = [];

// Al cargar el DOM, se inicializan la carga de datos, el formulario y la búsqueda.
document.addEventListener('DOMContentLoaded', function () {
  loadDepartments();

  // Configurar el formulario para agregar departamentos
  const departmentForm = document.getElementById("department-form");
  if (departmentForm) {
    departmentForm.addEventListener('submit', registerDepartment);
  }

  // Configurar el input de búsqueda
  const searchInput = document.getElementById('search-department');
  if (searchInput) {
    searchInput.addEventListener('input', filterDepartments);
  }

  // Configurar el botón de búsqueda (opcional)
  const searchButton = document.getElementById('search-button');
  if (searchButton) {
    searchButton.addEventListener('click', filterDepartments);
  }
});

/**
 * Carga todos los departamentos desde la API y los renderiza en el contenedor.
 */
async function loadDepartments() {
  try {
    const departmentListContainer = document.getElementById('department-list');
    if (!departmentListContainer) {
      console.error("No se encontró el contenedor 'department-list'.");
      return;
    }

    const response = await fetch(API_URL + "/");
    if (!response.ok) {
      const errorText = await response.text();
      showModal('Error', `Error al cargar los departamentos: ${errorText}`, 'error');
      return;
    }

    allDepartments = await response.json();
    renderDepartments(allDepartments);
  } catch (error) {
    console.error("Error al cargar los departamentos:", error);
    showModal('Error', "Ocurrió un error al cargar los departamentos: " + error.message, 'error');
  }
}

/**
 * Renderiza la lista de departamentos en el contenedor.
 * @param {Array} departments - Lista de departamentos a mostrar.
 */
function renderDepartments(departments) {
  const departmentListContainer = document.getElementById('department-list');
  if (departmentListContainer) {
    // Mantener el título de la sección
    const sectionTitle = departmentListContainer.querySelector('.section-title');
    departmentListContainer.innerHTML = '';
    
    if (sectionTitle) {
      departmentListContainer.appendChild(sectionTitle);
    } else {
      const title = document.createElement('h2');
      title.className = 'section-title';
      title.textContent = 'Lista de Departamentos';
      departmentListContainer.appendChild(title);
    }
    
    // Agregar las tarjetas de departamentos
    departments.forEach(department => {
      const departmentCard = createDepartmentCard(department);
      departmentListContainer.appendChild(departmentCard);
    });
  }
}

/**
 * Filtra los departamentos basándose en el término de búsqueda y los renderiza.
 */
function filterDepartments() {
  const searchValue = document.getElementById('search-department').value.toLowerCase();

  const filteredDepartments = allDepartments.filter(department =>
    department.departmentName.toLowerCase().includes(searchValue) ||
    department.locationDepartment.toLowerCase().includes(searchValue) ||
    department.director.toLowerCase().includes(searchValue)
  );

  renderDepartments(filteredDepartments);
}

/**
 * Crea una tarjeta para mostrar un departamento.
 * @param {Object} department - Datos del departamento.
 * @returns {HTMLElement} - Elemento de tarjeta para el departamento.
 */
/**
 * Crea una tarjeta para mostrar un departamento.
 * @param {Object} department - Datos del departamento.
 * @returns {HTMLElement} - Elemento de tarjeta para el departamento.
 */
function createDepartmentCard(department) {
    // Verificar que el objeto department exista
    if (!department) {
      console.error("Objeto de departamento inválido:", department);
      return document.createElement('div'); // Devolver un div vacío
    }
    
    const card = document.createElement('div');
    card.className = 'card';
    card.dataset.departmentId = department.departmentId; 
  
    // Asegurarse de que todos los campos tengan valores por defecto si son undefined
    const name = department.departmentName || 'Sin nombre';
    const location = department.locationDepartment || 'Sin ubicación';
    const director = department.director || 'Sin director';
    
    // Manejar específicamente el presupuesto para evitar error con toLocaleString()
    let budgetDisplay = 'No disponible';
    if (department.budget !== undefined && department.budget !== null) {
      try {
        budgetDisplay = `$${department.budget.toLocaleString()}`;
      } catch (e) {
        console.error("Error al formatear presupuesto:", e);
        budgetDisplay = `$${department.budget}`;
      }
    }
  
    card.innerHTML = `
      <div class="card-content">
        <h3 class="card-title">${name}</h3>
        <p class="card-info"><strong>Ubicación:</strong> ${location}</p>
        <p class="card-info"><strong>Director:</strong> ${director}</p>
        <p class="card-info"><strong>Presupuesto:</strong> ${budgetDisplay}</p>
        <div class="card-actions">
          <button class="btn edit-btn" onclick="showEditModal(${department.departmentId})">
            <i class="fas fa-edit"></i> Editar
          </button>
          <button class="btn delete-btn" onclick="deleteDepartment(${department.departmentId})">
            <i class="fas fa-trash"></i> Eliminar
          </button>
        </div>
      </div>
    `;
    return card;
  }


  async function registerDepartment(event) {
    event.preventDefault();
  
    const name = document.getElementById("dept-name").value.trim();
    const location = document.getElementById("dept-location").value.trim();
    const director = document.getElementById("dept-director").value.trim();
    const budget = document.getElementById("dept-budget").value.trim();
  
    if (!name || !location || !director || !budget) {
      showModal('Error', "Por favor, completa todos los campos.", 'error');
      return;
    }
  
    const departmentDTO = {
      name_department: name,
      location_department: location,
      director: director,
      budget: parseInt(budget, 10)
    };
  
    try {
      const response = await fetch(API_URL + "/", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(departmentDTO)
      });
  
      if (response.ok) {
        // Intentamos obtener la respuesta del servidor
        let newDepartment;
        try {
          const newDepartmentFromServer = await response.json();
          
          // Creamos el objeto con formato correcto usando datos del formulario
          // y solo tomamos el ID del objeto devuelto por el servidor
          newDepartment = {
            departmentId: newDepartmentFromServer.departmentId || newDepartmentFromServer.id,
            departmentName: name, // Usamos los valores del formulario para asegurar consistencia
            locationDepartment: location,
            director: director,
            budget: parseInt(budget, 10),
            status: true // Asumimos que está activo
          };
        } catch (e) {
          console.warn("No se pudo obtener respuesta JSON del servidor, usando datos del formulario");
          // Si no podemos obtener el ID del servidor, generamos uno temporal
          // (esto debería evitarse en producción, pero es útil para pruebas)
          const tempId = Date.now();
          newDepartment = {
            departmentId: tempId,
            departmentName: name,
            locationDepartment: location,
            director: director,
            budget: parseInt(budget, 10),
            status: true
          };
        }
        
        // Añadir el nuevo departamento a la lista
        allDepartments.push(newDepartment);
        
        // Renderizar la lista actualizada
        renderDepartments(allDepartments);
        
        // Resetear el formulario
        document.getElementById("department-form").reset();
        
        // Mostrar mensaje de éxito
        showModal('Éxito', 'Departamento registrado correctamente', 'success');
      } else {
        const errorText = await response.text();
        showModal('Error', `Error al registrar el departamento: ${errorText}`, 'error');
      }
    } catch (error) {
      console.error("Error al registrar el departamento:", error);
      showModal('Error', "Ocurrió un error durante el registro: " + error.message, 'error');
    }
}


async function showEditModal(departmentId) {
  // Verifica que el departmentId no sea undefined
  if (!departmentId) {
    console.error("ID de departamento no válido:", departmentId);
    showModal('Error', "ID de departamento no válido o no especificado", 'error');
    return;
  }

  try {
    const response = await fetch(`${API_URL}/${departmentId}`);
    if (!response.ok) {
      const errorText = await response.text();
      showModal('Error', `Error al obtener el departamento: ${errorText}`, 'error');
      return;
    }

    const department = await response.json();
    
    const modal = document.createElement('div');
    modal.className = 'modal-overlay';
    modal.id = 'edit-modal';
    
    modal.innerHTML = `
        <div class="modal-content">
            <span class="close-modal" onclick="closeModal('edit-modal')">&times;</span>
            <h2>Editar Departamento</h2>
            <form id="edit-department-form">
                <input type="hidden" id="edit-department-id" value="${department.departmentId}">
                <div class="form-group">
                    <label for="edit-dept-name">Nombre:</label>
                    <input type="text" id="edit-dept-name" value="${department.departmentName}" required>
                </div>
                <div class="form-group">
                    <label for="edit-dept-location">Ubicación:</label>
                    <input type="text" id="edit-dept-location" value="${department.locationDepartment}" required>
                </div>
                <div class="form-group">
                    <label for="edit-dept-director">Director:</label>
                    <input type="text" id="edit-dept-director" value="${department.director}" required>
                </div>
                <div class="form-group">
                    <label for="edit-dept-budget">Presupuesto:</label>
                    <input type="number" id="edit-dept-budget" value="${department.budget}" required>
                </div>
                <div class="form-actions">
                    <button type="button" class="btn cancel-btn" onclick="closeModal('edit-modal')">Cancelar</button>
                    <button type="submit" class="btn submit-btn">Actualizar</button>
                </div>
            </form>
        </div>
    `;
    
    document.body.appendChild(modal);

    document.getElementById("edit-department-form").addEventListener('submit', function(event) {
        event.preventDefault();
        updateDepartment();
    });
  } catch (error) {
    console.error("Error al cargar el departamento:", error);
    showModal('Error', "Ocurrió un error al cargar el departamento: " + error.message, 'error');
  }
}

/**
 * Actualiza los datos de un departamento.
 */
/**
 * Actualiza los datos de un departamento.
 */
async function updateDepartment() {
    const id = document.getElementById("edit-department-id").value;
    const name = document.getElementById("edit-dept-name").value.trim();
    const location = document.getElementById("edit-dept-location").value.trim();
    const director = document.getElementById("edit-dept-director").value.trim();
    const budget = document.getElementById("edit-dept-budget").value.trim();
  
    if (!name || !location || !director || !budget) {
        showModal('Error', "Por favor, completa todos los campos.", 'error');
        return;
    }
  
    const departmentDTO = {
      name_department: name,
      location_department: location,
      director: director,
      budget: parseInt(budget, 10)
    };
  
    try {
        const response = await fetch(`${API_URL}/${id}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(departmentDTO)
        });
  
        if (response.ok) {
            // Obtener la respuesta del servidor
            const updatedDepartmentFromServer = await response.json();
            
            // Adaptar el formato al usado en el frontend (similar a lo que hicimos en registerDepartment)
            const updatedDepartment = {
                departmentId: updatedDepartmentFromServer.departmentId || parseInt(id),
                departmentName: updatedDepartmentFromServer.departmentName || updatedDepartmentFromServer.name_department || name,
                locationDepartment: updatedDepartmentFromServer.locationDepartment || updatedDepartmentFromServer.location_department || location,
                director: updatedDepartmentFromServer.director || director,
                budget: updatedDepartmentFromServer.budget !== undefined ? updatedDepartmentFromServer.budget : parseInt(budget, 10),
                status: updatedDepartmentFromServer.status !== undefined ? updatedDepartmentFromServer.status : true
            };
            
            // Actualizar el departamento en la lista local
            const index = allDepartments.findIndex(dept => dept.departmentId === parseInt(id));
            if (index !== -1) {
                allDepartments[index] = updatedDepartment;
            } else {
                // Si por alguna razón no se encuentra en la lista, lo agregamos
                allDepartments.push(updatedDepartment);
            }
            
            renderDepartments(allDepartments);
            closeModal('edit-modal');
            showModal('Éxito', 'Departamento actualizado correctamente', 'success');
        } else {
            const errorText = await response.text();
            showModal('Error', `Error al actualizar el departamento: ${errorText}`, 'error');
        }
    } catch (error) {
        console.error("Error al actualizar el departamento:", error);
        showModal('Error', "Ocurrió un error durante la actualización: " + error.message, 'error');
    }
  }
  
  /**
   * Elimina un departamento.
   * @param {number} departmentId - ID del departamento a eliminar.
   */
  async function deleteDepartment(departmentId) {
      try {
          if (!departmentId) {
              showModal('Error', "ID de departamento no válido o no especificado", 'error');
              return;
          }
          
          const confirmed = confirm("¿Estás seguro que deseas eliminar este departamento?");
          
          if (!confirmed) return;
  
          const response = await fetch(`${API_URL}/${departmentId}`, {
              method: "DELETE"
          });
  
          if (response.ok) {
              // Filtra y actualiza la lista sin recargar la página
              allDepartments = allDepartments.filter(dept => dept.departmentId !== departmentId);
              renderDepartments(allDepartments);
              showModal('Éxito', 'Departamento eliminado correctamente', 'success');
          } else {
              const errorText = await response.text();
              showModal('Error', `Error al eliminar el departamento: ${errorText}`, 'error');
          }
      } catch (error) {
          console.error("Error al eliminar el departamento:", error);
          showModal('Error', "Ocurrió un error durante la eliminación: " + error.message, 'error');
      }
  }
  
  /**
   * Muestra un modal con un mensaje.
   * @param {string} title - Título del modal.
   * @param {string} message - Mensaje a mostrar.
   * @param {string} type - Tipo de modal (success, error, warning).
   */
  function showModal(title, message, type = 'info') {
      const modalId = 'message-modal';
      
      // Remover modal existente si hay uno
      const existingModal = document.getElementById(modalId);
      if (existingModal) {
          document.body.removeChild(existingModal);
      }
      
      const modal = document.createElement('div');
      modal.className = 'modal-overlay';
      modal.id = modalId;
      
      modal.innerHTML = `
          <div class="modal-content modal-${type}">
              <span class="close-modal" onclick="closeModal('${modalId}')">&times;</span>
              <h2>${title}</h2>
              <p>${message}</p>
              <div class="form-actions">
                  <button type="button" class="btn" onclick="closeModal('${modalId}')">Aceptar</button>
              </div>
          </div>
      `;
      
      document.body.appendChild(modal);
      
      // Agregar event listener para cerrar con la tecla Escape
      const handleEscape = (e) => {
          if (e.key === 'Escape') {
              closeModal(modalId);
              document.removeEventListener('keydown', handleEscape);
          }
      };
      document.addEventListener('keydown', handleEscape);
  }
  
  /**
   * Cierra un modal por su ID.
   * @param {string} modalId - ID del modal a cerrar.
   */
  function closeModal(modalId) {
      const modal = document.getElementById(modalId);
      if (modal) {
          document.body.removeChild(modal);
      }
  }