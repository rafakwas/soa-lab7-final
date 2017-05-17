package utils;

import model.Book;
import model.Bookstore;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class IOUtils {
    private final static Logger LOGGER = Logger.getLogger(IOUtils.class.toString());


    private static final String BOOKSTORE_PATH = "bookstore.xml";
    private static int counter;

    public static Bookstore getBookstore() {
        Bookstore bookstore = null;
        File file = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Bookstore.class);
            file = new File(BOOKSTORE_PATH);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            bookstore = (Bookstore) jaxbUnmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            e.printStackTrace();
            LOGGER.info(() -> "Błąd przy wczytywaniu biblioteki: " + e.getMessage());
        }
        return bookstore;
    }

    public static Bookstore addBook(Book book) {
        Bookstore bookstore = getBookstore();
        if (bookstore == null) {
            bookstore = new Bookstore();
        }
        if (bookstore.getBookList() == null) {
            bookstore.setBookList(new ArrayList<Book>());
        }
        List<Book> bookList = bookstore.getBookList();
        book.setId(counter++);
        bookList.add(book);
        bookstore.setBookList(bookList);
        write(BOOKSTORE_PATH,bookstore);

        return bookstore;
    }

    public static void modifyReservation(Book book) {
        Bookstore bookstore = getBookstore();
        List<Book> bookList = bookstore.getBookList();
        bookList.remove(book);
        if (book.isReserved()) book.setReserved(false); else book.setReserved(true);
        bookList.add(book);
        bookstore.setBookList(bookList);
        write(BOOKSTORE_PATH,bookstore);
    }


    public static void setRent(Book book,boolean rent) {
        Bookstore bookstore = getBookstore();
        List<Book> bookList = bookstore.getBookList();
        bookList.remove(book);
        book.setRented(rent);
        bookList.add(book);
        bookstore.setBookList(bookList);
        write(BOOKSTORE_PATH,bookstore);
    }

    public static void removeBooks() {
        write(BOOKSTORE_PATH,new Bookstore());
    }


    private static void write(final String BOOKSTORE_PATH,final Bookstore bookstore) {

        JAXBContext jc = null;
        File file = null;
        try {
            jc = JAXBContext.newInstance(Bookstore.class);
            file = new File(BOOKSTORE_PATH);
            Marshaller jaxbMarshaller = jc.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
            jaxbMarshaller.marshal(bookstore,file);
        }
        catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
