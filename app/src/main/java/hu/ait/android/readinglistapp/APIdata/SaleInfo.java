package hu.ait.android.readinglistapp.APIdata;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaleInfo {

    @SerializedName("country")
    @Expose
    public String country;
    @SerializedName("saleability")
    @Expose
    public String saleability;
    @SerializedName("isEbook")
    @Expose
    public Boolean isEbook;
    @SerializedName("listPrice")
    @Expose
    public ListPrice listPrice;
    @SerializedName("retailPrice")
    @Expose
    public RetailPrice retailPrice;
    @SerializedName("buyLink")
    @Expose
    public String buyLink;
    @SerializedName("offers")
    @Expose
    public List<Offer> offers = null;

    public String getCountry() {
        return country;
    }

    public String getSaleability() {
        return saleability;
    }

    public Boolean getEbook() {
        return isEbook;
    }

    public ListPrice getListPrice() {
        return listPrice;
    }

    public RetailPrice getRetailPrice() {
        return retailPrice;
    }

    public String getBuyLink() {
        return buyLink;
    }

    public List<Offer> getOffers() {
        return offers;
    }
}