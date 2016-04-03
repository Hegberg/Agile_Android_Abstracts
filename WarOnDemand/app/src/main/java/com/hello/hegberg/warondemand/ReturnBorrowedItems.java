package com.hello.hegberg.warondemand;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ReturnBorrowedItems extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_borrowed_items);

        Button returnItem = (Button) findViewById(R.id.return_item);
        Button getloc = (Button) findViewById(R.id.get_location);
        TextView itemsName = (TextView) findViewById(R.id.borrowed_item_return);
        TextView itemsDesc = (TextView) findViewById(R.id.borrowed_item_desc_return);
        TextView ownersName = (TextView) findViewById(R.id.bidder_name_return);

        itemsName.setText(BorrowingActivity.borrowedItem.getName());
        itemsDesc.setText(BorrowingActivity.borrowedItem.getDesc());
        ownersName.setText(BorrowingActivity.borrowedItem.getOwner().getUsername());


        getloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReturnBorrowedItems.this, MapActivity.class);
                startActivity(intent);



            }
        });

        returnItem.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                WarItem temp = BorrowingActivity.borrowedItem;
                BorrowingActivity.borrowedItem.setStatus(0);
                BorrowingActivity.borrowedItem.setBorrower(null);
                DatabaseController.updateItem(temp, BorrowingActivity.borrowedItem);
                Toast.makeText(ReturnBorrowedItems.this, "Item successfully returned", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}
