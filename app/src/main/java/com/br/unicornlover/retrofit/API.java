package com.br.unicornlover.retrofit;

import com.br.unicornlover.model.Unicorn;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface API {

    @POST("unicorns")
    Call<Unicorn> createUnicorn(@Body Unicorn unicorn);

    @GET("unicorns")
    Call<List<Unicorn>> getUnicorns();

    @GET("unicorns/{id}")
    Call<Unicorn> getUnicorn(@Path("id") String id);

    @PUT("unicorns")
    Call<ResponseBody> editUnicorn(Unicorn unicorn);

    @DELETE("unicorns/{id}")
    Call<ResponseBody> deleteUnicorn(@Path("id") String id);

}