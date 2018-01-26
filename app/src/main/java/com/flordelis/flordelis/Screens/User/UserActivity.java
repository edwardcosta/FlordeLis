package com.flordelis.flordelis.Screens.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.flordelis.flordelis.Screens.Main.Fragment.ProductListFragment;
import com.flordelis.flordelis.Screens.Main.MainActivity;
import com.flordelis.flordelis.Screens.User.Fragments.UserEditFragment;
import com.flordelis.flordelis.Screens.User.Fragments.UserFragment;
import com.flordelis.flordelis.Utils.StaticValues.UserValues;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Created by Sala on 23/01/2018.
 */

public class UserActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private FirebaseFirestore db;
    private FirebaseUser user;

    private SimpleDraweeView _img_user;
    private SimpleDraweeView _img_back_user;

    private Fragment fragment;
    private FragmentManager fragmentManager;

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
        db = FirebaseFirestore.getInstance();

        _img_user = findViewById(R.id.activity_user_image);
        _img_back_user = findViewById(R.id.activity_user_back_image);

        fragmentManager = getSupportFragmentManager();

        _img_user.setImageURI(user.getPhotoUrl());
        db.collection(UserValues.USER_DATABASE_REFERENCE).document(user.getUid())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    User user = task.getResult().toObject(User.class);
                    if(user.getBackImage() != null && !user.getBackImage().isEmpty()){
                        _img_back_user.setImageURI(user.getBackImage());
                    }
                }
            }
        });

        final FragmentTransaction fragmentTransaction;
        fragmentTransaction = fragmentManager.beginTransaction();
        fragment = fragmentManager.findFragmentById(R.id.activity_user_fragment);

        if (fragment != null) {
            fragmentTransaction.remove(fragment);
        }

        fragment = new UserFragment();
        fragmentTransaction.add(R.id.activity_user_fragment, fragment).commit();

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
    }

    @Override
    public void onBackPressed() {
        if(fragmentManager.findFragmentById(R.id.activity_user_fragment) instanceof UserEditFragment){
            fragmentManager.popBackStack();
        } else {
            super.onBackPressed();
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
                fragmentManager.beginTransaction()
                        .replace(R.id.activity_user_fragment,new UserEditFragment())
                        .addToBackStack(null)
                        .commit();
                return true;
            case android.R.id.home:
                onBackPressed();
               return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
