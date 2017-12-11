package hu.ait.android.readinglistapp.APIdata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IndustryIdentifier {

    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("identifier")
    @Expose
    public String identifier;

    public String getType() {
        return type;
    }

    public String getIdentifier() {
        return identifier;
    }
}