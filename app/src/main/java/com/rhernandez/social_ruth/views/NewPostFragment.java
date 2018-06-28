package com.rhernandez.social_ruth.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.rhernandez.social_ruth.R;
import com.rhernandez.social_ruth.SocialRuth;
import com.rhernandez.social_ruth.models.PostEntity;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by Roberto Hernandez on 6/25/2018.
 */

public class NewPostFragment extends Fragment {

    private View view;
    private TextView title, description;
    public static Uri selectedImage;
    private ImageView image;
    private Button submit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_new_post, container, false);
        initUI();
        return view;
    }

    private void initUI() {
        title = view.findViewById(R.id.title);
        description = view.findViewById(R.id.description);
        image = view.findViewById(R.id.image);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPhoto();
            }
        });

        submit = view.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MaterialDialog progress = showProgress();
                if (NewPostFragment.selectedImage != null && !title.getText().toString().isEmpty() && !description.getText().toString().isEmpty()) {
                    PostEntity postEntity = new PostEntity();
                    postEntity.setPath(getRealPathFromURI(NewPostFragment.selectedImage));
                    postEntity.setTitle(title.getText().toString());
                    postEntity.setDescription(description.getText().toString());
                    postEntity.setUserName(SocialRuth.getInstance().getValue("name"));
                    postEntity.setUserPhoto("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQd2ZKfTJ9umiE7LayIXlKwVBBbiCp3gocrjPOcSLI9c4xmTFC_XQ");
                    SocialRuth.getInstance().getPostEntities().add(postEntity);
                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                public void run() {
                                    progress.dismiss();
                                    title.setText("");
                                    description.setText("");
                                    image.setBackgroundResource(R.drawable.add_photo);
                                }
                            }, 3000);

                } else {
                    Toast.makeText(getActivity(), "Estimada usuaria, favor ingresar los datos indicados.", Toast.LENGTH_SHORT).show();
                    progress.dismiss();
                }
            }
        });

        MainActivity.getInstance().setImageMenu(new MainActivity.ImageMenu() {
            @Override
            public void onResponse(Uri selectedImage) {
                image.setImageURI(selectedImage);
                NewPostFragment.selectedImage = selectedImage;
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NewPostFragment.selectedImage = null;
        image.setImageURI(null);
    }

    private void uploadPhoto() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.view_image_menu, null);
        dialogBuilder.setView(dialogView);

        TextView gallery = dialogView.findViewById(R.id.gallery);
        TextView camera = dialogView.findViewById(R.id.camera);

        final AlertDialog alertDialog = dialogBuilder.create();
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 1);
                alertDialog.dismiss();
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture, 0);
                alertDialog.dismiss();
            }
        });
        alertDialog.setTitle("");
        alertDialog.show();
    }

    public MaterialDialog showProgress() {
        return new MaterialDialog.Builder(getActivity())
                .titleColorRes(R.color.colorPrimaryDark)
                .title("Creando post")
                .contentColorRes(R.color.colorPrimaryDark)
                .backgroundColorRes(R.color.white)
                .content("Cargando...")
                .cancelable(false)
                .progress(true, 0)
                .show();
    }

    public String getRealPathFromURI(Uri contentUri)
    {
        String[] proj = { MediaStore.Audio.Media.DATA };
        Cursor cursor = getActivity().managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
}
