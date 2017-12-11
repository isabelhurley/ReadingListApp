package hu.ait.android.readinglistapp.APIdata;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListPrice_ {

    @SerializedName("amountInMicros")
    @Expose
    public Double amountInMicros;
    @SerializedName("currencyCode")
    @Expose
    public String currencyCode;

    public Double getAmountInMicros() {
        return amountInMicros;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }
}