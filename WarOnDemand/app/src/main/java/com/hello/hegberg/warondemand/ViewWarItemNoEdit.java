package com.hello.hegberg.warondemand;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Class for viewing items and not editing them
 */
public class ViewWarItemNoEdit extends AppCompatActivity {
    public static User specificUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_war_item_no_edit);

        TextView itemsName = (TextView) findViewById(R.id.name_info_view_borrower);
        TextView itemsDesc = (TextView) findViewById(R.id.desc_view_borrower);
        TextView biddersName = (TextView) findViewById(R.id.borrower_name_view_borrower);
        TextView minBidPrice = (TextView) findViewById(R.id.min_bid_price_borrowing);

        itemsName.setText(ViewMyItemsActivity.itemClicked.getName());
        itemsDesc.setText(ViewMyItemsActivity.itemClicked.getDesc());
        minBidPrice.setText(String.valueOf(ViewMyItemsActivity.itemClicked.getCost()));

        /**
         * Checks between wether an item is borrowed or not
         */
        try {
            biddersName.setText(ViewMyItemsActivity.itemClicked.getBorrower().getUsername());
        } catch (NullPointerException e) {
            biddersName.setText("No current borrower");
        }

        /**
         * checks if image is present
         */
        ImageView imageView = (ImageView) findViewById(R.id.picView);
        if(ViewMyItemsActivity.itemClicked.getThumbnail() != null){
            imageView.setImageBitmap(ViewMyItemsActivity.itemClicked.getThumbnail());
        }

        /**
         * If bidders name is clicked take user to bidder information
         */
        biddersName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Need to make it so viewspecificuser grabs the specific user from the previous activity.
                specificUser = ViewMyItemsActivity.itemClicked.getBorrower();

                Intent intent = new Intent(ViewWarItemNoEdit.this, ViewSpecificUser.class).putExtra("from", "ViewWarItemNoEdit");
                startActivity(intent);

            }
        });
    }
}
