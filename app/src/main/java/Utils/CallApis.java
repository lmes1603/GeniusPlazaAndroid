package Utils;


import android.util.Log;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonObject;
import org.json.*;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import retrofit2.*;
import retrofit2.converter.gson.*;


public class CallApis {

    //Var Declarations
    private Callbacks callback;
    private String url, params;
    private OkHttpClient client=new OkHttpClient();


    //Constructs
    public CallApis(){
        client = new OkHttpClient.Builder().build();
    }

    public CallApis(String url, String params, Callbacks callback) {
        this.callback = callback;
        this.url = url;
        this.params = params;
    }


    //Element Functions

    public CallApis(Callbacks callback) {
        this.callback = callback;
    }



    //Utility Functions
    public void get(final String page){


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://reqres.in/api/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        REST service = retrofit.create(REST.class);

        // TODO Change Key hardcode To Key From Json
        params="users";
        Log.d("URL", "https://reqres.in/api/"+params+"/page="+page);
        ImmutableMap<String, String> types = ImmutableMap.of("page", page);
        Call<JsonObject> call = service.listRepos(params, types);
        try {
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, final Response<JsonObject> response) {
                    try {
                        callback.callbackJsonObjectListView(new JSONObject(response.body().toString()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                }
            });
        }
        catch (Exception exception){
        }


    }

    public void post(HashMap<String, String> values){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://reqres.in/api/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final REST service = retrofit.create(REST.class);
        // TODO Change Key hardcode To Key From Json
        params = "users";
        Call<JsonObject> call = service.createUser(params, values);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, final Response<JsonObject> response) {

                try {
                    callback.callbackJsonObjectListView(new JSONObject(response.body().toString()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.callbackError(null);

            }
        });
    }

}