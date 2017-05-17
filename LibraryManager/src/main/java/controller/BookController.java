package controller;

import data.BookRepositoryRemote;
import model.Book;
import model.Pair;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
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

@ManagedBean
@ViewScoped
@Named("bookcontroller")
public class BookController {
    private final static Logger LOGGER = Logger.getLogger(BookController.class.toString());

    @Resource(mappedName = "java:/library/ConfirmationQueue")
    Queue queue;
    @Inject
    JMSContext jmsContext;
    @EJB
    BookRepositoryRemote bookRepositoryRemote;



    private Book book;
    private List<Book> books;

    private String output;

    private List<String> authors;
    private List<Pair> titles;
    private String isbn;

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

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
        String title = book.getTitleList().get(0).getValue();
        sendMessage("Book " +title+ " added to library");
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

    public void reserveBook(Book book) {
        bookRepositoryRemote.reserve(book);
        String title = book.getTitleList().get(0).getValue();
        sendMessage("Book " +title+ " reserved");
    }

    public void sendMessage(String message) {
        LOGGER.info(() -> "Message \"" + message + "\" sent");
        jmsContext.createProducer().send(queue,message);
    }

    public void rentBook(Book book) {
        bookRepositoryRemote.rent(book);
        String title = book.getTitleList().get(0).getValue();
        sendMessage("Book "+title+" rented");
    }

    public void returning(Book book) {
        bookRepositoryRemote.returning(book);
        String title = book.getTitleList().get(0).getValue();
        sendMessage("Book "+title+" returned");
    }

    public void removeBooks() {
        bookRepositoryRemote.removeBookstore();
        sendMessage("Books removed");
    }

}