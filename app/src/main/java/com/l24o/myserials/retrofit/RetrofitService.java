package com.l24o.myserials.retrofit;

import com.l24o.myserials.models.Serial;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author Alexander Popov created on 01.03.2016.
 */
public interface RetrofitService {

    @GET("/serials")
    Call<List<Serial>> getSerials(@Query("from") int from, @Query("limit") int limit);

}
