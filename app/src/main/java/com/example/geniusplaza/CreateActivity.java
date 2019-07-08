package com.example.geniusplaza;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

import Utils.CallApis;
import Utils.Callbacks;

public class CreateActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView avatar;
    private Button saveButton;
    private TextView firstName;
    private TextView lastName;
    private TextView email;
    CallApis Api;

    private CreateActivity self = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        Callbacks callbacks = new Callbacks() {
            @Override
            public void callbackJsonObjectListView(JSONObject body) throws JSONException {
                AlertDialog.Builder builder = new AlertDialog.Builder(self);
                builder.setMessage(body.toString())
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }

            @Override
            public void callbackError(JSONObject body) {

            }
        };
        Api = new CallApis(callbacks);
        avatar = (ImageView)findViewById(R.id.avatar);
        saveButton = (Button)findViewById(R.id.saveButton);
        firstName = (EditText)findViewById(R.id.FirstName);
        lastName = (EditText)findViewById(R.id.LastName);
        email = (EditText)findViewById(R.id.userEmail);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(firstName.getText() != null && !firstName.getText().toString().isEmpty() && lastName.getText() != null && !lastName.getText().toString().isEmpty() && email.getText() != null && !email.getText().toString().isEmpty()){
                    HashMap<String, String> values =  new HashMap<>();
                    values.put("last_name", lastName.getText().toString());
                    values.put("first_name", firstName.getText().toString());
                    values.put("email", email.getText().toString());

                    Api.post(values);
                }

            }
        });

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                if (pickPhoto.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(pickPhoto, REQUEST_IMAGE_CAPTURE);

                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            avatar.setImageBitmap(imageBitmap);

            final Uri imageUri = data.getData();
            final InputStream imageStream;
            try {
                imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                avatar.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }
}
