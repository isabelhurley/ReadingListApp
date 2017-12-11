package hu.ait.android.readinglistapp.APIdata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReadingModes {

    @SerializedName("text")
    @Expose
    public Boolean text;
    @SerializedName("image")
    @Expose
    public Boolean image;

    public Boolean getText() {
        return text;
    }

    public Boolean getImage() {
        return image;
    }
}