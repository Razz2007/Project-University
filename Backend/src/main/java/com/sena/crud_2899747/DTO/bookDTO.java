package com.sena.crud_2899747.DTO;

public class bookDTO {

    private int bookId;
    private String title;
    private String author;
    private String publisher;
    private String isbn;
    private int publicationYear;
    private String category;
    private boolean status;

    // Constructor vacío
    public bookDTO() {
    }

    // Constructor con todos los campos
    public bookDTO(int bookId, String title, String author, String publisher, String isbn, int publicationYear, String category, boolean status) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.category = category;
        this.status = status;
    }

    // Getters y Setters
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
