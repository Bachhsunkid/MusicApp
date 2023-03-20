package com.example.app_nhac.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app_nhac.Database.MyDatabase;
import com.example.app_nhac.R;

public class DangNhapActivity extends AppCompatActivity {
    EditText txtusername;
    EditText txtpassword;
    Button btndangnhap;
    TextView txtdangky;
    MyDatabase db;
    public  static int statuslongin = 0;
    static public  int CALCULATE_MARK_REQUEST = 100;
    public  static String username="";
    public  static  String password="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        init();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if ((requestCode == CALCULATE_MARK_REQUEST) && (resultCode == RESULT_OK)) {
            txtusername.setText(
                    data.getExtras().getString("username"));
            txtpassword.setText(data.getExtras().getString("password"));
        }
    }

    protected  void onStart() {
        super.onStart();
        db.openDB();
    }
    protected  void onStop() {
        super.onStop();
        db.closeDB();
    }

    private void init() {
        txtusername = findViewById(R.id.edittexttaikhoan);
        txtpassword = findViewById(R.id.edittextmatkhau);
        txtdangky = findViewById(R.id.textviewdangky);
        btndangnhap = findViewById(R.id.buttondangnhap);
        db = new MyDatabase(this);
        txtdangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangNhapActivity.this,DangKyActivity.class);
                startActivity(intent);
            }
        });
        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = txtusername.getText().toString().trim();
                password = txtpassword.getText().toString().trim();
                if (username.equals("") || password.equals("") ) {
                    Toast.makeText(DangNhapActivity.this, "Tài khoản và mật khẩu không được để trống!", Toast.LENGTH_SHORT).show();
                } else {
                    if (db.checkUser(username, password) == true) {
                        statuslongin = 1;
                        Toast.makeText(DangNhapActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DangNhapActivity.this, MainActivity.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(DangNhapActivity.this, "Tài khoản, mật khẩu không đúng!", Toast.LENGTH_SHORT).show();
                        statuslongin = 0;
                    }
                }
            }
        });
    }
}
