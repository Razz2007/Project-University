// Variables globales
const API_URL = 'http://localhost:8085/api/v1/book';
let allBooks = []; // Variable global para almacenar los libros

// Función que se ejecuta cuando el DOM está completamente cargado
document.addEventListener('DOMContentLoaded', function() {
    // Obtener todos los libros al cargar la página
    getAllBooks();
    
    // Configurar el evento de envío del formulario para agregar libros
    const bookForm = document.getElementById('book-form');
    if (bookForm) {
        bookForm.addEventListener('submit', handleFormSubmit);
    }
    
    // Configurar el evento del botón de búsqueda
    const searchButton = document.getElementById('search-button');
    if (searchButton) {
        searchButton.addEventListener('click', handleSearch);
    }
    
    // Configurar búsqueda al presionar Enter en el campo de búsqueda
    const searchInput = document.getElementById('search-book');
    if (searchInput) {
        searchInput.addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                handleSearch();
            }
        });
        // Añadir la búsqueda en tiempo real como en degree.js
        searchInput.addEventListener('input', filterBooks);
    }
    
    // Validación de ISBN en tiempo real
    const isbnInput = document.getElementById('book-isbn');
    if (isbnInput) {
        isbnInput.addEventListener('blur', validateISBN);
    }
});

// Función para obtener todos los libros
function getAllBooks() {
    fetch(API_URL + '/')
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al obtener los libros');
            }
            return response.json();
        })
        .then(books => {
            allBooks = books; // Guardar en la variable global
            displayBooks(books);
        })
        .catch(error => {
            showModal('Error', error.message, 'error');
            console.error('Error:', error);
        });
}

// Función para mostrar los libros en el DOM
function displayBooks(books) {
    const booksContainer = document.getElementById('books-list');
    
    // Limpiar el contenedor antes de agregar nuevos libros
    booksContainer.innerHTML = '';
    
    if (books.length === 0) {
        booksContainer.innerHTML = '<p class="no-results">No se encontraron libros.</p>';
        return;
    }
    
    // Crear una tarjeta para cada libro
    books.forEach(book => {
        const bookCard = document.createElement('div');
        bookCard.className = 'card';
        bookCard.dataset.id = book.bookId;
        
        const statusClass = book.status ? 'available' : 'unavailable';
        const statusText = book.status ? 'Disponible' : 'No disponible';
        
        bookCard.innerHTML = `
            <div class="card-content">
                <h3 class="card-title">${book.title}</h3>
                <p class="card-info"><strong>Autor:</strong> ${book.author}</p>
                <p class="card-info"><strong>Editorial:</strong> ${book.publisher}</p>
                <p class="card-info"><strong>ISBN:</strong> ${book.isbn}</p>
                <p class="card-info"><strong>Año:</strong> ${book.publicationYear}</p>
                <p class="card-info"><strong>Categoría:</strong> ${book.category}</p>
                <p class="card-info"><strong>Estado:</strong> <span class="${statusClass}">${statusText}</span></p>
                <div class="card-actions">
                    <button class="btn edit-btn" onclick="showEditModal(${book.bookId})"><i class="fas fa-edit"></i> Editar</button>
                    <button class="btn delete-btn" onclick="deleteBook(${book.bookId})"><i class="fas fa-trash"></i> Eliminar</button>
                </div>
            </div>
        `;
        
        booksContainer.appendChild(bookCard);
    });
}

// Función para filtrar libros en tiempo real (como en degree.js)
function filterBooks() {
    const searchValue = document.getElementById('search-book').value.toLowerCase();

    const filteredBooks = allBooks.filter(book => 
        book.title.toLowerCase().includes(searchValue) ||
        book.author.toLowerCase().includes(searchValue) ||
        book.category.toLowerCase().includes(searchValue) ||
        book.isbn.toLowerCase().includes(searchValue)
    );

    displayBooks(filteredBooks);
}

// Función para validar si un ISBN ya existe
function validateISBN(event) {
    const isbnInput = event ? event.target : document.getElementById('book-isbn');
    const isbn = isbnInput.value.trim();
    const currentBookId = document.getElementById('book-form').dataset.id || 0;
    
    // Verificar formato de ISBN (básico)
    if (isbn && !isValidISBNFormat(isbn)) {
        showInputError(isbnInput, 'El formato de ISBN no es válido');
        return false;
    }
    
    // Verificar si el ISBN ya existe en otro libro
    const duplicateBook = allBooks.find(book => 
        book.isbn === isbn && book.bookId !== parseInt(currentBookId)
    );
    
    if (duplicateBook) {
        showInputError(isbnInput, 'Este ISBN ya está registrado en otro libro');
        return false;
    } else {
        clearInputError(isbnInput);
        return true;
    }
}

// Función para validar el formato de ISBN (implementación básica)
function isValidISBNFormat(isbn) {
    // ISBN-10: 10 dígitos (puede incluir guiones)
    // ISBN-13: 13 dígitos (puede incluir guiones)
    // Esta es una validación sencilla, se puede mejorar según necesidades
    const cleanISBN = isbn.replace(/-/g, '');
    return /^\d{10}$|^\d{13}$/.test(cleanISBN);
}

// Mostrar error en el input
function showInputError(inputElement, message) {
    // Eliminar mensaje de error previo si existe
    clearInputError(inputElement);
    
    // Agregar clase de error al input
    inputElement.classList.add('input-error');
    
    // Crear y mostrar mensaje de error
    const errorMessage = document.createElement('div');
    errorMessage.className = 'error-message';
    errorMessage.textContent = message;
    
    // Insertar mensaje después del input
    inputElement.parentNode.appendChild(errorMessage);
}

// Limpiar error del input
function clearInputError(inputElement) {
    inputElement.classList.remove('input-error');
    
    // Eliminar mensaje de error si existe
    const errorMessage = inputElement.parentNode.querySelector('.error-message');
    if (errorMessage) {
        errorMessage.remove();
    }
}

// Función para manejar el envío del formulario
async function handleFormSubmit(e) {
    e.preventDefault();
    
    // Obtener los valores del formulario
    const bookId = document.getElementById('book-form').dataset.id || 0;
    const title = document.getElementById('book-title').value.trim();
    const author = document.getElementById('book-author').value.trim();
    const publisher = document.getElementById('book-publisher').value.trim();
    const isbn = document.getElementById('book-isbn').value.trim();
    const publicationYear = parseInt(document.getElementById('book-publicationYear').value);
    const category = document.getElementById('book-category').value.trim();
    const status = document.getElementById('book-status').value === 'true';
    
    // Validar los campos
    if (!title || !author || !publisher || !isbn || !publicationYear || !category) {
        showModal('Error', 'Por favor, complete todos los campos obligatorios.', 'error');
        return;
    }
    
    // Validar formato y duplicado de ISBN
    if (!validateISBN()) {
        return;
    }
    
    // Crear el objeto libro
    const book = {
        bookId: parseInt(bookId),
        title,
        author,
        publisher,
        isbn,
        publicationYear,
        category,
        status
    };
    
    // Determinar si es una actualización o una creación
    const isUpdate = bookId > 0;
    
    try {
        // URL y método para la petición
        const url = isUpdate ? `${API_URL}/${bookId}` : `${API_URL}/`;
        const method = isUpdate ? 'PUT' : 'POST';
        
        // Realizar la petición
        const response = await fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(book)
        });

        if (!response.ok) {
            const errorData = await response.json();
            // Verificar si el error es de ISBN duplicado
            if (errorData.message && errorData.message.includes('ISBN')) {
                showInputError(document.getElementById('book-isbn'), 'Este ISBN ya está registrado en la base de datos');
                throw new Error('ISBN duplicado: ' + errorData.message);
            } else {
                throw new Error('Error al ' + (isUpdate ? 'actualizar' : 'guardar') + ' el libro: ' + errorData.message);
            }
        }

        const data = await response.json();
        showModal('Éxito', data.message || 'Libro ' + (isUpdate ? 'actualizado' : 'agregado') + ' correctamente', 'success');
        resetForm();
        getAllBooks();
    } catch (error) {
        // Solo mostrar modal si no es un error de ISBN (ya se muestra en el input)
        if (!error.message.includes('ISBN duplicado')) {
            showModal('Error', error.message, 'error');
        }
        console.error('Error:', error);
    }
}

// Función para mostrar el modal de edición (como en degree.js)
async function showEditModal(id) {
    try {
        const response = await fetch(`${API_URL}/${id}`);
        if (!response.ok) {
            const errorText = await response.text();
            showModal('Error', `Error al obtener el libro: ${errorText}`, 'error');
            return;
        }
        
        const book = await response.json();
        
        const modal = document.createElement('div');
        modal.className = 'modal-overlay';
        modal.id = 'edit-modal';
        
        modal.innerHTML = `
            <div class="modal-content">
                <span class="close-modal" onclick="closeModal('edit-modal')">&times;</span>
                <h2>Editar Libro</h2>
                <form id="edit-book-form">
                    <input type="hidden" id="edit-book-id" value="${book.bookId}">
                    <div class="form-group">
                        <label for="edit-book-title">Título:</label>
                        <input type="text" id="edit-book-title" value="${book.title}" required>
                    </div>
                    <div class="form-group">
                        <label for="edit-book-author">Autor:</label>
                        <input type="text" id="edit-book-author" value="${book.author}" required>
                    </div>
                    <div class="form-group">
                        <label for="edit-book-publisher">Editorial:</label>
                        <input type="text" id="edit-book-publisher" value="${book.publisher}" required>
                    </div>
                    <div class="form-group">
                        <label for="edit-book-isbn">ISBN:</label>
                        <input type="text" id="edit-book-isbn" value="${book.isbn}" required>
                    </div>
                    <div class="form-group">
                        <label for="edit-book-year">Año de publicación:</label>
                        <input type="number" id="edit-book-year" value="${book.publicationYear}" required>
                    </div>
                    <div class="form-group">
                        <label for="edit-book-category">Categoría:</label>
                        <input type="text" id="edit-book-category" value="${book.category}" required>
                    </div>
                    <div class="form-group">
                        <label for="edit-book-status">Estado:</label>
                        <select id="edit-book-status">
                            <option value="true" ${book.status ? 'selected' : ''}>Disponible</option>
                            <option value="false" ${!book.status ? 'selected' : ''}>No disponible</option>
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

        // Añadir validación de ISBN al modal de edición
        const editIsbnInput = document.getElementById('edit-book-isbn');
        editIsbnInput.addEventListener('blur', validateEditISBN);

        document.getElementById('edit-book-form').addEventListener('submit', function(event) {
            event.preventDefault();
            updateBook();
        });
    } catch (error) {
        console.error("Error al cargar el libro:", error);
        showModal('Error', "Ocurrió un error al cargar el libro: " + error.message, 'error');
    }
}

// Validar ISBN en el formulario de edición
function validateEditISBN() {
    const isbnInput = document.getElementById('edit-book-isbn');
    const isbn = isbnInput.value.trim();
    const currentBookId = document.getElementById('edit-book-id').value;
    
    // Verificar formato de ISBN
    if (isbn && !isValidISBNFormat(isbn)) {
        showInputError(isbnInput, 'El formato de ISBN no es válido');
        return false;
    }
    
    // Verificar si el ISBN ya existe en otro libro
    const duplicateBook = allBooks.find(book => 
        book.isbn === isbn && book.bookId !== parseInt(currentBookId)
    );
    
    if (duplicateBook) {
        showInputError(isbnInput, 'Este ISBN ya está registrado en otro libro');
        return false;
    } else {
        clearInputError(isbnInput);
        return true;
    }
}

// Función para actualizar un libro (desde el modal)
async function updateBook() {
    const bookId = document.getElementById("edit-book-id").value;
    const title = document.getElementById("edit-book-title").value.trim();
    const author = document.getElementById("edit-book-author").value.trim();
    const publisher = document.getElementById("edit-book-publisher").value.trim();
    const isbn = document.getElementById("edit-book-isbn").value.trim();
    const publicationYear = document.getElementById("edit-book-year").value.trim();
    const category = document.getElementById("edit-book-category").value.trim();
    const status = document.getElementById("edit-book-status").value === 'true';

    if (!title || !author || !publisher || !isbn || !publicationYear || !category) {
        showModal('Error', "Por favor, completa todos los campos.", 'error');
        return;
    }

    // Validar ISBN antes de enviar
    if (!validateEditISBN()) {
        return;
    }

    const bookData = {
        bookId: parseInt(bookId),
        title,
        author,
        publisher,
        isbn,
        publicationYear: parseInt(publicationYear),
        category,
        status
    };

    try {
        const response = await fetch(`${API_URL}/${bookId}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(bookData)
        });

        if (!response.ok) {
            const errorData = await response.json();
            // Verificar si el error es de ISBN duplicado
            if (errorData.message && errorData.message.includes('ISBN')) {
                showInputError(document.getElementById('edit-book-isbn'), 'Este ISBN ya está registrado en la base de datos');
                throw new Error('ISBN duplicado: ' + errorData.message);
            } else {
                throw new Error('Error al actualizar el libro: ' + errorData.message);
            }
        }

        const data = await response.json();
        allBooks = allBooks.map(book => 
            book.bookId === parseInt(bookId) ? bookData : book
        );
        displayBooks(allBooks);
        closeModal('edit-modal');
        showModal('Éxito', data.message || 'Libro actualizado correctamente', 'success');
    } catch (error) {
        // Solo mostrar modal si no es un error de ISBN (ya se muestra en el input)
        if (!error.message.includes('ISBN duplicado')) {
            showModal('Error', error.message, 'error');
        }
        console.error("Error al actualizar el libro:", error);
    }
}

// Función para eliminar un libro (mejorada)
async function deleteBook(id) {
    try {
        const confirmed = confirm("¿Está seguro de que desea eliminar este libro?");
        
        if (!confirmed) return;
        
        const response = await fetch(`${API_URL}/${id}`, {
            method: 'DELETE'
        });
        
        if (response.ok) {
            const data = await response.json();
            allBooks = allBooks.filter(book => book.bookId !== id);
            displayBooks(allBooks);
            showModal('Éxito', data.message || 'Libro eliminado correctamente', 'success');
        } else {
            const errorText = await response.text();
            showModal('Error', `Error al eliminar el libro: ${errorText}`, 'error');
        }
    } catch (error) {
        console.error("Error al eliminar el libro:", error);
        showModal('Error', "Ocurrió un error durante la eliminación: " + error.message, 'error');
    }
}

// Función para manejar la búsqueda
function handleSearch() {
    const searchTerm = document.getElementById('search-book').value.trim();
    
    if (!searchTerm) {
        getAllBooks();
        return;
    }
    
    // Intentar buscar por título primero
    fetch(`${API_URL}/title/${searchTerm}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al buscar libros');
            }
            return response.json();
        })
        .then(books => {
            if (books.length > 0) {
                displayBooks(books);
            } else {
                // Si no hay resultados por título, buscar por autor
                return fetch(`${API_URL}/author/${searchTerm}`);
            }
        })
        .then(response => {
            if (response) {
                return response.json();
            }
            return null;
        })
        .then(books => {
            if (books && books.length > 0) {
                displayBooks(books);
            } else if (books) {
                displayBooks([]);
            }
        })
        .catch(error => {
            showModal('Error', error.message, 'error');
            console.error('Error:', error);
        });
}

// Función para reiniciar el formulario
function resetForm() {
    const form = document.getElementById('book-form');
    form.reset();
    delete form.dataset.id;
    
    // Limpiar cualquier mensaje de error
    const inputs = form.querySelectorAll('input, select');
    inputs.forEach(input => clearInputError(input));
    
    // Restaurar el texto del botón de envío
    document.querySelector('.submit-button').textContent = 'Agregar Libro';
}

// Función para mostrar modales (como en degree.js)
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

// Función para cerrar modales (como en degree.js)
function closeModal(modalId) {
    const modal = document.getElementById(modalId);
    if (modal) modal.remove();
}