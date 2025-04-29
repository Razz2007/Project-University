// URL base de la API
const API_URL = "http://localhost:8085/api/v1/loan"; 
const STUDENT_API_URL = "http://localhost:8085/api/v1/student";
const BOOK_API_URL = "http://localhost:8085/api/v1/book";

let allLoans = [];

document.addEventListener('DOMContentLoaded', function() {
    loadLoans();    
    loadStudents('student-id');
    loadBooks('book-id');

    const loanForm = document.getElementById("loan-form");
    if (loanForm) {
        loanForm.addEventListener('submit', registerLoan);
    }

    const searchButton = document.getElementById('search-button');
    if (searchButton) {
        searchButton.addEventListener('click', filterLoansByStudent);
    }

    const searchInput = document.getElementById('search-loan');
    if (searchInput) {
        searchInput.addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                filterLoansByStudent();
            }
        });
    }
});

// Cargar todos los préstamos
async function loadLoans() {
    try {
        const response = await fetch(API_URL + "/");
        if (!response.ok) {
            throw new Error(await response.text());
        }
        allLoans = await response.json();
        renderLoans(allLoans);
    } catch (error) {
        console.error(error);
        showModal('Error', "Ocurrió un error al cargar préstamos: " + error.message, 'error');
    }
}

// Cargar estudiantes dinámicamente en un select
async function loadStudents(targetSelectId) {
    try {
        const response = await fetch(STUDENT_API_URL + "/");
        if (!response.ok) {
            throw new Error(await response.text());
        }
        const students = await response.json();
        const studentSelect = document.getElementById(targetSelectId);
        if (!studentSelect) {
            console.error(`El elemento ${targetSelectId} no se encontró en el DOM`);
            return;
        }

        studentSelect.innerHTML = '<option value="">Seleccione un estudiante</option>'; // Opción por defecto

        students.forEach(student => {
            const option = document.createElement("option");
            option.value = student.studentId;
            option.textContent = `${student.firstName} ${student.lastName}`;
            studentSelect.appendChild(option);
        });
    } catch (error) {
        console.error(error);
        showModal('Error', "Ocurrió un error al obtener los estudiantes: " + error.message, 'error');
    }
}

// Cargar libros dinámicamente en un select
// Cargar libros dinámicamente en un select - VERSIÓN CORREGIDA
async function loadBooks(targetSelectId) {
    try {
        const response = await fetch(BOOK_API_URL + "/");
        if (!response.ok) {
            throw new Error(await response.text());
        }
        const books = await response.json();
        const bookSelect = document.getElementById(targetSelectId);
        if (!bookSelect) {
            console.error(`El elemento ${targetSelectId} no se encontró en el DOM`);
            return;
        }

        bookSelect.innerHTML = '<option value="">Seleccione un libro</option>'; // Opción por defecto

        books.forEach(book => {
            const option = document.createElement("option");
            // CORRECCIÓN: Usar la propiedad bookId en lugar de id
            option.value = book.bookId; // CAMBIADO: id -> bookId
            option.textContent = book.title;
            bookSelect.appendChild(option);
        });
    } catch (error) {
        console.error(error);
        showModal('Error', "Ocurrió un error al obtener los libros: " + error.message, 'error');
    }
}

// Filtrar préstamos por nombre de estudiante
async function filterLoansByStudent() {
    const searchValue = document.getElementById('search-loan').value.trim();
    
    try {
        if (searchValue === '') {
            // Si el campo está vacío, cargar todos los préstamos
            loadLoans();
            return;
        }
        
        const response = await fetch(`${API_URL}/filter/${searchValue}`);
        if (!response.ok) {
            throw new Error(await response.text());
        }
        const filteredLoans = await response.json();
        renderLoans(filteredLoans);
    } catch (error) {
        console.error(error);
        showModal('Error', "Error al filtrar préstamos: " + error.message, 'error');
    }
}

// Renderizar los préstamos en la página
// Renderizar los préstamos en la página - VERSIÓN CORREGIDA
function renderLoans(loans) {
    const loanContainer = document.getElementById("loans-list");
    loanContainer.innerHTML = '';

    if (loans.length === 0) {
        loanContainer.innerHTML = '<p class="no-results">No hay préstamos para mostrar.</p>';
        return;
    }

    loans.forEach(loan => {
        if (!loan || !loan.loanId) return;

        const loanCard = document.createElement('div');
        loanCard.className = 'card';
        loanCard.dataset.id = loan.loanId;

        // Formatear fechas correctamente - ajustando para mostrar la fecha real seleccionada
        let loanDateObj = null;
        let returnDateObj = null;
        
        if (loan.loanDate) {
            loanDateObj = new Date(loan.loanDate);
            // Ajuste para mostrar la fecha correcta
            loanDateObj.setMinutes(loanDateObj.getMinutes() + loanDateObj.getTimezoneOffset());
        }
        
        if (loan.returnDate) {
            returnDateObj = new Date(loan.returnDate);
            // Ajuste para mostrar la fecha correcta
            returnDateObj.setMinutes(returnDateObj.getMinutes() + returnDateObj.getTimezoneOffset());
        }
        
        const loanDate = loanDateObj ? loanDateObj.toLocaleDateString() : 'No definida';
        const returnDate = returnDateObj ? returnDateObj.toLocaleDateString() : 'No definida';

        // Mostrar estado del préstamo
        const statusClass = loan.status ? 'status-active' : 'status-finished';
        const statusText = loan.status ? 'Activo' : 'Finalizado';

        loanCard.innerHTML = `
            <div class="card-content">
                <h3 class="card-title">Préstamo #${loan.loanId}</h3>
                <p class="card-info"><strong>Estudiante:</strong> ${loan.student?.firstName || ''} ${loan.student?.lastName || ''}</p>
                <p class="card-info"><strong>Libro:</strong> ${loan.book?.title || ''}</p>
                <p class="card-info"><strong>Fecha de préstamo:</strong> ${loanDate}</p>
                <p class="card-info"><strong>Fecha de devolución:</strong> ${returnDate}</p>
                <p class="card-info"><strong>Estado:</strong> <span class="${statusClass}">${statusText}</span></p>
                <div class="card-actions">
                    <button class="btn edit-btn" onclick="showEditModal(${loan.loanId})">
                        <i class="fas fa-edit"></i> Editar
                    </button>
                    <button class="btn delete-btn" onclick="deleteLoan(${loan.loanId})">
                        <i class="fas fa-trash"></i> Eliminar
                    </button>
                </div>
            </div>
        `;
        
        loanContainer.appendChild(loanCard);
    });
}

// Registrar nuevo préstamo
// Versión ajustada de la función registerLoan para depuración
// Modificación a la función registerLoan para corregir el problema de las fechas
async function registerLoan(event) {
    event.preventDefault();

    const studentId = document.getElementById("student-id").value.trim();
    const bookId = document.getElementById("book-id").value.trim();
    const loanDate = document.getElementById("loan-date").value.trim();
    const returnDate = document.getElementById("return-date").value.trim();
    const status = document.getElementById("loan-status").value === 'true';

    if (!studentId || !bookId || !loanDate) {
        showModal('Error', "Por favor, completa los campos obligatorios.", 'error');
        return;
    }

    // Corregir el problema de fechas usando fechas ISO
    // Esto asegura que la fecha enviada al servidor sea exactamente la seleccionada
    const loanDateObj = new Date(loanDate + 'T12:00:00');
    const returnDateObj = returnDate ? new Date(returnDate + 'T12:00:00') : null;
    
    const loanData = {
        student: { studentId: parseInt(studentId, 10) },
        book: { bookId: parseInt(bookId, 10) },
        loanDate: loanDateObj.toISOString(),
        returnDate: returnDateObj ? returnDateObj.toISOString() : null,
        status
    };

    console.log("Enviando datos de préstamo:", JSON.stringify(loanData));

    try {
        const response = await fetch(API_URL + "/", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(loanData)
        });

        console.log("Status code:", response.status);
        
        const responseData = await response.json();
        console.log("Respuesta del servidor:", responseData);

        if (response.ok) {
            await loadLoans();
            document.getElementById("loan-form").reset();
            showModal('Éxito', 'Préstamo registrado correctamente', 'success');
        } else {
            throw new Error(responseData.message || "Error del servidor");
        }
    } catch (error) {
        console.error("Error completo:", error);
        showModal('Error', "Ocurrió un error al registrar: " + error.message, 'error');
    }
}
// Eliminar un préstamo
async function deleteLoan(id) {
    try {
        const confirmed = confirm("¿Estás seguro de eliminar este préstamo?");
        if (!confirmed) return;

        const response = await fetch(API_URL + "/" + id, {
            method: "DELETE",
            headers: { "Content-Type": "application/json" }
        });

        if (response.ok) {
            allLoans = allLoans.filter(loan => loan.loanId !== id);
            renderLoans(allLoans);
            showModal('Éxito', 'Préstamo eliminado correctamente', 'success');
        } else {
            throw new Error(await response.text());
        }
    } catch (error) {
        console.error(error);
        showModal('Error', "Ocurrió un error al eliminar: " + error.message, 'error');
    }
}

// Mostrar el modal de edición de un préstamo
// Mostrar el modal de edición de un préstamo - VERSIÓN CORREGIDA
async function showEditModal(loanId) {
    try {
        const response = await fetch(`${API_URL}/${loanId}`);
        if (!response.ok) {
            throw new Error(await response.text());
        }

        const loan = await response.json();

        // Crear y agregar el modal al DOM
        const modal = document.createElement('div');
        modal.className = 'modal-overlay';
        modal.id = 'edit-modal';

        // Formatear fechas para el formulario - corregido para mostrar la fecha correcta
        let loanDateFormatted = '';
        let returnDateFormatted = '';
        
        if (loan.loanDate) {
            const loanDateObj = new Date(loan.loanDate);
            // Ajuste para obtener la fecha correcta
            loanDateObj.setMinutes(loanDateObj.getMinutes() + loanDateObj.getTimezoneOffset());
            loanDateFormatted = loanDateObj.toISOString().substring(0, 10);
        }
        
        if (loan.returnDate) {
            const returnDateObj = new Date(loan.returnDate);
            // Ajuste para obtener la fecha correcta
            returnDateObj.setMinutes(returnDateObj.getMinutes() + returnDateObj.getTimezoneOffset());
            returnDateFormatted = returnDateObj.toISOString().substring(0, 10);
        }

        modal.innerHTML = `
            <div class="modal-content">
                <span class="close-modal" onclick="closeModal('edit-modal')">&times;</span>
                <h2>Editar Préstamo</h2>
                <form id="edit-loan-form">
                    <input type="hidden" id="edit-loan-id" value="${loan.loanId}">
                    <div class="form-group">
                        <label>Estudiante:</label>
                        <select id="edit-student-id" required></select>
                    </div>
                    <div class="form-group">
                        <label>Libro:</label>
                        <select id="edit-book-id" required></select>
                    </div>
                    <div class="form-group">
                        <label>Fecha de Préstamo:</label>
                        <input type="date" id="edit-loan-date" value="${loanDateFormatted}" required>
                    </div>
                    <div class="form-group">
                        <label>Fecha de Devolución:</label>
                        <input type="date" id="edit-return-date" value="${returnDateFormatted}">
                    </div>
                    <div class="form-group">
                        <label>Estado:</label>
                        <select id="edit-loan-status" required>
                            <option value="true" ${loan.status ? 'selected' : ''}>Activo</option>
                            <option value="false" ${!loan.status ? 'selected' : ''}>Finalizado</option>
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

        // Cargar estudiantes y libros en los selectores
        await loadStudents('edit-student-id');
        await loadBooks('edit-book-id');

        // Establecer los valores actuales
        document.getElementById('edit-student-id').value = loan.student?.studentId || '';
        document.getElementById('edit-book-id').value = loan.book?.bookId || '';

        document.getElementById('edit-loan-form').addEventListener('submit', function(event) {
            event.preventDefault();
            updateLoan();
        });

    } catch (error) {
        console.error(error);
        showModal('Error', "Error al cargar datos del préstamo: " + error.message, 'error');
    }
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

// Cerrar modales
function closeModal(modalId) {
    const modal = document.getElementById(modalId);
    if (modal) {
        modal.remove();
    }
}