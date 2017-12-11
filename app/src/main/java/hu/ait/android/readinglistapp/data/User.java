package hu.ait.android.readinglistapp.data;

import java.util.List;


public class User {

    private String displayName;
    private String email;
    private List<Booklist> booklists;
    private String userId;

    public User(String userId, String displayName, String email) {
        this.userId = userId;
        this.displayName = displayName;
        this.email = email;
    }

    public User() {}

    public List<Booklist> getBooklists() {
        return booklists;
    }

    public void setBooklists(List<Booklist> booklists) {
        this.booklists = booklists;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
