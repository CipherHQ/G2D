package eu.faircode.netguard.g2d.api;

import java.util.List;

import eu.faircode.netguard.g2d.models.Domain;
import eu.faircode.netguard.g2d.models.RequestResponse;
import eu.faircode.netguard.g2d.models.Subscription;
import eu.faircode.netguard.g2d.models.Success;
import eu.faircode.netguard.g2d.models.User;
import eu.faircode.netguard.g2d.models.UserResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface API {

    @POST("/api/login")
    Call<RequestResponse> login(@Body User user);

    @POST("/api/register")
    Call<RequestResponse> register(@Body User user);

    @POST("/api/details")
    Call<UserResponse> details(@Header("Authorization") String auth);


    @GET("/api/setSubscription")
    Call<Subscription> setSubscription(@Header("Authorization") String auth);

    @POST("/api/getSubscription")
    Call<Subscription> getSubscription(@Header("Authorization") String auth);

    @GET("/api/get_domains")
    Call<List<Domain>> getDomains();

    @POST("/api/setUserDomainUpdate")
    Call<UserResponse> setDomainUpdate(@Header("Authorization") String auth);

}
