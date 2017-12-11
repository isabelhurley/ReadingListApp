package hu.ait.android.readinglistapp.APIdata;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Offer {

    @SerializedName("finskyOfferType")
    @Expose
    public Integer finskyOfferType;
    @SerializedName("listPrice")
    @Expose
    public ListPrice_ listPrice;
    @SerializedName("retailPrice")
    @Expose
    public RetailPrice_ retailPrice;


    public Integer getFinskyOfferType() {
        return finskyOfferType;
    }

    public ListPrice_ getListPrice() {
        return listPrice;
    }

    public RetailPrice_ getRetailPrice() {
        return retailPrice;
    }
}