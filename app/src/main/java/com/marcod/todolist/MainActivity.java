package com.marcod.todolist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ListView Lv;
    DB DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DB = new DB(this);
        Lv = findViewById(R.id.list_todo);

        updateListView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_task:
                AlertDialog.Builder todoTaskBuilder = new AlertDialog.Builder(this);
                todoTaskBuilder.setTitle("Add a new task");
                todoTaskBuilder.setMessage("What do you want to do next?");
                final EditText todoET = new EditText(this);
                todoTaskBuilder.setView(todoET);
                todoTaskBuilder.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String todoTaskInput = todoET.getText().toString();
                        ContentValues values = new ContentValues();
                        values.put(DB.row_task, todoTaskInput);
                        if(todoTaskInput.equals("")){
                            Toast.makeText(MainActivity.this,"You did not add anything",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else{
                            DB.addData(values);
                            updateListView();
                        }
                    }
                });
                todoTaskBuilder.setNegativeButton("Cancel", null);
                todoTaskBuilder.create().show();
                return true;

            default:
                return false;
        }
    }


    private void updateListView() {
        Cursor cursor = DB.allData();
        Adapter customCursorAdapter = new Adapter(this,cursor,0);
        Lv.setAdapter(customCursorAdapter);
    }

    public void hapusTodo(View view) {
        View v = (View) view.getParent();
        TextView getId = v.findViewById(R.id.task_id);
        final long id = Long.parseLong(getId.getText().toString());

        DB.deleteData(id);
        updateListView();
    }

    public void updateTodo(View view) {
        View v = (View) view.getParent();
        TextView getId = v.findViewById(R.id.task_id);
        TextView getTask =  v.findViewById(R.id.task_update);
        final long id = Long.parseLong(getId.getText().toString());
        String todoTaskItem = getTask.getText().toString();
        AlertDialog.Builder todoTaskBuilder = new AlertDialog.Builder(this);
        todoTaskBuilder.setTitle("Update task");
        todoTaskBuilder.setMessage("What are you going to change?");
        final EditText todoET = new EditText(this);
        todoET.setText(todoTaskItem);
        todoTaskBuilder.setView(todoET);
        todoTaskBuilder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String todoTaskInput = todoET.getText().toString();
                ContentValues values = new ContentValues();
                values.put(DB.row_task, todoTaskInput);
                if(todoTaskInput.equals("")){
                    Toast.makeText(MainActivity.this,"You did not update anything", Toast.LENGTH_SHORT).show();
                }else{
                    DB.updateData(values,id);
                    updateListView();
                }
            }
        });
        todoTaskBuilder.setNegativeButton("Cancel", null);
        todoTaskBuilder.create().show();
    }

}