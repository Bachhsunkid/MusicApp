package com.example.app_nhac.Activity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app_nhac.Database.MyDatabase;
import com.example.app_nhac.R;

public class DoiMatKhauActivity extends AppCompatActivity{
    EditText txtpassword,txtnewpassword,txtxacnhannewpassword;
    Button btnxacnhan,btnhuy;
    MyDatabase db;
    public static String username="";
    public static String password="";
    public static String newpassword="";
    public static String cf_password="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_mat_khau);
        db=new MyDatabase(this);
        init();
        event();
    }

    private void event() {
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = DangNhapActivity.username;
                password = txtpassword.getText().toString().trim();
                newpassword = txtnewpassword.getText().toString().trim();
                cf_password = txtxacnhannewpassword.getText().toString().trim();

                if (password.equals("") || newpassword.equals("") || cf_password.equals("")) {
                    Toast.makeText(DoiMatKhauActivity.this, "Mật khẩu không được để trống", Toast.LENGTH_SHORT).show();
                } else {
                    Cursor cursor = db.getAllUser();
                    for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()) {
                        if (cursor.getString(cursor.getColumnIndex(MyDatabase.getUSERNAME())).equals(username) && cursor.getString(cursor.getColumnIndex(MyDatabase.getPASSWORD())).equals(password)) {
                            if (!newpassword.equals(cf_password))
                                Toast.makeText(DoiMatKhauActivity.this, "Mật khẩu xác nhận không khớp", Toast.LENGTH_SHORT).show();
                            else {
                                Log.d("user", username + "-" + password);
                                long resultAdd = db.UpdateUser(username, newpassword);
                                if (resultAdd == 0) {
                                    Toast.makeText(DoiMatKhauActivity.this, "Đổi mật khẩu không thành công", Toast.LENGTH_SHORT).show();
                                } else if (resultAdd == 1) {
                                    Toast.makeText(DoiMatKhauActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
//                                    startActivity(new Intent(DoiMatKhauActivity.this, TaiKhoanCaNhanActivity.class));
                                    finish();
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    private void init() {
        txtpassword = findViewById(R.id.edittextmatkhauhientai);
        txtnewpassword = findViewById(R.id.edittextmatkhaumoi);
        txtxacnhannewpassword = findViewById(R.id.edittextxacnhanmatkhau);
        btnxacnhan = findViewById(R.id.buttonxacnhan);
        btnhuy = findViewById(R.id.buttonhuy);
    }
}
