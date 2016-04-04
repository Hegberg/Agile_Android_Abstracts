package com.hello.hegberg.warondemand;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ViewWarItemNoEdit extends AppCompatActivity {

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
        try {
            biddersName.setText(ViewMyItemsActivity.itemClicked.getBorrower().getUsername());
        } catch (NullPointerException e) {

            biddersName.setText("No current borrower");
        }
    }
}
