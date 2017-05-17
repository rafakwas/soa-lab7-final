package data;

import model.Book;
import utils.IOUtils;

import java.util.List;
import java.util.Map;

public interface BookRepositoryRemote {
    List<Book> getAllBooks();
    List<String> getNotifications();
    List<String> getConfirmations(String user);
    void rent(Book book,String user);
    void reserve(Book book,String user);
    void returning(Book book,String user);
    void persist(Book book);
    void removeBookstore();
    boolean checkCredentials(String login,String password);
    void addUser(String login, String password, Boolean notifications);
    boolean checkNotifications(String login);

    void addNotification(String text);
    void addConfirmation(String user, String text);
}
