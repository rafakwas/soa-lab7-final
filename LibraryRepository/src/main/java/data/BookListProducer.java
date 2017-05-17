package data;

import model.Book;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
@Remote(BookListProducerRemote.class)
public class BookListProducer implements BookListProducerRemote {

    @EJB
    private BookRepositoryRemote bookRepository;

    private List<Book> books;

    @PostConstruct
    public void retrieveAllBooksFromBookstore() {
        books = bookRepository.getAllBooks();
    }
    public List<Book> getBooks() {
        return books;
    }
}
