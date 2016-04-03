package com.hello.hegberg.warondemand;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ReturnBorrowedItems extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_borrowed_items);

        Button returnItem = (Button) findViewById(R.id.return_item);

        TextView itemsName = (TextView) findViewById(R.id.borrowed_item_return);
        TextView itemsDesc = (TextView) findViewById(R.id.borrowed_item_desc_return);
        TextView ownersName = (TextView) findViewById(R.id.bidder_name_return);

        itemsName.setText(BorrowingActivity.borrowedItem.getName());
        itemsDesc.setText(BorrowingActivity.borrowedItem.getDesc());
        ownersName.setText(BorrowingActivity.borrowedItem.getOwner().getUsername());
    }
}
