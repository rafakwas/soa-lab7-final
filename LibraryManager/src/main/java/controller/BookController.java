package controller;

import data.BookRepositoryRemote;
import model.Book;
import model.Pair;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.naming.NamingException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

@Stateless
@SessionScoped
@Named("bookcontroller")
@Local(BookControllerInterface.class)
public class BookController implements BookControllerInterface {
    private final static Logger LOGGER = Logger.getLogger(BookController.class.toString());

    @EJB
    BookRepositoryRemote bookRepositoryRemote;


    private Book book;
    private String output;

    private List<String> authors;
    private List<Pair> titles;
    private String isbn;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void addBook() {
        book = new Book();
        book.setTitleList(Arrays.asList(new Pair("Polski", "Rzeka"), new Pair("Angielski", "River")));
        book.setAuthorList(Arrays.asList("Andrzej Kołodziejko", "Bisurman Tatrzański", "Hędycwoż Międzyrzecki"));
        book.setIsbn("123-456-789");
        book.setReserved(false);
        book.setRented(false);
        bookRepositoryRemote.persist(book);
    }

    public List<Book> getBooks() {
        List<Book> bookList = bookRepositoryRemote.getAllBooks();
        if (bookList != null && !bookList.isEmpty()) {
            Collections.sort(bookList, new Comparator<Book>() {
                @Override
                public int compare(Book o1, Book o2) {
                    Integer id1 = o1.getId();
                    Integer id2 = o2.getId();
                    return id1.compareTo(id2);
                }
            });
        }
        return bookList;
    }

    public void reserveBook(Book book,String user) {
        bookRepositoryRemote.reserve(book,user);
        LOGGER.info(() -> "Book reserved by " + user);
    }

    public void rentBook(Book book,String user) {
        bookRepositoryRemote.rent(book,user);
        LOGGER.info(() -> "Book rented by " + user);
    }

    public void returning(Book book,String user) {
        bookRepositoryRemote.returning(book,user);
        LOGGER.info(() -> "Book returned by " + user);
    }

    public void removeBooks() {
        bookRepositoryRemote.removeBookstore();
    }

    @Override
    public void log() {
        LOGGER.info(() -> "MDB triggered this!");
    }

    @Override
    public List<String> getNotifications() {
        return bookRepositoryRemote.getNotifications();
    }

    @Override
    public List<String> getConfirmations(String user) {
        return bookRepositoryRemote.getConfirmations(user);
    }
}