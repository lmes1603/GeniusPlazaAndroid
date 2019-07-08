package Utils;

import com.google.gson.JsonObject;


import org.json.JSONException;
import org.json.JSONObject;

//CallBacks

//Callback with one param type JsonObject
public interface Callbacks {
    void callbackJsonObjectListView(JSONObject body) throws JSONException;
    void callbackError(JSONObject body);

}
