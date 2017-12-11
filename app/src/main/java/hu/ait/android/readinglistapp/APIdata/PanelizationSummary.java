package hu.ait.android.readinglistapp.APIdata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PanelizationSummary {

    @SerializedName("containsEpubBubbles")
    @Expose
    public Boolean containsEpubBubbles;
    @SerializedName("containsImageBubbles")
    @Expose
    public Boolean containsImageBubbles;

    public Boolean getContainsEpubBubbles() {
        return containsEpubBubbles;
    }

    public Boolean getContainsImageBubbles() {
        return containsImageBubbles;
    }
}