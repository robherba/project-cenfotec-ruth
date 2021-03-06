package com.rhernandez.social_ruth.views;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.rhernandez.social_ruth.R;
import com.rhernandez.social_ruth.SocialRuth;
import com.rhernandez.social_ruth.adapters.UserAdapter;
import com.rhernandez.social_ruth.models.UserEntity;
import com.rhernandez.social_ruth.utilities.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Roberto Hernandez on 6/24/2018.
 */

public class UsersFragment extends Fragment {

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerView list = view.findViewById(R.id.list);
        final UserAdapter adapter = new UserAdapter(getActivity(), SocialRuth.getInstance().getUserEntities());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        list.setLayoutManager(mLayoutManager);
        list.addItemDecoration(new DividerItemDecoration(getActivity()));
        list.setItemAnimator(new DefaultItemAnimator());
        list.setAdapter(adapter);
        final EditText search  = view.findViewById(R.id.search);
        search.setVisibility(View.VISIBLE);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!search.getText().toString().isEmpty()) {
                    String text = search.getText().toString().toLowerCase(Locale.getDefault());
                    adapter.filter(text);
                } else {
                    adapter.filter("");
                }
            }
        });
        return view;
    }
}