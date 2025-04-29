// URL base de la API para bibliotecas
const API_URL = "http://localhost:8085/api/v1/library"; // Ajusta la URL según tu configuración

// Variable global para almacenar todas las bibliotecas
let allLibraries = [];

// Al cargar el DOM, se inicializan la carga de datos, el formulario y la búsqueda.
document.addEventListener('DOMContentLoaded', function () {
  loadLibraries();

  // Configurar el formulario para agregar bibliotecas
  const libraryForm = document.getElementById("library-form");
  if (libraryForm) {
    libraryForm.addEventListener('submit', registerLibrary);
  }

  // Configurar el input de búsqueda
  const searchInput = document.getElementById('search-library');
  if (searchInput) {
    searchInput.addEventListener('input', filterLibraries);
  }
});

/**
 * Carga todas las bibliotecas desde la API y las renderiza en el contenedor "universitarias".
 */
async function loadLibraries() {
  try {
    const universitariasContainer = document.getElementById('universitarias');
    if (!universitariasContainer) {
      console.error("No se encontró el contenedor 'universitarias'.");
      return;
    }

    const response = await fetch(API_URL + "/");
    if (!response.ok) {
      const errorText = await response.text();
      showModal('Error', `Error al cargar las bibliotecas: ${errorText}`, 'error');
      return;
    }

    allLibraries = await response.json();
    renderLibraries(allLibraries);
  } catch (error) {
    console.error("Error al cargar las bibliotecas:", error);
    showModal('Error', "Ocurrió un error al cargar las bibliotecas: " + error.message, 'error');
  }
}


function renderLibraries(libraries) {
  const universitariasContainer = document.getElementById('universitarias');
  if (universitariasContainer) {
    universitariasContainer.innerHTML = ''; // Limpiar contenedor
    libraries.forEach(library => {
      const libraryCard = createLibraryCard(library);
      universitariasContainer.appendChild(libraryCard);
    });
  }
}

/**
 * Filtra las bibliotecas basándose en el término de búsqueda y las renderiza.
 */
function filterLibraries() {
  const searchValue = document.getElementById('search-library').value.toLowerCase();

  const filteredLibraries = allLibraries.filter(library =>
    library.nameLibrary.toLowerCase().includes(searchValue) ||
    library.location.toLowerCase().includes(searchValue)
  );

  renderLibraries(filteredLibraries);
}


function createLibraryCard(library) {
    const card = document.createElement('div');
    card.className = 'card';
    card.dataset.libraryId = library.libraryId;
  
    card.innerHTML = `
      <div class="card-content">
        <h3 class="card-title">${library.nameLibrary}</h3>
        <p class="card-info"><strong>Ubicación:</strong> ${library.location}</p>
        <p class="card-info"><strong>Hora de Apertura:</strong> ${library.openingTime}</p>
        <p class="card-info"><strong>Hora de Cierre:</strong> ${library.closingTime}</p>
        <p class="card-info"><strong>Capacidad:</strong> ${library.capacity}</p>
        <div class="card-actions">
        <button class="btn edit-btn" onclick="showEditModal(${library.libraryId})">
                  <i class="fas fa-edit"></i> Editar
              </button>
          <button class="btn delete-btn" onclick="deleteLibrary(${library.libraryId})">
            <i class="fas fa-trash"></i> Eliminar
          </button>
        </div>
      </div>
    `;
    return card;
    }	

/**
 * Registra una nueva biblioteca mediante el formulario.
 * @param {Event} event - Evento submit del formulario.
 */
async function registerLibrary(event) {
  event.preventDefault();

  const name = document.getElementById("library-name").value.trim();
  const location = document.getElementById("library-location").value.trim();
  const openingTime = document.getElementById("opening-time").value;
  const closingTime = document.getElementById("closing-time").value;
  const capacity = document.getElementById("library-capacity").value.trim();

  if (!name || !location || !openingTime || !closingTime || !capacity) {
    showModal('Error', "Por favor, completa todos los campos.", 'error');
    return;
  }

  const libraryDTO = {
    nameLibrary: name,
    location: location,
    openingTime: openingTime,
    closingTime: closingTime,
    capacity: parseInt(capacity, 10)
  };

  try {
    const response = await fetch(API_URL + "/", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(libraryDTO)
    });

    if (response.ok) {
      const data = await response.json();
      // Se asume que la API devuelve el objeto con el identificador asignado (libraryId o id)
      const newLibrary = {
        libraryId: data.libraryId || data.id,
        nameLibrary: name,
        location: location,
        openingTime: openingTime,
        closingTime: closingTime,
        capacity: parseInt(capacity, 10)
      };

      allLibraries.push(newLibrary);
      renderLibraries(allLibraries);
      document.getElementById("library-form").reset();
      showModal('Éxito', 'Biblioteca registrada correctamente', 'success');
    } else {
      const errorText = await response.text();
      showModal('Error', `Error al registrar la biblioteca: ${errorText}`, 'error');
    }
  } catch (error) {
    console.error("Error al registrar la biblioteca:", error);
    showModal('Error', "Ocurrió un error durante el registro: " + error.message, 'error');
  }
}

// Mostrar el modal de edición
async function showEditModal(libraryId) {
    // Verifica que el libraryId no sea undefined
    if (!libraryId) {
      console.error("ID de biblioteca no válido:", libraryId);
      showModal('Error', "ID de biblioteca no válido o no especificado", 'error');
      return;
    }
  
    try {
      const response = await fetch(`${API_URL}/${libraryId}`);
      if (!response.ok) {
        const errorText = await response.text();
        showModal('Error', `Error al obtener la biblioteca: ${errorText}`, 'error');
        return;
      }
  
      const library = await response.json();
      
      const modal = document.createElement('div');
      modal.className = 'modal-overlay';
      modal.id = 'edit-modal';
      
      modal.innerHTML = `
          <div class="modal-content">
              <span class="close-modal" onclick="closeModal('edit-modal')">&times;</span>
              <h2>Editar Biblioteca</h2>
              <form id="edit-library-form">
                  <input type="hidden" id="edit-library-id" value="${library.libraryId}">
                  <div class="form-group">
                      <label for="edit-library-name">Nombre:</label>
                      <input type="text" id="edit-library-name" value="${library.nameLibrary}" required>
                  </div>
                  <div class="form-group">
                      <label for="edit-library-location">Ubicación:</label>
                      <input type="text" id="edit-library-location" value="${library.location}" required>
                  </div>
                  <div class="form-group">
                      <label for="edit-opening-time">Hora de Apertura:</label>
                      <input type="time" id="edit-opening-time" value="${library.openingTime}" required>
                  </div>
                  <div class="form-group">
                      <label for="edit-closing-time">Hora de Cierre:</label>
                      <input type="time" id="edit-closing-time" value="${library.closingTime}" required>
                  </div>
                  <div class="form-group">
                      <label for="edit-library-capacity">Capacidad:</label>
                      <input type="number" id="edit-library-capacity" value="${library.capacity}" required>
                  </div>
                  <div class="form-actions">
                      <button type="button" class="btn cancel-btn" onclick="closeModal('edit-modal')">Cancelar</button>
                      <button type="submit" class="btn submit-btn">Actualizar</button>
                  </div>
              </form>
          </div>
      `;
      
      document.body.appendChild(modal);
  
      document.getElementById("edit-library-form").addEventListener('submit', function(event) {
          event.preventDefault();
          updateLibrary();
      });
    } catch (error) {
      console.error("Error al cargar la biblioteca:", error);
      showModal('Error', "Ocurrió un error al cargar la biblioteca: " + error.message, 'error');
    }
  }
  // Actualizar una biblioteca
  async function updateLibrary() {
    const id = document.getElementById("edit-library-id").value;
    const name = document.getElementById("edit-library-name").value.trim();
    const location = document.getElementById("edit-library-location").value.trim();
    const openingTime = document.getElementById("edit-opening-time").value;
    const closingTime = document.getElementById("edit-closing-time").value;
    const capacity = document.getElementById("edit-library-capacity").value.trim();
  
    if (!name || !location || !openingTime || !closingTime || !capacity) {
        showModal('Error', "Por favor, completa todos los campos.", 'error');
        return;
    }
  
    const libraryData = {
        libraryId: parseInt(id),
        nameLibrary: name,
        location: location,
        openingTime: openingTime,
        closingTime: closingTime,
        capacity: parseInt(capacity, 10)
    };
  
    try {
        const response = await fetch(`${API_URL}/${id}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(libraryData)
        });
  
        if (response.ok) {
            allLibraries = allLibraries.map(library => 
                library.libraryId === parseInt(id) ? libraryData : library
            );
            renderLibraries(allLibraries);
            closeModal('edit-modal');
            showModal('Éxito', 'Biblioteca actualizada correctamente', 'success');
        } else {
            const errorText = await response.text();
            showModal('Error', `Error al actualizar la biblioteca: ${errorText}`, 'error');
        }
    } catch (error) {
        console.error("Error al actualizar la biblioteca:", error);
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

  async function deleteLibrary(libraryId) {
    // Usando modal personalizado para confirmar en lugar de confirm nativo
    const modal = document.createElement('div');
    modal.className = 'modal-overlay';
    modal.id = 'confirm-delete-modal';
    
    modal.innerHTML = `
        <div class="modal-content warning">
            <span class="close-modal" onclick="closeModal('confirm-delete-modal')">&times;</span>
            <h2>Confirmar Eliminación</h2>
            <p>¿Estás seguro de eliminar esta biblioteca?</p>
            <div class="form-actions">
                <button class="btn cancel-btn" onclick="closeModal('confirm-delete-modal')">Cancelar</button>
                <button class="btn delete-btn" id="confirm-delete-btn">Eliminar</button>
            </div>
        </div>
    `;
    
    document.body.appendChild(modal);
  
    // Agregar evento al botón de confirmación
    document.getElementById("confirm-delete-btn").addEventListener('click', async function() {
      closeModal('confirm-delete-modal');
      
      try {
        const response = await fetch(`${API_URL}/${libraryId}`, {
          method: "DELETE"
        });
  
        if (response.ok) {
          allLibraries = allLibraries.filter(lib => lib.libraryId !== libraryId);
          renderLibraries(allLibraries);
          showModal('Éxito', 'Biblioteca eliminada correctamente', 'success');
        } else {
          const errorText = await response.text();
          showModal('Error', `Error al eliminar la biblioteca: ${errorText}`, 'error');
        }
      } catch (error) {
        console.error("Error al eliminar la biblioteca:", error);
        showModal('Error', "Ocurrió un error al eliminar la biblioteca: " + error.message, 'error');
      }
    });
  }
