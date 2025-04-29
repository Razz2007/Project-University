    package com.sena.crud_2899747.model;

    import jakarta.persistence.Column;
    import jakarta.persistence.Entity;
    import jakarta.persistence.GeneratedValue;
    import jakarta.persistence.GenerationType;
    import jakarta.persistence.Id;
    @Entity(name = "book")
    public class book {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "book_id")
        private int bookId;

        // Evitamos problemas de serialización en la relación ManyToOne


        @Column(name = "title", length = 200, nullable = false)
        private String title;

        @Column(name = "author", length = 100, nullable = false)
        private String author;

        @Column(name = "publisher", length = 100, nullable = false)
        private String publisher;

        @Column(name = "isbn", length = 20, nullable = false, unique = true)
        private String isbn;

        @Column(name = "publication_year", nullable = false)
        private int publicationYear;

        @Column(name = "category", length = 50, nullable = false)
        private String category;

        @Column(name = "status", nullable = false, columnDefinition = "boolean default true")
        private boolean status;

        // Constructor sin parámetros
        public book() {
        }

        // Constructor con todos los parámetros – se asigna también el valor de status
        public book(int bookId, String title, String author, String publisher, String isbn, int publicationYear, String category, boolean status) {
            this.bookId = bookId;
            this.title = title;
            this.author = author;
            this.publisher = publisher;
            this.isbn = isbn;
            this.publicationYear = publicationYear;
            this.category = category;
            this.status = status;  // Se asigna el valor de status
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
