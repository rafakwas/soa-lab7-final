package controller;

import model.Book;

import javax.ejb.Remote;
import java.util.List;

public interface BookControllerInterface {
    public void log();
    public Book getBook();
    public void setBook(Book book);
    public void addBook();
    public List<Book> getBooks();
    public List<String> getNotifications();
    List<String> getConfirmations(String user);
    public void reserveBook(Book book,String user);
    public void rentBook(Book book,String user);
    public void returning(Book book, String user);
    public void removeBooks();
}