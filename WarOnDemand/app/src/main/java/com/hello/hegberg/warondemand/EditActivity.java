package com.hello.hegberg.warondemand;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {
    private ArrayList<String> contactInfoHolder;
    private User tempUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        TextView nameInfo = (TextView) findViewById(R.id.editNameText);
        final TextView descriptionInfo = (TextView) findViewById(R.id.descriptionUserEdit);
        final TextView contactInfo = (TextView) findViewById(R.id.contactInfoUserEdit);

        Button done = (Button) findViewById(R.id.doneEditAccount);

        nameInfo.setText(MainActivity.chosenUser.getUsername());
        contactInfoHolder = MainActivity.chosenUser.getContactInfo();
        descriptionInfo.setText(contactInfoHolder.get(0));
        contactInfo.setText(contactInfoHolder.get(1));

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = descriptionInfo.getText().toString();
                String contact = contactInfo.getText().toString();

                if (description.equals("")) {
                    Toast.makeText(EditActivity.this, "You need to enter a description", Toast.LENGTH_SHORT).show();
                } else if (contact.equals("")) {
                    Toast.makeText(EditActivity.this, "You need to enter your contact information", Toast.LENGTH_SHORT).show();
                } else {
                    tempUser = MainActivity.chosenUser;
                    MainActivity.chosenUser.editUser(MainActivity.chosenUser.getUsername(), description, contact);
                    DatabaseController controller = new DatabaseController();
                    controller.updateUser(tempUser, MainActivity.chosenUser);
                    finish();
                    startActivity(new Intent(EditActivity.this, AccountController.class));
                }
            }
        });
    }
}
