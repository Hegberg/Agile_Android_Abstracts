package com.hello.hegberg.warondemand;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.BitmapFactory;
import java.util.ArrayList;
import android.content.res.*;


/**
 * Created by unkno on 2016-03-29.
 * Converts any list of War Items so that the image of the item can be displayed beside the War Item's
 * information.
 */

public class WarItemAdapter extends ArrayAdapter<WarItem>{

    public WarItemAdapter(Context context, ArrayList<WarItem> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        WarItem warItem = getItem(position);
        String message = warItem.toString();
        Bitmap thumbnail;

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        ImageView itemImage = (ImageView) convertView.findViewById(R.id.pictureButton);
        TextView itemText = (TextView) convertView.findViewById(R.id.itemData);
        // Populate the data into the template view using the data object
        itemText.setText(message);
        Resources res = getContext().getResources();

        //I was not sure on how to make a default image, so this is my attempt.
        //Default image is whatever is contained in the src of the xml.
        if(warItem.getThumbnail() != null) {
            thumbnail = warItem.getThumbnail();
        }
        else {
            thumbnail = BitmapFactory.decodeResource(res, R.mipmap.ic_launcher);
        }


        //thumbnail = img.setIsmageResource(R.mipmap.logo1_launcher);
        itemImage.setImageBitmap(thumbnail);

        //}

        // Return the completed view to render on screen
        return convertView;
    }

}
