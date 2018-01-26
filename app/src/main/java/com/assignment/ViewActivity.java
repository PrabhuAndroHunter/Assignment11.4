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
        setContentView(R.layout.activity_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_View_employee);
        mStatusTv = (TextView) findViewById(R.id.textview_no_result);
        dbHelper = CommonUtilities.getDBObject(this);
        getSupportActionBar().setTitle("Employee Details");
        empViewAdapter = new EmpViewAdapter();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new RecyclerViewItemDecorator(this, 0));
        mRecyclerView.setAdapter(empViewAdapter);
        registerForContextMenu(mRecyclerView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Thread(new Runnable() {
            public void run() {
                emp = dbHelper.getEmployeeDetails(); // Creating object
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
                } else {
                    Log.d(TAG, "total records : " + emp.size());
                    mStatusTv.setVisibility(View.INVISIBLE);
                    empViewAdapter.updateEmpRecords(emp);
                    empViewAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        Log.d(TAG, "onCreateContextMenu: ");
        getMenuInflater().inflate(R.menu.ed_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Employee editEmplolyee = emp.get(empViewAdapter.getPosition());
        if (item.getItemId() == R.id.menu_edit) {
            Intent intent = new Intent(this, EditActivity.class);
            intent.putExtra("ID", editEmplolyee.getId());
            intent.putExtra("NAME", editEmplolyee.getName());
            intent.putExtra("AGE", editEmplolyee.getAge());
            intent.putExtra("ADDRESS", editEmplolyee.getAddress());
            startActivityForResult(intent, 1);
            Log.d(TAG, "onContextItemSelected: " + editEmplolyee.getName());
        } else if (item.getItemId() == R.id.menu_delete) {
            String id = "" + editEmplolyee.getId();
            boolean result = dbHelper.deleteRecords(id);
            if (result) {
                Toast.makeText(this, "Record Deleted", Toast.LENGTH_SHORT).show();
                emp = dbHelper.getEmployeeDetails();
                empViewAdapter.updateEmpRecords(emp);
                empViewAdapter.notifyDataSetChanged();
            }
        }
        return true;
    }
}
