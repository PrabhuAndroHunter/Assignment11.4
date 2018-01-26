package com.assignment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = MainActivity.class.toString();
    private Button mViewBtn, mAddBtn;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // init layout
        setContentView(R.layout.activity_main);
        // init all Views
        inItViews();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    // init all views
    private void inItViews() {
        Log.d(TAG, "inItViews: ");
        mViewBtn = (Button) findViewById(R.id.button_view);
        mAddBtn = (Button) findViewById(R.id.button_add);
        mViewBtn.setOnClickListener(this);
        mAddBtn.setOnClickListener(this);
    }

    // handle all button click events
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_view:  // start view activity
                intent = new Intent(this, ViewActivity.class);
                startActivity(intent);
                break;
            case R.id.button_add:  // start add activity
                intent = new Intent(this, AddActivity.class);
                startActivity(intent);
                break;
        }

    }
}
