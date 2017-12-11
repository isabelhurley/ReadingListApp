package hu.ait.android.readinglistapp.network;

import hu.ait.android.readinglistapp.APIdata.BooksResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BooksAPI {

    @GET("volumes")
    Call<BooksResult> getBookByID(@Query("id") String book_id, @Query("appid") String app_id);

    @GET("volumes")
    Call<BooksResult> getBookByName(@Query("q") String book_name, @Query("appid") String app_id);

}
