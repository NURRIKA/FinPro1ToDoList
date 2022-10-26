package com.marcod.todolist;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class Adapter  extends CursorAdapter  {

    private final LayoutInflater ly;
    private final SparseBooleanArray mSelectedItems;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)

    public Adapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        ly = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mSelectedItems=new SparseBooleanArray();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View v = ly.inflate(R.layout.todo,viewGroup,false);
        MyHolder holder = new MyHolder();
        holder.idTask = v.findViewById(R.id.task_id);
        holder.taskTodoList =v.findViewById(R.id.task_update);
        v.setTag(holder);

        return v;
    }

    @SuppressLint("Range")
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        MyHolder holder = (MyHolder)view.getTag();

        holder.idTask.setText(cursor.getString(cursor.getColumnIndex(DB.row_idtask)));

        holder.taskTodoList.setText(cursor.getString(cursor.getColumnIndex(DB.row_task)));
    }

    class MyHolder{
        TextView idTask;
        TextView taskTodoList;
    }

}