package org.code5.code5push.network.apiservice;

/**
 * Created by sony on 8/18/2017.
 */

import org.code5.code5push.network.model.Area;
import org.code5.code5push.network.model.City;
import org.code5.code5push.network.model.Division;
import org.code5.code5push.network.model.RegistrationResponseData;
import org.code5.code5push.network.model.State;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

public interface UserRegistrationService {

    @FormUrlEncoded
    @POST("register")
    Call<RegistrationResponseData> registerInDetail(@Field("name") String name, @Field("email") String email,
                                                    @Field("password") String password, @Field("password_confirmation") String passwordConfirmation
            , @Field("mobile") String mobileNumber, @Field("region") String region, @Field("sector") String sector);

    @FormUrlEncoded
    @POST("register")
    Observable<String> register(@Field("name") String name, @Field("email") String email,
                                @Field("password") String password, @Field("password_confirmation") String passwordConfirmation);
    @GET("state/list")
    Call<List<State>> getStates();

    @FormUrlEncoded
    @GET("city/{state}")
    Call<List<City>> getCities(@Path("state") String stateName);


    @FormUrlEncoded
    @GET("division/{city}")
    Call<List<Division>> getDivision(@Path("city") String cityName);

    @FormUrlEncoded
    @POST("area/division}")
    Call<List<Area>> getAreas(@Path("division") String divisionName);


    @FormUrlEncoded
    @POST("login")
    Observable<String> login(@Field("email") String email, @Field("password") String password);

    @GET("logout/{token}")
    Observable<String> logout(@Path("token") String token);

    @FormUrlEncoded
    @POST("ticket/create")
    Observable<String> createTicket(@Field("subject") String subject, @Field("description") String description);

    @POST("ticket/{id}")
    Observable<String> getTicketDetail(@Path("id") String tokenId);



    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Api.BASE_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();




}