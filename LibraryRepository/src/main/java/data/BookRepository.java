package data;

import model.Book;
import model.Pair;
import utils.IOUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import javax.inject.Inject;
import javax.jms.*;
import java.util.*;

@Singleton
@Startup
@Remote(BookRepositoryRemote.class)
public class BookRepository implements BookRepositoryRemote {
    private static final String BOOKSTORE_PATH = "bookstore.xml";

    @Resource(mappedName = "java:/library/NotificationTopic")
    javax.jms.Topic topic;
    @Resource(mappedName = "java:/library/ConfirmationQueue")
    javax.jms.Queue queue;
    @Inject
    JMSContext jmsContext;

    private List<Book> bookList;
    private Map<String,Map.Entry<String,Boolean>> users= new HashMap<>();

    private List<String> notifications = new LinkedList<>();
    private Map<String,List<String>> confirmations = new HashMap<>();

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
    public List<String> getNotifications() {
        return notifications;
    }

    @Override
    public List<String> getConfirmations(String user) {
        return confirmations.get(user);
    }

    @Override
    public void rent(Book book,String user) {
        if(!book.isReserved()) {
            IOUtils.setRent(book,true);
            String text = "Book " + book.getTitleList().get(0).getValue() + " rented";
            ObjectMessage message = jmsContext.createObjectMessage( new AbstractMap.SimpleEntry<String,String>(user,text) );
            jmsContext.createProducer().send(queue,message);
        }
    }

    @Override
    public void reserve(Book book,String user) {
        IOUtils.modifyReservation(book);
        String text = "Book " + book.getTitleList().get(0).getValue() + " reserved";
        ObjectMessage message = jmsContext.createObjectMessage( new AbstractMap.SimpleEntry<String,String>(user,text) );
        jmsContext.createProducer().send(queue,message);
    }

    @Override
    public void returning(Book book,String user) {
        if(book.isRented()) {
            IOUtils.setRent(book,false);
            String text = "Book " + book.getTitleList().get(0).getValue() + " returned";
            ObjectMessage message = jmsContext.createObjectMessage( new AbstractMap.SimpleEntry<String,String>(user,text) );
            jmsContext.createProducer().send(queue,message);
        }
    }

    @Override
    public void persist(Book book)
    {
        IOUtils.addBook(book);
        jmsContext.createProducer().send(topic,"New book added");
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
    public void addNotification(String text) {
        notifications.add(text);
    }

    @Override
    public void addConfirmation(String user, String text) {
        if(!confirmations.containsKey(user)) {
            confirmations.put(user,new ArrayList<>());
        }
        List<String> list = confirmations.get(user);
        list.add(text);
        confirmations.put(user,list);
    }

    @Override
    public boolean checkCredentials(String login, String password) {
        return (users.containsKey(login) && users.get(login).getKey().equals(password)) ? true : false;
    }
}
