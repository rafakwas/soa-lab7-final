package data;

import model.Book;
import model.Pair;
import utils.IOUtils;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import java.util.*;

@Singleton
@Startup
@Remote(BookRepositoryRemote.class)
public class BookRepository implements BookRepositoryRemote {
    private static final String BOOKSTORE_PATH = "bookstore.xml";

    private List<Book> bookList;
    private Map<String,Map.Entry<String,Boolean>> users= new HashMap<>();

    @PostConstruct
    public void initializeUsers() {
        users.put("a",new AbstractMap.SimpleImmutableEntry<String, Boolean>("a",Boolean.TRUE));
        users.put("b",new AbstractMap.SimpleImmutableEntry<String, Boolean>("b",Boolean.FALSE));
        users.put("c",new AbstractMap.SimpleImmutableEntry<String, Boolean>("c",Boolean.TRUE));

    }

    public List<Book> getAllBooks() {
        return IOUtils.getBookstore().getBookList();
    }

    @Lock
    public List<Book> getBookList() {
        return getAllBooks();
    }

    @Override
    public void rent(Book book) {
        if(!book.isReserved()) {
            IOUtils.setRent(book,true);
        }
    }

    @Override
    public void reserve(Book book) {
        IOUtils.modifyReservation(book);
    }

    @Override
    public void returning(Book book) {
        if(book.isRented()) {
            IOUtils.setRent(book,false);
        }
    }

    @Override
    public void persist(Book book) {
        IOUtils.addBook(book);
    }

    @Override
    public void removeBookstore() {
        IOUtils.removeBooks();
    }

    @Override
    public void addUser(String login,String password, Boolean notifications) {
        users.put(login,new AbstractMap.SimpleImmutableEntry<String, Boolean>(password,notifications));
    }

    @Override
    public boolean checkNotifications(String login) {
        return users.get(login).getValue();
    }

    @Override
    public boolean checkCredentials(String login, String password) {
        return (users.containsKey(login) && users.get(login).getKey().equals(password)) ? true : false;
    }
}
