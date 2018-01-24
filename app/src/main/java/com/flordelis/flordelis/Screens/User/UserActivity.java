package com.flordelis.flordelis.Screens.User;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.flordelis.flordelis.Model.User;
import com.flordelis.flordelis.R;

/**
 * Created by Sala on 23/01/2018.
 */

public class UserActivity extends AppCompatActivity {

    private User user;

    private SimpleDraweeView _img_user;
    private SimpleDraweeView _img_user_back;
    private TextView _name;
    private TextView _email;
    private TextView _phone;
    private TextView _cellphone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        _img_user = findViewById(R.id.actitivy_user_image);
        _img_user_back = findViewById(R.id.activity_user_back_image);
        _name = findViewById(R.id.activity_user_name);
        _email = findViewById(R.id.activity_user_email);
        _phone = findViewById(R.id.activity_user_phone);
        _cellphone = findViewById(R.id.activity_user_cellphone);

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
            _img_user_back.setImageURI(user.getBackImage());
            _name.setText(user.getDisplayName());
            _email.setText(user.getEmail());
            _phone.setText(user.getTelefone());
            _cellphone.setText(user.getCelular());
        }
    }
}
