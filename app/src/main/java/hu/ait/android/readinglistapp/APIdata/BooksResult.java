package hu.ait.android.readinglistapp.APIdata;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BooksResult {

    @SerializedName("kind")
    @Expose
    public String kind;
    @SerializedName("totalItems")
    @Expose
    public Integer totalItems;
    @SerializedName("items")
    @Expose
    public List<Item> items = null;

    public String getKind() {
        return kind;
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public List<Item> getItems() {
        return items;
    }
}