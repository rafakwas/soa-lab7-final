package controller;

import data.BookRepositoryRemote;
import model.Book;
import model.Pair;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import javax.naming.NamingException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ManagedBean
@ViewScoped
@Named("formcontroller")
public class FormController implements Serializable{

    @EJB
    BookRepositoryRemote bookRepositoryRemote;

    private String title;
    private String author;
    private String isbn;
    private String output;


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

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void addBook() {
        Book book = new Book();
        String[] titles = title.split(",");
        String[] authors = author.split(",");
        List<Pair> pairs = new ArrayList<>();
        for (String temp : titles) {
            String[] p = temp.split(":");
            pairs.add(new Pair(p[0],p[1]));
        }
        book.setAuthorList(new ArrayList<String>(Arrays.asList(authors)));
        book.setTitleList(new ArrayList<Pair>(pairs));
        book.setIsbn(isbn);
        book.setReserved(false);
        book.setRented(false);
        bookRepositoryRemote.persist(book);
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public void submit() {
        output = "hello world";
    }
}
