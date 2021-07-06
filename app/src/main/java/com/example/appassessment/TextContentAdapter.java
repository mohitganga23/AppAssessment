package com.example.appassessment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TextContentAdapter extends RecyclerView.Adapter<TextContentAdapter.ViewHolder> {

    Context context;
    List<Model> model;

    public TextContentAdapter(List<Model> model) {
        this.model = model;
    }

    @NonNull
    @Override
    public TextContentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_content_layout, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TextContentAdapter.ViewHolder holder, int position) {
        holder.setIsRecyclable(false);

        String textContent = model.get(position).getText();
        String time = model.get(position).getTimeStamp();

        holder.setTextContent(textContent, time);
        holder.setFadeAnimation(holder.itemView);
    }

    @Override
    public int getItemCount() {
        if (model.size() != 0){
            return model.size();
        }
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final View mView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setTextContent(String text, String time) {

            TextView textView = mView.findViewById(R.id.text_content);
            TextView timeStamp = mView.findViewById(R.id.time_stamp);

            textView.setText(text);
            timeStamp.setText(time);
        }

        public void setFadeAnimation(View view) {
            AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(500);
            view.startAnimation(anim);
        }
    }
}
