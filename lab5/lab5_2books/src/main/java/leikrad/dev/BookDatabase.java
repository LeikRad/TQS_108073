package leikrad.dev;

import java.util.ArrayList;
import java.util.List;

public class BookDatabase {
    
    private List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
    }

    public List<Book> findBooksBetweenYears(int fromYear, int toYear) {
        return null;
    }

    public List<Book> findBooksByAuthor(String author) {
        return null;
    }

    public List<Book> findBooksByTitle(String title) {
        return null;
    }
    
    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }


}
