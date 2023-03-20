package com.example.app_nhac.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.app_nhac.Model.BaiHatOffline;
import com.example.app_nhac.Model.Baihat;
import com.example.app_nhac.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;

public class FacebookActivity extends AppCompatActivity {
    LoginButton loginButton;
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    ShareLinkContent shareLinkContent;
    ShareButton shareButton;
    Toolbar toolbar;
    Baihat baihat;
    BaiHatOffline baiHatOffline;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);
        init();
        GetDataFromIntent();
        //phan hoi dn
        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions("email");
        shareButton.setVisibility(View.INVISIBLE);
        shareDialog = new ShareDialog(FacebookActivity.this);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   LoginManager.getInstance().logInWithReadPermissions(FacebookActivity.this, Arrays.asList("public_profile"));
                if(baihat != null){
                    if (ShareDialog.canShow(ShareLinkContent.class)){
                        shareLinkContent = new ShareLinkContent.Builder()
                                .setContentUrl(Uri.parse(baihat.getLinkbaihat()))
                                .setQuote("Bài hát: "+baihat.getTenbaihat()+"\nCa sĩ: "+baihat.getCasi())
                                .build();
                        shareDialog.show(shareLinkContent);
                    }
                }
                else {
                    if (ShareDialog.canShow(ShareLinkContent.class)){
                        shareLinkContent = new ShareLinkContent.Builder()
                                .setContentUrl(Uri.parse(baiHatOffline.getLinkbaihat()))
                                .setQuote("Bài hát: "+baiHatOffline.getTenbaihat()+"\nCa sĩ: "+baiHatOffline.getCasi())
                                .build();
                        shareDialog.show(shareLinkContent);
                    }
                }
            }
        });
        shareButton.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                loginButton.setVisibility(View.INVISIBLE);
                shareButton.setVisibility(View.VISIBLE);
                shareButton.setEnabled(true);
                if(baiHatOffline == null){
                    toolbar.setTitle(baihat.getTenbaihat());
                }
                else{
                    toolbar.setTitle(baiHatOffline.getTenbaihat());
                }
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }
    private void GetDataFromIntent() {
        Intent intent = getIntent();
        if(intent!=null){
            if(intent.hasExtra("sharecakhuc")){
                baihat = intent.getParcelableExtra("sharecakhuc");
            }
            if(intent.hasExtra("sharecakhucoff")){
                baiHatOffline = intent.getParcelableExtra("sharecakhucoff");
            }
        }
    }


    private void init() {
        loginButton = (LoginButton) findViewById(R.id.login_button);
        shareButton = (ShareButton) findViewById(R.id.share_button);
        toolbar = findViewById(R.id.toolbarshare);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Chia sẻ với Facebook");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //chuyen kq dn
        callbackManager.onActivityResult(requestCode, resultCode, data);

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        //dx sau moi lan tat ud
        LoginManager.getInstance().logOut();
        super.onStart();
    }
}
