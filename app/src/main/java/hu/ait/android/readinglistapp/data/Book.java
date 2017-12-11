package hu.ait.android.readinglistapp.data;


public class Book {
    private String title;
    private String author;
    private String desc;

    public Book(String title, String author, String desc) {
        this.title = title;
        this.author = author;
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
