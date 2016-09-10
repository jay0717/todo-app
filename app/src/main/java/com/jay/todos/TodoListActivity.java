package com.jay.todos;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class TodoListActivity extends AppCompatActivity {

    TextView textViewUsername;
    ListView listViewTodos;
    Button buttonAddTodos;

    ArrayList<String> todoList = new ArrayList<>();
    ArrayAdapter<String> adapterTodoList;

    SharedPreferences sharedPreferences;

    String userId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);


        sharedPreferences = this.getSharedPreferences("com.jay.todos", Context.MODE_PRIVATE);


        textViewUsername = (TextView) findViewById(R.id.textViewUsername);
        listViewTodos = (ListView) findViewById(R.id.listViewTodos);
        buttonAddTodos = (Button) findViewById(R.id.buttonAddTodo);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            userId = bundle.getString("id");
            textViewUsername.setText(userId);
        }


        adapterTodoList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoList);

        listViewTodos.setAdapter(adapterTodoList);

        buttonAddTodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(TodoListActivity.this);
                builder.setTitle("할 일을 추가해주세요");


                final EditText editTextAddTodo = new EditText(TodoListActivity.this);
                builder.setView(editTextAddTodo);


                builder.setPositiveButton("추가", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String todo = editTextAddTodo.getText().toString();
                        addTodo(todo);
                    }
                });

                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        listViewTodos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(position);
            }
        });

        listViewTodos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                System.out.println("LOng Clicked" + position);

                final AlertDialog.Builder builder = new AlertDialog.Builder(TodoListActivity.this);
                builder.setTitle("삭제");

                builder.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeTodo(position);
                    }
                });

                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });


                builder.show();

                return true;


            }
        });

        loadTodos();
    }


    void addTodo(String todo) {
        todoList.add(todo);
        adapterTodoList.notifyDataSetChanged();

    }

    void removeTodo(int p) {
        todoList.remove(p);
        adapterTodoList.notifyDataSetChanged();

    }

    void savetodos() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String todosString = "";
        for (String todo : todoList) {
            todosString += todo + "&&&";

        }

        System.out.println("saved:" + todosString);

        editor.putString("todos"+userId, todosString);
        editor.commit();
    }


    void loadTodos() {

        String saveTodoString = sharedPreferences.getString("todos"+userId, "");

        if (!saveTodoString.equals("")) {

            for (String todo : saveTodoString.split("&&&")) {
                todoList.add(todo);
            }

            adapterTodoList.notifyDataSetChanged();
        }


    }

    @Override
    protected void onStop() {
        super.onStop();
        savetodos();
    }
}







