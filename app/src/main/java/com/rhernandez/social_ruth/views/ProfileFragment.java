package com.rhernandez.social_ruth.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rhernandez.social_ruth.R;
import com.rhernandez.social_ruth.SocialRuth;
import com.rhernandez.social_ruth.models.PostEntity;
import com.rhernandez.social_ruth.models.UserEntity;
import com.squareup.picasso.Picasso;

/**
 * Created by Roberto Hernandez on 6/26/2018.
 */

public class ProfileFragment extends Fragment {

    private View view;
    private EditText name, phone, state;
    private ImageView user_photo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        initUI();
        loadUseData();
        return view;
    }

    private void loadUseData() {
        name.setText(SocialRuth.getInstance().getValue("name"));
        phone.setText(SocialRuth.getInstance().getValue("phone"));
        state.setText(SocialRuth.getInstance().getValue("state"));
        Picasso.get()
                .load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQd2ZKfTJ9umiE7LayIXlKwVBBbiCp3gocrjPOcSLI9c4xmTFC_XQ")
                .placeholder(R.drawable.bg_auth)
                .into(user_photo);
    }
    private void initUI() {
        name = view.findViewById(R.id.name);
        phone = view.findViewById(R.id.phone);
        state = view.findViewById(R.id.estado);
        user_photo = view.findViewById(R.id.user_photo);

        (view.findViewById(R.id.submit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SocialRuth.getInstance().saveData("name", name.getText().toString());
                SocialRuth.getInstance().saveData("phone", phone.getText().toString());
                SocialRuth.getInstance().saveData("state", state.getText().toString());
                Toast.makeText(getActivity(), "Datos Actualizados..", Toast.LENGTH_SHORT).show();
            }
        });

        (view.findViewById(R.id.posts)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UserPostActivity.class);
                UserEntity user = new UserEntity();
                user.setName(SocialRuth.getInstance().getValue("name"));
                user.setPhone(SocialRuth.getInstance().getValue("phone"));
                user.setState(SocialRuth.getInstance().getValue("state"));
                intent.putExtra("user", user);
                getActivity().startActivity(intent);
            }
        });

        (view.findViewById(R.id.loguot)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    public void logout() {
        android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(getActivity());
        View dialogView = getActivity().getLayoutInflater().inflate(R.layout.view_comfirmatio, null);
        dialogBuilder.setView(dialogView);

        TextView confirm = dialogView.findViewById(R.id.confirm);
        TextView cancel = dialogView.findViewById(R.id.cancel);

        final android.app.AlertDialog alertDialog = dialogBuilder.create();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                SocialRuth.getInstance().removeData("login");
                Intent intent = new Intent(getActivity(), AuthenticationActivity.class);
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.setTitle("");
        alertDialog.show();
    }
}