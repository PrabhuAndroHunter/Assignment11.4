package com.assignment;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.assignment.database.DBHelper;
import com.assignment.utils.CommonUtilities;
import com.assignment.utils.Constants;

public class AddActivity extends AppCompatActivity {
    private final String TAG = AddActivity.class.toString();
    private EditText mNameEt, mAgeEt, mAddressEt;
    private Button mSaveBtn;
    private String name, age, address;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // init layout
        setContentView(R.layout.activity_add);
        Log.d(TAG, "onCreate: ");
        // init all views
        inItViews();
        getSupportActionBar().setTitle("Add Employee");

        // get Database object
        dbHelper = CommonUtilities.getDBObject(this);
        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick save button : ");
                name = mNameEt.getText().toString();
                age = mAgeEt.getText().toString();
                address = mAddressEt.getText().toString();
                validateAndSave(name, age, address);
            }
        });
    }

    private void inItViews() {
        Log.d(TAG, "inItViews: ");
        mNameEt = (EditText) findViewById(R.id.edit_text_name);
        mAgeEt = (EditText) findViewById(R.id.edit_text_age);
        mAddressEt = (EditText) findViewById(R.id.edit_text_address);
        mSaveBtn = (Button) findViewById(R.id.button_save);
    }

    private void validateAndSave(String name, String age, String address) {
        Log.d(TAG, "validateAndSave: ");
        if (name.equalsIgnoreCase("")) {
            mNameEt.setError("Please Enter Name");
        } else if (age.equalsIgnoreCase("")) {
            mAgeEt.setError("Please Enter Age");
        } else if (address.equalsIgnoreCase("")) {
            mAddressEt.setError("Please Enter Address");
        } else {
            final ContentValues contentValues = new ContentValues();
            contentValues.put(Constants.EMPLOYEE_NAME, name);
            contentValues.put(Constants.EMPLOYEE_AGE, age);
            contentValues.put(Constants.EMPLOYEE_ADDRESS, address);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    long result = dbHelper.insertContentVals(Constants.EMPLOYEE_TABLE, contentValues);

                }
            }).start();

            Toast.makeText(this, "Saved Successfully", Toast.LENGTH_SHORT).show();
            mNameEt.setText("");
            mAddressEt.setText("");
            mAgeEt.setText("");
            finish();
        }

    }
}
