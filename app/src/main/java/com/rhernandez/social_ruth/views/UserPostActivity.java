package com.rhernandez.social_ruth.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toolbar;

import com.rhernandez.social_ruth.R;
import com.rhernandez.social_ruth.SocialRuth;
import com.rhernandez.social_ruth.adapters.PostAdapter;
import com.rhernandez.social_ruth.models.PostEntity;
import com.rhernandez.social_ruth.models.UserEntity;
import com.rhernandez.social_ruth.utilities.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roberto Hernandez on 6/27/2018.
 */

public class UserPostActivity  extends AppCompatActivity {

    private UserEntity selection;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);

        selection = (UserEntity) getIntent().getSerializableExtra("user");

        toolbar = findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
        setActionBar(toolbar);
        getActionBar().setTitle(selection.getName());
        getActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView list = findViewById(R.id.list);
        PostAdapter adapter = new PostAdapter(this, SocialRuth.getInstance().getPostByUserName(selection.getName()));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(mLayoutManager);
        list.setItemAnimator(new DefaultItemAnimator());
        list.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}