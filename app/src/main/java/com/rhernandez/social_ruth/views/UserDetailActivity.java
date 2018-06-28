package com.rhernandez.social_ruth.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.rhernandez.social_ruth.R;
import com.rhernandez.social_ruth.models.PostEntity;
import com.rhernandez.social_ruth.models.UserEntity;

/**
 * Created by rhernandez on 28/6/18.
 */
public class UserDetailActivity extends AppCompatActivity {

    private PostEntity selection;
    private EditText name, phone, estado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);
        selection = (PostEntity) getIntent().getSerializableExtra("post");
        initUI();
    }

    private void initUI() {
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        estado = findViewById(R.id.estado);

        (findViewById(R.id.submit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserEntity postEntity = new UserEntity();
                postEntity.setImage("");
                postEntity.setName(name.getText().toString());
                postEntity.setPhone(phone.getText().toString());
                postEntity.setState(estado.getText().toString());
            }
        });

        (findViewById(R.id.posts)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDetailActivity.this, UserPostActivity.class);
                startActivity(intent);
            }
        });
    }
}
