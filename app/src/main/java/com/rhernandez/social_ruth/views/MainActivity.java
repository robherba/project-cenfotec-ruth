package com.rhernandez.social_ruth.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.rhernandez.social_ruth.R;
import com.rhernandez.social_ruth.adapters.TabAdapter;

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

    public static MainActivity getInstance() {
        return instance;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        new AlertDialog.Builder(this)
            .setTitle("Confirmar")
            .setMessage("¿Está segura que desea salir de Social Ruth?")
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    finish();
                }})
            .setNegativeButton("Cancelar", null)
            .show();
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