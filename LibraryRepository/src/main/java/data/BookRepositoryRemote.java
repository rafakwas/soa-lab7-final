package data;

import model.Book;
import utils.IOUtils;

import java.util.List;

public interface BookRepositoryRemote {
    List<Book> getAllBooks();
    void rent(Book book);
    void reserve(Book book);
    void returning(Book book);
    void persist(Book book);
    void removeBookstore();
    boolean checkCredentials(String login,String password);
    void addUser(String login, String password, Boolean notifications);
    boolean checkNotifications(String login);
}
