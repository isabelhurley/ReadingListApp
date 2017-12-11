package hu.ait.android.readinglistapp.APIdata;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchInfo {

    @SerializedName("textSnippet")
    @Expose
    public String textSnippet;

    public String getTextSnippet() {
        return textSnippet;
    }
}