package hu.ait.android.readinglistapp.data;


public class Booklist {

    private String listName;

    public Booklist(String listName) {
        this.listName = listName;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }
}
