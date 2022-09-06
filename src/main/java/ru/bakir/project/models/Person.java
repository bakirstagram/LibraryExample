package ru.bakir.project.models;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "fio")
    private String fio;
    @Column(name = "born")
    private int born;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;
    public Person(){

    }

    public Person(int id, String fio, int born) {
        this.id = id;
        this.fio = fio;
        this.born = born;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public int getBorn() {
        return born;
    }

    public void setBorn(int born) {
        this.born = born;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}