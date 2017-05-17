package data;

import model.Book;

import java.util.List;

public interface BookListProducerRemote {
    List<Book> getBooks();
}
