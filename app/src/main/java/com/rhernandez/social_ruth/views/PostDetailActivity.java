package com.rhernandez.social_ruth.views;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.rhernandez.social_ruth.R;
import com.rhernandez.social_ruth.models.PostEntity;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

/**
 * Created by Roberto Hernandez on 6/26/2018.
 */

public class PostDetailActivity extends AppCompatActivity {

    private PostEntity selection;
    private TextView title, description, info;
    private ImageView image;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_new_post);
        selection = (PostEntity) getIntent().getSerializableExtra("post");
        initUI();
        loadSelection();
    }

    private void initUI() {
        (findViewById(R.id.title)).setEnabled(false);
        (findViewById(R.id.description)).setEnabled(false);
        (findViewById(R.id.info)).setVisibility(View.GONE);
        image = findViewById(R.id.image);
        ((Button) findViewById(R.id.submit)).setText("Cerrar");
        (findViewById(R.id.submit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void setSelection(PostEntity selection) {
        this.selection = selection;
    }

    public void loadSelection() {
        ((TextView) findViewById(R.id.title)).setText(selection.getTitle());
        ((TextView) findViewById(R.id.description)).setText(selection.getDescription());
        if (selection.getImage() != null) {
            Picasso.get()
                    .load(selection.getImage())
                    .placeholder(R.drawable.bg_auth)
                    .into(image);
        } else {
            image.setImageBitmap(decodeSampledBitmapFromFile(selection.getPath(), 500, 500));
        }
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
}

