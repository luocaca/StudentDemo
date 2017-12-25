package luocaca.studentdemo.Reponsity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/10/15 0015.
 */

public interface ServiceAdd {
    @GET("hello/book/add")
    Call<String> requestAdd(@Query("book_id") String book_id,
                            @Query("number") String number,
                            @Query("detail") String detail,
                            @Query("name") String name
    );


}
