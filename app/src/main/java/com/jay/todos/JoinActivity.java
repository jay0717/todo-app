
package com.jay.todos;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class JoinActivity extends AppCompatActivity {

    EditText editTexjoinid , editTextjoinpw, editTextjoinpw2;
    Button buttonjoinjoin ;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);


        editTexjoinid=(EditText) findViewById(R.id.editTextjoinid);
        editTextjoinpw=(EditText) findViewById(R.id.editTextjoinpw);
        editTextjoinpw2=(EditText) findViewById(R.id.editTextpw2);
        buttonjoinjoin=(Button) findViewById(R.id.buttonjoinjoin);
        sharedPreferences = this.getSharedPreferences("com.jay.todos", Context.MODE_PRIVATE);


        buttonjoinjoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValidInput()) {
                    SharedPreferences.Editor editor= sharedPreferences.edit();

                    String id = editTexjoinid.getText().toString();
                    String pw = editTextjoinpw.getText().toString();

                    editor.putString(id, pw);

                    editor.commit();

                    showToast("회원가입완료");


                    finish();
                }
            }
        });


    }

    void showToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    boolean isValidInput() {

        boolean isIdEdited = editTexjoinid.getText().toString().length() > 0;
        boolean isPwEdited = editTextjoinpw.getText().toString().length() > 0;
        boolean isPw2Edited = editTextjoinpw2.getText().toString().length() > 0;
        if (!isIdEdited || !isPwEdited || !isPw2Edited) {
            showToast("필수 입력 항목을 전부 입력해주세요");
            return false;
        }

        String pw= editTextjoinpw.getText().toString();
        String pw2= editTextjoinpw2.getText().toString();

        if(!pw.equals(pw2)) {
            showToast ("비밀번호가 서로 다릅니다");
            return false;
        }

        return true;
    }
}
