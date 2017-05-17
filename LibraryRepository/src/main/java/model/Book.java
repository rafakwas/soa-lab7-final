package model;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;


@XmlRootElement(name = "book")
@XmlType(propOrder = { "id","authorList", "titleList", "isbn", "reserved", "rented"})

public class Book implements Serializable {
    int id;
    List<String> authorList;
    List<Pair> titleList;
    String isbn;
    boolean isReserved;
    boolean isRented;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @XmlElement(name = "author")
    public List<String> getAuthorList() {
        return authorList;
    }

    public void setAuthorList(List<String> authorList) {
        this.authorList = authorList;
    }

    @XmlElement(name = "title")
    public List<Pair> getTitleList() {
        return titleList;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setTitleList(List<Pair> titleList) {
        this.titleList = titleList;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @XmlElement(name = "reserved")
    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }

    public boolean isRented() {
        return isRented;
    }

    public void setRented(boolean rented) {
        isRented = rented;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", authorList=" + authorList +
                ", titleList=" + titleList +
                ", isbn='" + isbn + '\'' +
                ", isReserved=" + isReserved +
                ", isRented=" + isRented +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        return id == book.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
