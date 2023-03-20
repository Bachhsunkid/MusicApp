package com.example.app_nhac.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.app_nhac.Model.User;
import com.example.app_nhac.R;

public class TaiKhoanCaNhanActivity extends AppCompatActivity {
    TextView txtdoimatkhau, txtdangxuat, txttentaikhoan;
    Toolbar toolbar;
    private User user;
    public  static int statusDangxuat = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tai_khoan_ca_nhan);
        txtdoimatkhau = findViewById(R.id.textviewdoimatkhau);
        txtdangxuat = findViewById(R.id.textviewdangxuat);
        txttentaikhoan = findViewById(R.id.textviewtentaikhoan);
        toolbar = findViewById(R.id.toolbartaikhoancanhan);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setTitle("");
        if (DangNhapActivity.statuslongin == 0) {
            Intent intent = new Intent(TaiKhoanCaNhanActivity.this,DangNhapActivity.class);
            startActivity(intent);
        } else {
            txttentaikhoan.setText(DangNhapActivity.username);
            txtdangxuat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(TaiKhoanCaNhanActivity.this);
                    dialog.setTitle("Đăng xuất tài khoản");
                    dialog.setMessage("Bạn có muốn đăng xuất không?");
                    dialog.setPositiveButton("Đăng xuất", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            statusDangxuat = 1;
                            DangNhapActivity.statuslongin = 0;
                            Toast.makeText(TaiKhoanCaNhanActivity.this,"Đăng xuất thành công!",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(TaiKhoanCaNhanActivity.this, MainActivity .class));
                        }
                    });
                    dialog.setNegativeButton("Bỏ qua", null);
                    dialog.show();

                }
            });
            txtdoimatkhau.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(TaiKhoanCaNhanActivity.this, DoiMatKhauActivity.class);
                    startActivity(intent);

                }
            });
        }
    }
}
