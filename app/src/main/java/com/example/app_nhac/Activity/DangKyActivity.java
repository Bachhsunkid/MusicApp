package com.example.app_nhac.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app_nhac.Database.MyDatabase;
import com.example.app_nhac.R;

public class DangKyActivity extends AppCompatActivity {
    EditText txtusername;
    EditText txtpassword,txtxacnhanpassword;
    Button btndangky;
    TextView txtdangnhap;
    MyDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        init();
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
        txtusername = findViewById(R.id.edittextdangkytaikhoan);
        txtpassword = findViewById(R.id.edittextdangkymatkhau);
        txtxacnhanpassword = findViewById(R.id.edittextxacnhanmatkhau);
        txtdangnhap = findViewById(R.id.textviewdangnhap);
        btndangky = findViewById(R.id.buttondangky);
        db = new MyDatabase(this);
        txtdangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangKyActivity.this,DangNhapActivity.class);
                startActivity(intent);
            }
        });
        btndangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = txtusername.getText().toString().trim();
                String password = txtpassword.getText().toString().trim();
                String cf_password = txtxacnhanpassword.getText().toString().trim();
                if(username.equals("") || password.equals("") || cf_password.equals("")){
                    Toast.makeText(DangKyActivity.this, "Tài khoản và mật khẩu không được để trống", Toast.LENGTH_SHORT).show();
                }
                else {
                    if(!password.equals(cf_password)){
                        Toast.makeText(DangKyActivity.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if(db.checkUsername(username)==false){
                            long check = db.InsertUser(username,password);
                            if( check > 0){
                                Toast.makeText(DangKyActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(DangKyActivity.this,DangNhapActivity.class);
                                intent.putExtra("username",username);
                                intent.putExtra("password",password);
                                setResult(RESULT_OK,intent);
                                finish();
                            }
                            else Toast.makeText(DangKyActivity.this, "Đăng ký không thành công", Toast.LENGTH_SHORT).show();
                        }
                        else Toast.makeText(DangKyActivity.this, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });

    }
}
