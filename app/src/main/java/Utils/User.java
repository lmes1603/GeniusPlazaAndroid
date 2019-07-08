package Utils;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class User {
    public int id;
    public String first_name;
    public String last_name;
    public String email;
    public String avalar_url;
    public Bitmap avatar;

    public User(int id, String first_name, String last_name, String email, String avatar) {

        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.avalar_url = avatar;


    }

    public User(JSONObject object){
        try {
            this.id = object.getInt("id");
            this.first_name = object.getString("first_name");
            this.last_name = object.getString("last_name");
            this.email = object.getString("email");
            AsyncTask<String, Void, Bitmap> avatar = new DownloadImageTask(this.avatar);
            avatar.execute(object.getString("avatar")).get();
            this.avatar = ((DownloadImageTask) avatar).bmImage;
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    // Factory method to convert an array of JSON objects into a list of objects
    // User.fromJson(jsonArray);
    public static ArrayList<User> fromJson(JSONArray jsonObjects) {
        ArrayList<User> users = new ArrayList<User>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                users.add(new User(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return users;
    }


}
