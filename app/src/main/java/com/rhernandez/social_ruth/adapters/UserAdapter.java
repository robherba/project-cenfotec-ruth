package com.rhernandez.social_ruth.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.rhernandez.social_ruth.R;
import com.rhernandez.social_ruth.models.UserEntity;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Roberto Hernandez on 6/24/2018.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.PostViewHolder> implements Filterable {

    public Activity activity;
    public List<UserEntity> entities;
    public List<UserEntity> filterList;
    private EntityFilter entityFilter;

    public UserAdapter(Activity activity, List<UserEntity> entities) {
        this.activity = activity;
        this.entities = entities;
        this.filterList = new ArrayList<>();
        this.filterList.addAll(entities);
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_user, parent, false);
        return new PostViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PostViewHolder holder, final int position) {
        final UserEntity user = entities.get(position);
        holder.name.setText(user.getName());
        holder.state.setText(user.getState());
        loadImage(holder.photo, user.getImage());
        holder.user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
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

    public void filter(String charText) {
        entities.clear();
        if (!charText.isEmpty()) {
            charText = charText.toLowerCase(Locale.getDefault());
            entities.clear();
            for (UserEntity entity : filterList) {
                if (entity.getName() != null) {
                    if (entity.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                        entities.add(entity);
                    }
                }
            }
        } else {
            entities.addAll(filterList);
        }
        notifyDataSetChanged();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {

        public ImageView photo;
        public TextView name, state;
        public LinearLayout user;

        public PostViewHolder(View view) {
            super(view);
            photo = view.findViewById(R.id.user_photo);
            name = view.findViewById(R.id.user_name);
            state = view.findViewById(R.id.user_state);
            user = view.findViewById(R.id.user);
        }
    }

    @Override
    public Filter getFilter() {
        if (entityFilter == null) {
            entityFilter = new EntityFilter();
        }
        return entityFilter;
    }

    public class EntityFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint == null || constraint.length() == 0) {
                results.values = entities;
                results.count = entities.size();
            } else {
                List<UserEntity> filter = new ArrayList<>();

                for (UserEntity entity : entities) {
                    if (!entity.getName().toUpperCase().equals(constraint.toString().toUpperCase())) {
                        filter.add(entity);
                    }
                }

                results.values = filter;
                results.count = filter.size();

            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count == 0)
                notifyDataSetChanged();
            else {
                entities = (List<UserEntity>) results.values;
                notifyDataSetChanged();
            }
        }
    }
}
