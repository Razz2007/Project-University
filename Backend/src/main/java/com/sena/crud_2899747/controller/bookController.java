package com.sena.crud_2899747.controller;

import com.sena.crud_2899747.DTO.bookDTO;
import com.sena.crud_2899747.DTO.responseDTO;
import com.sena.crud_2899747.model.book;
import com.sena.crud_2899747.service.bookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/book")
public class bookController {

    @Autowired
    private bookService bookService;

    // POST: Registrar un nuevo libro
    @PostMapping("/")
    public ResponseEntity<Object> registerBook(@RequestBody bookDTO book) {
        responseDTO response = bookService.save(book);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // GET: Obtener la lista de todos los libros
    @GetMapping("/")
    public ResponseEntity<Object> getAllBooks() {
        List<book> bookList = bookService.findAll();
        return new ResponseEntity<>(bookList, HttpStatus.OK);
    }

    // GET: Obtener un libro por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getBookById(@PathVariable int id) {
        Optional<book> bookOpt = bookService.findById(id);
        if (!bookOpt.isPresent()) {
            return new ResponseEntity<>(
                    new responseDTO(HttpStatus.NOT_FOUND.toString(), "Libro no encontrado"),
                    HttpStatus.NOT_FOUND
            );
        }
        return new ResponseEntity<>(bookOpt.get(), HttpStatus.OK);
    }

    // PUT: Actualizar la información de un libro existente
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateBook(@PathVariable int id, @RequestBody bookDTO book) {
        responseDTO response = bookService.updateBook(id, book);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // DELETE: Eliminar un libro por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBook(@PathVariable int id) {
        responseDTO response = bookService.deleteBook(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    // GET: Buscar libros por título
    @GetMapping("/title/{title}")
    public ResponseEntity<Object> getBooksByTitle(@PathVariable String title) {
        List<book> books = bookService.findByTitle(title);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
    
    // GET: Buscar libros por autor
    @GetMapping("/author/{author}")
    public ResponseEntity<Object> getBooksByAuthor(@PathVariable String author) {
        List<book> books = bookService.findByAuthor(author);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
    
    // GET: Buscar libros por categoría
    @GetMapping("/category/{category}")
    public ResponseEntity<Object> getBooksByCategory(@PathVariable String category) {
        List<book> books = bookService.findByCategory(category);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
    
    // GET: Buscar libros por año de publicación
    @GetMapping("/year/{year}")
    public ResponseEntity<Object> getBooksByYear(@PathVariable int year) {
        List<book> books = bookService.findByPublicationYear(year);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
    
    // GET: Buscar libros por estado (activo/inactivo)
    @GetMapping("/status/{status}")
    public ResponseEntity<Object> getBooksByStatus(@PathVariable boolean status) {
        List<book> books = bookService.findByStatus(status);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
    
    // GET: Buscar libros por ISBN
    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<Object> getBooksByIsbn(@PathVariable String isbn) {
        List<book> books = bookService.findByIsbn(isbn);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
}