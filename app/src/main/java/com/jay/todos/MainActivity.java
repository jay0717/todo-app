package com.jay.todos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText editTextid, editTextpw;
    Button buttonlogin, buttonjoin;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextid = (EditText) findViewById(R.id.editTextid);
        editTextpw = (EditText) findViewById(R.id.editTextpw);

        buttonjoin = (Button) findViewById(R.id.buttonjoin);
        buttonlogin = (Button) findViewById(R.id.buttonlogin);

        sharedPreferences = this.getSharedPreferences("com.jay.todos", Context.MODE_PRIVATE);

        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = editTextid.getText().toString();
                String pw = editTextpw.getText().toString();
                String savedPw = sharedPreferences.getString(id, "");
                if (savedPw.equals("")) {
                    // 로그인에 사용한 id가 회원가입이 되어있지 않을 때
                    Toast.makeText(MainActivity.this, "회원가입된 아이디가 아닙니다.", Toast.LENGTH_SHORT).show();
                } else if (!savedPw.equals(pw)) {
                    // 회원가입은 되어 있지만, 비밀번호가 다를 때
                    Toast.makeText(MainActivity.this, "비밀번호가 다릅니다.", Toast.LENGTH_SHORT).show();
                } else {
                    // 로그인 성공
                    Toast.makeText(MainActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();

                    // TODO: 투두리스트 화면으로 넘어간다.
                    Intent toTOdoListIntent = new Intent(MainActivity.this, TodoListActivity.class);
                    toTOdoListIntent.putExtra("id",id);
                    startActivity(toTOdoListIntent);

                }
            }
        });

        buttonjoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, JoinActivity.class);
                startActivity(intent);
            }
        });
    }

}