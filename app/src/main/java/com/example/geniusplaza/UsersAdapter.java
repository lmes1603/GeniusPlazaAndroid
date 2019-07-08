package com.example.geniusplaza;

import android.content.Context;
import android.view.*;

import android.widget.*;

import java.util.ArrayList;

import Utils.User;

public class UsersAdapter extends ArrayAdapter<User> {
    public UsersAdapter(Context context, ArrayList<User> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        User user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_listview, parent, false);
        }
        // Lookup view for data population
        TextView first_name = (TextView) convertView.findViewById(R.id.first_name);
        TextView last_name = (TextView) convertView.findViewById(R.id.last_name);
        TextView email = (TextView) convertView.findViewById(R.id.email);

        // Populate the data into the template view using the data object
        first_name.setText(user.first_name);
        last_name.setText(user.last_name);
        email.setText(user.email);

        // Return the completed view to render on screen
        return convertView;
    }
}