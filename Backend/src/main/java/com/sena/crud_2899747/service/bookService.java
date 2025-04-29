package com.sena.crud_2899747.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sena.crud_2899747.DTO.responseDTO;
import com.sena.crud_2899747.DTO.bookDTO;
import com.sena.crud_2899747.model.book;
import com.sena.crud_2899747.repository.Ibook;

@Service
public class bookService {

    @Autowired
    private Ibook data;

    // Obtiene la lista de todos los libros
    public List<book> findAll() {
        return data.findAll();
    }

    // Busca un libro por su ID
    public Optional<book> findById(int id) {
        return data.findById(id);
    }

    // Elimina un libro según su ID
    public responseDTO deleteBook(int id) {
        Optional<book> bookOpt = findById(id);
        if (bookOpt.isEmpty()) {
            return new responseDTO(HttpStatus.NOT_FOUND.toString(), "Error: El libro no existe.");
        }
        data.deleteById(id);
        return new responseDTO(HttpStatus.OK.toString(), "Libro eliminado correctamente.");
    }

    // Guarda un nuevo libro con validaciones
    public responseDTO save(bookDTO bookDTO) {
        responseDTO validationResponse = validateBook(bookDTO);
        if (validationResponse != null) return validationResponse;

        book bookRegister = convertToModel(bookDTO);
        data.save(bookRegister);
        return new responseDTO(HttpStatus.OK.toString(), "Libro guardado correctamente.");
    }

    // Actualiza la información de un libro existente
    public responseDTO updateBook(int id, bookDTO dto) {
        Optional<book> bookOpt = data.findById(id);
        if (bookOpt.isEmpty()) {
            return new responseDTO(HttpStatus.NOT_FOUND.toString(), "Error: El libro con ID " + id + " no existe.");
        }

        responseDTO validationResponse = validateBook(dto);
        if (validationResponse != null) return validationResponse;

        book existingBook = bookOpt.get();
        existingBook.setTitle(dto.getTitle());
        existingBook.setAuthor(dto.getAuthor());
        existingBook.setPublisher(dto.getPublisher());
        existingBook.setIsbn(dto.getIsbn());
        existingBook.setPublicationYear(dto.getPublicationYear());
        existingBook.setCategory(dto.getCategory());
        existingBook.setStatus(dto.isStatus());

        data.save(existingBook);
        return new responseDTO(HttpStatus.OK.toString(), "Libro actualizado correctamente.");
    }

    // Validaciones comunes para evitar código repetitivo
    private responseDTO validateBook(bookDTO dto) {
        if (dto.getTitle() == null || dto.getTitle().isEmpty() || dto.getTitle().length() > 100) {
            return new responseDTO(HttpStatus.BAD_REQUEST.toString(), "El título debe tener entre 1 y 100 caracteres.");
        }
        if (dto.getAuthor() == null || dto.getAuthor().isEmpty() || dto.getAuthor().length() > 100) {
            return new responseDTO(HttpStatus.BAD_REQUEST.toString(), "El autor debe tener entre 1 y 100 caracteres.");
        }
        if (dto.getPublisher() == null || dto.getPublisher().isEmpty()) {
            return new responseDTO(HttpStatus.BAD_REQUEST.toString(), "La editorial es obligatoria.");
        }
        if (dto.getIsbn() == null || dto.getIsbn().isEmpty()) {
            return new responseDTO(HttpStatus.BAD_REQUEST.toString(), "El ISBN es obligatorio.");
        }
        if (dto.getPublicationYear() <= 0) {
            return new responseDTO(HttpStatus.BAD_REQUEST.toString(), "El año de publicación debe ser mayor que 0.");
        }
        if (dto.getCategory() == null || dto.getCategory().isEmpty()) {
            return new responseDTO(HttpStatus.BAD_REQUEST.toString(), "La categoría es obligatoria.");
        }
        return null; // Sin errores
    }

    // Conversión de `book` a `bookDTO`
    public bookDTO convertToDTO(book book) {
        return new bookDTO(
                book.getBookId(),
                book.getTitle(),
                book.getAuthor(),
                book.getPublisher(),
                book.getIsbn(),
                book.getPublicationYear(),
                book.getCategory(),
                book.isStatus()
        );
    }

    // Conversión de `bookDTO` a `book`
    public book convertToModel(bookDTO bookDTO) {
        return new book(
                bookDTO.getBookId(),
                bookDTO.getTitle(),
                bookDTO.getAuthor(),
                bookDTO.getPublisher(),
                bookDTO.getIsbn(),
                bookDTO.getPublicationYear(),
                bookDTO.getCategory(),
                bookDTO.isStatus()
        );
    }
    
    // Métodos adicionales basados en las consultas de Ibook
    public List<book> findByTitle(String title) {
        return data.findByTitleContainingIgnoreCase(title);
    }
    
    public List<book> findByAuthor(String author) {
        return data.findByAuthorContainingIgnoreCase(author);
    }
    
    public List<book> findByCategory(String category) {
        return data.findByCategory(category);
    }
    
    public List<book> findByPublicationYear(int year) {
        return data.findByPublicationYear(year);
    }
    
    public List<book> findByStatus(boolean status) {
        return data.findByStatus(status);
    }
    
    public List<book> findByIsbn(String isbn) {
        return data.findByIsbn(isbn);
    }
}