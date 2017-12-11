package hu.ait.android.readinglistapp.APIdata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccessInfo {

    @SerializedName("country")
    @Expose
    public String country;
    @SerializedName("viewability")
    @Expose
    public String viewability;
    @SerializedName("embeddable")
    @Expose
    public Boolean embeddable;
    @SerializedName("publicDomain")
    @Expose
    public Boolean publicDomain;
    @SerializedName("textToSpeechPermission")
    @Expose
    public String textToSpeechPermission;
    @SerializedName("epub")
    @Expose
    public Epub epub;
    @SerializedName("pdf")
    @Expose
    public Pdf pdf;
    @SerializedName("webReaderLink")
    @Expose
    public String webReaderLink;
    @SerializedName("accessViewStatus")
    @Expose
    public String accessViewStatus;
    @SerializedName("quoteSharingAllowed")
    @Expose
    public Boolean quoteSharingAllowed;

    public String getCountry() {
        return country;
    }

    public String getViewability() {
        return viewability;
    }

    public Boolean getEmbeddable() {
        return embeddable;
    }

    public Boolean getPublicDomain() {
        return publicDomain;
    }

    public String getTextToSpeechPermission() {
        return textToSpeechPermission;
    }

    public Epub getEpub() {
        return epub;
    }

    public Pdf getPdf() {
        return pdf;
    }

    public String getWebReaderLink() {
        return webReaderLink;
    }

    public String getAccessViewStatus() {
        return accessViewStatus;
    }

    public Boolean getQuoteSharingAllowed() {
        return quoteSharingAllowed;
    }
}