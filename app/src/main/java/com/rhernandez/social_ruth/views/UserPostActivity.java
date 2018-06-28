package com.rhernandez.social_ruth.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import com.rhernandez.social_ruth.R;
import com.rhernandez.social_ruth.SocialRuth;
import com.rhernandez.social_ruth.adapters.PostAdapter;
import com.rhernandez.social_ruth.models.PostEntity;
import com.rhernandez.social_ruth.utilities.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roberto Hernandez on 6/27/2018.
 */

public class UserPostActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);
        RecyclerView list = findViewById(R.id.list);
        PostAdapter adapter = new PostAdapter(this, SocialRuth.getInstance().getPostEntities());
        list.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        list.setAdapter(adapter);
    }
}