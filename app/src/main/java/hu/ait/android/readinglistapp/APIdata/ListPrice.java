package hu.ait.android.readinglistapp.APIdata;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListPrice {

    @SerializedName("amount")
    @Expose
    public Double amount;
    @SerializedName("currencyCode")
    @Expose
    public String currencyCode;

    public Double getAmount() {
        return amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }
}