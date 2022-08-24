package com.example.java17il2022.week4.demo4;

import com.example.java17il2022.week4.demo3.Teacher_Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Entity
@Table(name = "book_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ISBN;
    @Column(name = "tile")
    private String title;

    public int getISBN() {
        return ISBN;
    }

    public void setISBN(int ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<Author_Book> author_books = new ArrayList<>();

    public List<Author_Book> getAuthor_books() {
        return author_books;
    }
    public void setAuthor_books(List<Author_Book> author_books){
        this.author_books=author_books;
    }

    @Override
    public String toString() {
        return "Book{" +
                "ISBN=" + ISBN +
                ", title='" + title + '\'' +
                ", author_books=" + author_books +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return ISBN == book.ISBN && Objects.equals(title, book.title) && Objects.equals(author_books, book.author_books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ISBN, title, author_books);
    }
}
