package com.example.java17il2022.week4.demo4;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;
@Entity
@Table(name = "author_book")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Author_Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int author_book_table_id;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "b_id")
    private Book book;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "a_id")
    private  Author author;

    @Override
    public String toString() {
        return "Author_Book{" +
                "author_book_table_id=" + author_book_table_id +
                ", book=" + book +
                ", author=" + author +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author_Book)) return false;
        Author_Book that = (Author_Book) o;
        return author_book_table_id == that.author_book_table_id && Objects.equals(book, that.book) && Objects.equals(author, that.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author_book_table_id, book, author);
    }

    public void addAuthor_Book(EntityTransaction ab) {
    }

    public void commit() {
    }
}
