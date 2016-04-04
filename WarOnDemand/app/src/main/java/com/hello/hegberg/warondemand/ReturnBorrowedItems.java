package com.hello.hegberg.warondemand;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This class allows the user to return borrowed items
 * and also get the location of items to pick up.
 */
public class ReturnBorrowedItems extends AppCompatActivity {

    public static User specificUser;

    /**
     * onCreate ReturnBorrowedItems
     * This allows the borrower to return an item
     * Also user can get the location of this product if not picked up
     * @param savedInstanceState
     */
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

        ImageView imageView = (ImageView) findViewById(R.id.picView);
        if(BorrowingActivity.borrowedItem.getThumbnail() != null){
            imageView.setImageBitmap(BorrowingActivity.borrowedItem.getThumbnail());
        }

        getloc.setOnClickListener(new View.OnClickListener() {
            /**
             * After a item has been borrowed the user must pick it up
             * The location of the item is recorded, and this button allows the
             * user to use google maps to get directions to the item
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReturnBorrowedItems.this, MapActivity.class);
                startActivity(intent);
            }
        });

        returnItem.setOnClickListener(new View.OnClickListener() {
            /**
             * onClick returnItem
             * The item is returned to the owner.
             * removed from borrowers possession
             * @param v
             */
            public void onClick(View v) {
                WarItem temp = BorrowingActivity.borrowedItem;
                BorrowingActivity.borrowedItem.setStatus(0);
                BorrowingActivity.borrowedItem.setBorrower(null);
                DatabaseController.updateItem(temp, BorrowingActivity.borrowedItem);
                Toast.makeText(ReturnBorrowedItems.this, "Item successfully returned", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        ownersName.setOnClickListener(new View.OnClickListener() {
            /**
             * onClick ownersName
             * This allows the borrower to get contact information about the seller
             * @param v
             */
            @Override
            public void onClick(View v) {
                //TODO Need to make it so viewspecificuser grabs the specific user from the previous activity.
                specificUser = BorrowingActivity.borrowedItem.getOwner();

                Intent intent = new Intent(ReturnBorrowedItems.this, ViewSpecificUser.class).putExtra("from","ReturnBorrowedItems");
                startActivity(intent);

            }
        });

    }
}
