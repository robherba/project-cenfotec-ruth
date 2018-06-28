package com.rhernandez.social_ruth.adapters;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rhernandez.social_ruth.R;
import com.rhernandez.social_ruth.SocialRuth;
import com.rhernandez.social_ruth.models.PostEntity;
import com.rhernandez.social_ruth.views.PostDetailActivity;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Roberto Hernandez on 6/24/2018.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    public Activity activity;
    public List<PostEntity> entities;

    public PostAdapter(Activity activity, List<PostEntity> entities) {
        this.activity = activity;
        this.entities = entities;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_post, parent, false);
        return new PostViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PostViewHolder holder, final int position) {
        final PostEntity post = entities.get(position);
        holder.user_name.setText(post.getUserName());
        holder.likes.setText(String.valueOf(post.getLikes()));
        holder.body.setText(post.getTitle());
        if (post.getImage() != null) {
            loadImage(holder.image, post.getImage());
        } else {
            if (test()) {
                holder.image.setImageBitmap(decodeSampledBitmapFromFile(post.getPath(), 500, 500));
            }
        }
        loadImage(holder.user_photo, post.getUserPhoto());

        holder.likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post.setLikes(post.getLikes()+1);
                holder.likes.setText(String.valueOf(post.getLikes()));
                Toast.makeText(activity, "Likes: " + post.getLikes(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "Share action!", Toast.LENGTH_SHORT).show();
            }
        });

        holder.post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, PostDetailActivity.class);
                intent.putExtra("post", post);
                activity.startActivity(intent);
            }
        });
    }

    public boolean test() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            return false;
        } else {
            return true;
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

    public void loadImage(ImageView view, String url) {
        Picasso.get()
            .load(url)
            .placeholder(R.drawable.bg_auth)
            .into(view);
    }

    @Override
    public int getItemCount() {
        return entities.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {

        public ImageView image, likeBtn, shareBtn;
        public CircleImageView user_photo;
        public TextView likes;
        public TextView body, user_name;
        public CardView post;

        public PostViewHolder(View view) {
            super(view);
            image = view.findViewById(R.id.post_img);
            likes = view.findViewById(R.id.post_likes);
            body = view.findViewById(R.id.post_body);
            likeBtn = view.findViewById(R.id.like_btn);
            shareBtn = view.findViewById(R.id.share_btn);
            post = view.findViewById(R.id.post);

            user_name = view.findViewById(R.id.user_name);
            user_photo = view.findViewById(R.id.user_photo);
        }
    }
}
