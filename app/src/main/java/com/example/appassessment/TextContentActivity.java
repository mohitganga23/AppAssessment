package com.example.appassessment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class TextContentActivity extends AppCompatActivity {

    List<Model> model;
    TextContentAdapter textContentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_content);

        MaterialToolbar materialToolbar = findViewById(R.id.toolbar);
        materialToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        DatabaseHandler db = new DatabaseHandler(this);
        RecyclerView recyclerView = findViewById(R.id.text_content_recycler_view);
        TextView toolbarTitle = findViewById(R.id.toolbar_title);

        String currentAppName = getIntent().getStringExtra("CURRENT_APP_NAME");
        toolbarTitle.setText(currentAppName);

        model = new ArrayList<>();
        model = db.getTextContent(currentAppName);

        textContentAdapter = new TextContentAdapter(model);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(textContentAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}