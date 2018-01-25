package com.flordelis.flordelis.Screens.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.firebase.ui.auth.AuthUI;
import com.flordelis.flordelis.Model.User;
import com.flordelis.flordelis.R;
import com.flordelis.flordelis.Screens.Authentication.LoginActivity;
import com.flordelis.flordelis.Screens.Main.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Sala on 23/01/2018.
 */

public class UserActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private FirebaseUser user;
    private SimpleDraweeView _img_user;
    private TextView _name;
    private TextView _email;
    private TextView _phone;
    private TextView _cellphone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        toolbar = (Toolbar) findViewById(R.id.activity_user_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        findViewById(R.id.activity_user_appbar).bringToFront();

        user = FirebaseAuth.getInstance().getCurrentUser();

        _img_user = findViewById(R.id.actitivy_user_image);
        _name = findViewById(R.id.activity_user_name);
        _email = findViewById(R.id.activity_user_email);
        _phone = findViewById(R.id.activity_user_phone);

        _img_user.setImageURI(user.getPhotoUrl());

        postponeEnterTransition();
        _img_user.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        _img_user.getViewTreeObserver().removeOnPreDrawListener(this);
                        startPostponedEnterTransition();
                        return true;
                    }
                }
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(user != null){
            //_img_user_back.setImageURI(user.getBackImage());
            _name.setText(user.getDisplayName());
            _email.setText(user.getEmail());
            _phone.setText(user.getPhoneNumber());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_logout:
                AuthUI.getInstance()
                        .signOut(UserActivity.this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                Intent intent = new Intent(UserActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                return true;
            case R.id.action_edit_user:
                Toast.makeText(this, "Editar Perfil clicado", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
