package com.example.sm1999.aimtodo;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sm1999.aimtodo.Utils.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class StudiesActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    Button addNewData;
    EditText newTodo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studies);

        getSupportActionBar().setTitle("Studies");

        databaseHelper = new DatabaseHelper(this);

        final ArrayList<String> tasksArray = new ArrayList<>();
        Cursor data = databaseHelper.showData("STUDIES");

        while(data.moveToNext()) {
            if(!data.getString(0).equals(""))
                tasksArray.add(data.getString(0));
        }

        final ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, tasksArray);
        final ListView listView = (ListView) findViewById(R.id.family_list);
        listView.setAdapter(adapter);

        addNewData = findViewById(R.id.saveTodo);
        newTodo = findViewById(R.id.newStudiestodo);
        addNewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                String formattedDate = df.format(c);
                String studytodo = newTodo.getText().toString();
                boolean insertData = databaseHelper.addData("","","","","","",studytodo+" "+formattedDate,"");

                if(insertData){
                    Toast.makeText(StudiesActivity.this, "To-do Successfully stored!", Toast.LENGTH_SHORT).show();
                    newTodo.setText("");
                    tasksArray.clear();
                    Cursor data = databaseHelper.showData("STUDIES");
                    while(data.moveToNext()) {
                        if(!data.getString(0).equals(""))
                            tasksArray.add(data.getString(0));
                    }
                    adapter.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(StudiesActivity.this, "Error in storing data!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
