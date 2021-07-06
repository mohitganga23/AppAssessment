package com.example.appassessment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class AppLabelAdapter extends RecyclerView.Adapter<AppLabelAdapter.ViewHolder> {

    public Context context;
    public List<Model> appLabel;

    public AppLabelAdapter(List<Model> appLabel) {
        this.appLabel = appLabel;
    }

    @NonNull
    @Override
    public AppLabelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_label_layout, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppLabelAdapter.ViewHolder holder, int position) {

        holder.setIsRecyclable(false);

        String currentAppLabel = appLabel.get(position).getAppName();
        String currentPackageName = appLabel.get(position).getPackageName();

        try {
            Drawable icon = context.getPackageManager().getApplicationIcon(currentPackageName);
            holder.setAppLabelAndIcon(currentAppLabel, icon);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        holder.appLabelCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TextContentActivity.class);
                intent.putExtra("CURRENT_APP_NAME", currentAppLabel);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (appLabel.size() != 0) {
            return appLabel.size();
        }
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final View mView;
        private final MaterialCardView appLabelCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;

            appLabelCard = mView.findViewById(R.id.app_card);
        }

        public void setAppLabelAndIcon(String currentAppLabel, Drawable packageName) {
            ImageView icon = mView.findViewById(R.id.app_label_icon);
            TextView appLabel = mView.findViewById(R.id.app_label);

            appLabel.setText(currentAppLabel);
            icon.setImageDrawable(packageName);

        }
    }
}
