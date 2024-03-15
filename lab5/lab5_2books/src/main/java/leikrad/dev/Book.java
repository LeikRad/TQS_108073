package leikrad.dev;

import java.time.LocalDate;

public class Book {

    private String title;
    private String author;
    private LocalDate published;

    public Book(String title, String author, LocalDate published) {
        this.title = title;
        this.author = author;
        this.published = published;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDate getPublished() {
        return published;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublished(LocalDate published) {
        this.published = published;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Book other = (Book) obj;

        if (obj == this) {
            return true;
        }

        if (this.title.equals(other.title) && this.author.equals(other.author) && this.published.equals(other.published)) {
            return true;
        }

        return false;
    }
}
