package com.assignment;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.assignment.database.DBHelper;
import com.assignment.utils.CommonUtilities;
import com.assignment.utils.Constants;

public class EditActivity extends AppCompatActivity {
    private final String TAG = EditActivity.class.toString();
    private EditText mNameEt, mAgeEt, mAddressEt;
    private Button mUpdateBtn;
    private TextView mIdTv;
    private int id;
    private String name, newName;
    private String age, newAge;
    private String address, newAddress;
    private DBHelper dbHelper;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        // init layout
        setContentView(R.layout.activity_edit);
        dbHelper = CommonUtilities.getDBObject(this); // get database reference
        // init all views
        inItViews();
        // get all values from intent
        getValues(getIntent());
        //set onclick event on update button
        mUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newName = mNameEt.getText().toString();
                newAge = mAgeEt.getText().toString();
                newAddress = mAddressEt.getText().toString();
                if (newName.equalsIgnoreCase("")) {
                    newName = name;
                }
                if (newAge.equalsIgnoreCase("")) {
                    newAge = age;
                }
                if (newAddress.equalsIgnoreCase("")) {
                    newAddress = address;
                }

                ContentValues contentValues = new ContentValues();
                contentValues.put(Constants.EMPLOYEE_NAME, newName);
                contentValues.put(Constants.EMPLOYEE_AGE, newAge);
                contentValues.put(Constants.EMPLOYEE_ADDRESS, newAddress);
                int result = dbHelper.updateRecords(id, contentValues); // save updated records
                if (result != 0) {
                    Toast.makeText(EditActivity.this, "Record Updated", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditActivity.this, "Record Not Updated", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mIdTv.setText("" + id);
        // set old data as hint
        mNameEt.setHint(name);
        mAgeEt.setHint(age);
        mAddressEt.setHint(address);
    }

    // init all views
    private void inItViews() {
        mIdTv = (TextView) findViewById(R.id.ed_text_view_id);
        mNameEt = (EditText) findViewById(R.id.ed_edit_text_name);
        mAgeEt = (EditText) findViewById(R.id.ed_edit_text_age);
        mAddressEt = (EditText) findViewById(R.id.ed_edit_text_address);
        mUpdateBtn = (Button) findViewById(R.id.ed_button_update);
    }

    // get all values from intent
    private void getValues(Intent intent) {
        id = intent.getIntExtra("ID", 0);
        name = intent.getStringExtra("NAME");
        age = intent.getStringExtra("AGE");
        address = intent.getStringExtra("ADDRESS");
        Log.d(TAG, "onCreate: ID :" + id
                + " name = " + name);
    }
}
