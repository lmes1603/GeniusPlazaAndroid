package Utils;

import com.google.gson.*;
import java.util.*;
import retrofit2.Call;
import retrofit2.http.*;

public interface REST {
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("{params}")
    Call<JsonObject> listRepos(@Path("params") String params, @QueryMap(encoded=true) Map<String, String> filters);


    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("{params}")
    Call<JsonObject> createUser(@Path("params") String params, @Body HashMap<String, String> rating);

}
