package leikrad.dev;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookDatabase {
    
    private List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
    }

    public List<Book> findBooksBetweenYears(LocalDate fromYear, LocalDate toYear) {
        List<Book> foundBooks = new ArrayList<>();

        for (Book book : books) {
            System.out.println(book.getPublished() + " " + fromYear + " " + toYear);
            System.out.println(book.getPublished().isAfter(fromYear) + " " + book.getPublished().isBefore(toYear));
            if (book.getPublished().isAfter(fromYear) && book.getPublished().isBefore(toYear)) foundBooks.add(book);
            
        }

        return foundBooks;
    }

    public List<Book> findBooksByAuthor(String author) {
        List<Book> foundBooks = new ArrayList<>();

        for (Book book : books) {
            if (book.getAuthor().equals(author)) foundBooks.add(book);
        }

        return foundBooks;
    }

    public List<Book> findBooksByTitle(String title) {
        List<Book> foundBooks = new ArrayList<>();

        for (Book book : books) {
            if (book.getTitle().equals(title)) foundBooks.add(book);
        }

        return foundBooks;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }


}
