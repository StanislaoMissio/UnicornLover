package com.br.unicornlover.retrofit;

import com.br.unicornlover.model.Unicorn;

import java.util.List;

import io.reactivex.Observable;
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
    Observable<Unicorn> createUnicorn(@Body Unicorn unicorn);

    @GET("unicorns")
    Observable<List<Unicorn>> getUnicorns();

    @GET("unicorns/{id}")
    Observable<Unicorn> getUnicorn(@Path("id") String id);

    @PUT("unicorns/{id}")
    Observable<ResponseBody> editUnicorn(@Path("id")String id, @Body Unicorn unicorn);

    @DELETE("unicorns/{id}")
    Observable<ResponseBody> deleteUnicorn(@Path("id") String id);

}