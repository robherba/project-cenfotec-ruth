package com.rhernandez.social_ruth.views;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.rhernandez.social_ruth.R;
import com.rhernandez.social_ruth.SocialRuth;
import com.rhernandez.social_ruth.models.PostEntity;

import java.io.IOException;

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
                if (checkSelfPermission()) {
                    uploadPhoto();
                }
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
                                MainActivity.getInstance().goToHome();
                            }
                        }, 3000);
                    clean();

                } else {
                    Toast.makeText(getActivity(), "Estimada usuaria, favor ingresar los datos indicados.", Toast.LENGTH_SHORT).show();
                    progress.dismiss();
                }
            }
        });

        MainActivity.getInstance().setImageMenu(new MainActivity.ImageMenu() {
            @Override
            public void onResponse(Uri selectedImage) {
                NewPostFragment.selectedImage = selectedImage;
                image.setImageBitmap(decodeSampledBitmapFromFile(getRealPathFromURI(NewPostFragment.selectedImage), 500, 500));
            }
        });
    }

    public void clean() {
        title.setText("");
        description.setText("");
        image.setImageURI(null);
        image.setBackgroundResource(R.drawable.add_photo);
        selectedImage = null;
    }

    public boolean checkSelfPermission() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            return false;
        }
        return true;
    }

    public Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight) { // BEST QUALITY MATCH
        //First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize, Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        int inSampleSize = 1;

        if (height > reqHeight) {
            inSampleSize = Math.round((float) height / (float) reqHeight);
        }
        int expectedWidth = width / inSampleSize;

        if (expectedWidth > reqWidth) {
            if (Math.round((float) width / (float) reqWidth) > inSampleSize) // If bigger SampSize..
                inSampleSize = Math.round((float) width / (float) reqWidth);
        }

        options.inSampleSize = inSampleSize;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        Bitmap finalPicture = BitmapFactory.decodeFile(path, options);

        Bitmap rotatePicture = null;
        try {
            ExifInterface ei = new ExifInterface(path);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotatePicture = rotateImage(finalPicture, 90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotatePicture = rotateImage(finalPicture, 180);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotatePicture = rotateImage(finalPicture, 270);
                    break;
                default:
                    rotatePicture = finalPicture;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rotatePicture;
    }

    public Bitmap rotateImage(Bitmap bitmap, int rotation) {
        Matrix matrix = new Matrix();
        matrix.postRotate(rotation);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        clean();
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

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Audio.Media.DATA };
        Cursor cursor = getActivity().managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
}
