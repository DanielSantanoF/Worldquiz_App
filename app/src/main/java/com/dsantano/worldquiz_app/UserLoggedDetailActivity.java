package com.dsantano.worldquiz_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;

import java.util.Objects;

public class UserLoggedDetailActivity extends AppCompatActivity {

    String email, photo, name;
    ImageView ivUser;
    TextView txtUsername, txtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_logged_detail);

        email = Objects.requireNonNull(getIntent().getExtras().get("email")).toString();
        photo = Objects.requireNonNull(getIntent().getExtras().get("photo")).toString();
        name = Objects.requireNonNull(getIntent().getExtras().get("name")).toString();

        ivUser = findViewById(R.id.imageViewUserDetail);
        txtUsername = findViewById(R.id.textViewUsernameUserDetails);
        txtEmail = findViewById(R.id.textViewEmailUserDetails);

        txtUsername.setText(name);
        txtEmail.setText(email);
        Glide
                .with(this)
                .load(photo)
                .transform(new CircleCrop())
                .error(Glide.with(this).load(R.drawable.image_not_loaded_icon))
                .thumbnail(Glide.with(this).load(R.drawable.loading_gif))
                .into(ivUser);
    }
}
