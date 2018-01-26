package com.assignment.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.assignment.R;
import com.assignment.model.Employee;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prabhu on 25/1/18.
 */

public class EmpViewAdapter extends RecyclerView.Adapter<EmpViewAdapter.MyViewAdapter> {
    private final String TAG = EmpViewAdapter.class.toString();
    private List<Employee> empRecords = new ArrayList <Employee>();
    @Override
    public MyViewAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_employee, parent, false);
        return new MyViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(final MyViewAdapter holder, int position) {
        holder.mNameTv.setText(empRecords.get(position).getName());
        holder.mAgeTv.setText(empRecords.get(position).getAge());
        holder.mAddressTv.setText(empRecords.get(position).getAddress());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosition(holder.getPosition());
                return false;
            }
        });
    }

    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: "+ empRecords.size());
        return empRecords.size();
    }

    class MyViewAdapter extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        private TextView mNameTv, mAgeTv, mAddressTv;
        public MyViewAdapter(View itemView) {
            super(itemView);
            mNameTv = (TextView) itemView.findViewById(R.id.text_view_item_name);
            mAgeTv= (TextView) itemView.findViewById(R.id.text_view_item_age);
            mAddressTv = (TextView) itemView.findViewById(R.id.text_view_item_address);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(Menu.NONE, 1,
                    Menu.NONE, "Edit");
            menu.add(Menu.NONE, 2,
                    Menu.NONE, "Delete");
        }
    }

    public void updateEmpRecords(List<Employee> empRecords){
        this.empRecords = empRecords;
    }
}
