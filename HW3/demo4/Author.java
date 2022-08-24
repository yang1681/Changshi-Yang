package com.example.java17il2022.week4.demo4;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Table(name = "author_table")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int author_id;
    private  String name;

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Author(int author_id, String name) {
        this.author_id = author_id;
        this.name = name;
    }

   private List<Author_Book> author_book= new ArrayList<>();

    public List<Author_Book> getAuthor_book() {
        return author_book;
    }

    public void setAuthor_book(List<Author_Book> author_book) {
        this.author_book = author_book;
    }
    public void addAuthor_book(Author_Book author_book){
        this.author_book.add(author_book);

    }

    @Override
    public String toString() {
        return "Author{" +
                "author_id=" + author_id +
                ", name='" + name + '\'' +
                ", author_book=" + author_book +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author)) return false;
        Author author = (Author) o;

        return author_id == author.author_id && Objects.equals(name, author.name) && Objects.equals(author_book, author.author_book);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author_id, name, author_book);
    }
}
