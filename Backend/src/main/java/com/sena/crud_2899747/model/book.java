    package com.sena.crud_2899747.model;



    import jakarta.persistence.Column;
    import jakarta.persistence.Entity;
    import jakarta.persistence.GeneratedValue;
    import jakarta.persistence.GenerationType;
    import jakarta.persistence.Id;
    import jakarta.persistence.JoinColumn;
    import jakarta.persistence.ManyToOne;

    @Entity (name = "book")
    public class book {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "book_id")
        private int bookId;

        @ManyToOne
        @JoinColumn(name = "library_id")
        private library library; 


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


        public book(int bookId, String title, String author, String publisher, String isbn, int publicationYear, String category) {
            this.bookId = bookId;
            this.title = title;
            this.author = author;
            this.publisher = publisher;
            this.isbn = isbn;
            this.publicationYear = publicationYear;
            this.category = category;
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
    }

