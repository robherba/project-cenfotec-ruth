package com.rhernandez.social_ruth.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.rhernandez.social_ruth.R;
import com.rhernandez.social_ruth.adapters.TabAdapter;
import com.rhernandez.social_ruth.models.UserEntity;

public class MainActivity extends AppCompatActivity {

    public static MainActivity instance;
    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageMenu imageMenu;
    private int[] tabIcons = {
            R.drawable.home,
            R.drawable.search,
            R.drawable.photo_camera,
            R.drawable.woman
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.instance = this;
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment());
        adapter.addFragment(new UsersFragment());
        adapter.addFragment(new NewPostFragment());
        adapter.addFragment(new ProfileFragment());

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        for (int index = 0; index < tabLayout.getTabCount(); index++) {
            tabLayout.getTabAt(index).setIcon(tabIcons[index]);
        }
    }

    public void goToHome() {
        viewPager.setCurrentItem(0);
    }

    public static MainActivity getInstance() {
        return instance;
    }

    @Override
    public void onBackPressed() {
        android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.view_comfirmatio, null);
        dialogBuilder.setView(dialogView);

        TextView confirm = dialogView.findViewById(R.id.confirm);
        TextView cancel = dialogView.findViewById(R.id.cancel);

        final android.app.AlertDialog alertDialog = dialogBuilder.create();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                finish();
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

    public void setImageMenu(ImageMenu imageMenu) {
        this.imageMenu = imageMenu;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        if(resultCode == RESULT_OK) {
            imageMenu.onResponse(imageReturnedIntent.getData());
        }
    }

    public static interface ImageMenu {
        public void onResponse(Uri selectedImage);
    }
}