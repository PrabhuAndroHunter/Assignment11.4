package com.assignment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.assignment.adapter.EmpViewAdapter;
import com.assignment.database.DBHelper;
import com.assignment.model.Employee;
import com.assignment.utils.CommonUtilities;
import com.assignment.utils.Constants;
import com.assignment.utils.RecyclerViewItemDecorator;

import java.util.ArrayList;
import java.util.List;

public class ViewActivity extends AppCompatActivity {
    private final String TAG = ViewActivity.class.toString();
    private RecyclerView mRecyclerView;
    private TextView mStatusTv;
    private DBHelper dbHelper;
    private EmpViewAdapter empViewAdapter;
    List <Employee> emp = new ArrayList <Employee>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        // init layout
        setContentView(R.layout.activity_view);
        // init all views
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_View_employee);
        mStatusTv = (TextView) findViewById(R.id.textview_no_result);
        dbHelper = CommonUtilities.getDBObject(this); // get database reference
        // set actionbar tittle
        getSupportActionBar().setTitle("Employee Details");
        empViewAdapter = new EmpViewAdapter(); // create adapter instance
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new RecyclerViewItemDecorator(this, 0));
        mRecyclerView.setAdapter(empViewAdapter); // set adapter to recycler view
        registerForContextMenu(mRecyclerView); // register for Context menu
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Thread(new Runnable() {
            public void run() {
                emp = dbHelper.getEmployeeDetails(); // get Employee details. this has to call within new thread
                updateUi(emp);
            }
        }).start();
    }

    private void updateUi(final List <Employee> emp) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (emp.size() == 0) {
                    mRecyclerView.setVisibility(View.INVISIBLE);
                    Log.d(TAG, "run: ");
                } else { // if records found update ui
                    Log.d(TAG, "total records : " + emp.size());
                    mStatusTv.setVisibility(View.INVISIBLE);
                    empViewAdapter.updateEmpRecords(emp);
                    empViewAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    // This call back will call at the time of creating context menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        Log.d(TAG, "onCreateContextMenu: ");
        getMenuInflater().inflate(R.menu.ed_menu, menu);
    }

    // handle contextmenu item select event
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Employee editEmplolyee = emp.get(empViewAdapter.getPosition());
        if (item.getItemId() == R.id.menu_edit) {
            Intent intent = new Intent(this, EditActivity.class);
            intent.putExtra("ID", editEmplolyee.getId());
            intent.putExtra("NAME", editEmplolyee.getName());
            intent.putExtra("AGE", editEmplolyee.getAge());
            intent.putExtra("ADDRESS", editEmplolyee.getAddress());
            startActivityForResult(intent, 1);                  // start EditActivity
            Log.d(TAG, "onContextItemSelected: " + editEmplolyee.getName());
        } else if (item.getItemId() == R.id.menu_delete) {
            String id = "" + editEmplolyee.getId();
            boolean result = dbHelper.deleteRecords(id);  // delete employee details from datase with employee id
            if (result) {
                Toast.makeText(this, "Record Deleted", Toast.LENGTH_SHORT).show();
                emp = dbHelper.getEmployeeDetails(); // get remaining records
                empViewAdapter.updateEmpRecords(emp); // update records to adapter
                empViewAdapter.notifyDataSetChanged();
            }
        }
        return true;
    }
}
