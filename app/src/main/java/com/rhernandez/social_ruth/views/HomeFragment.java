package com.rhernandez.social_ruth.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.rhernandez.social_ruth.R;
import com.rhernandez.social_ruth.SocialRuth;
import com.rhernandez.social_ruth.adapters.PostAdapter;
import com.rhernandez.social_ruth.models.PostEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roberto Hernandez on 6/24/2018.
 */

public class HomeFragment extends Fragment {

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerView list = view.findViewById(R.id.list);
        PostAdapter adapter = new PostAdapter(getActivity(), SocialRuth.getInstance().getPostEntities());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        list.setLayoutManager(mLayoutManager);
        list.setItemAnimator(new DefaultItemAnimator());
        list.setAdapter(adapter);
        return view;
    }
}