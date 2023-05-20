package com.br.unicornlover.view;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.br.unicornlover.R;
import com.br.unicornlover.viewmodel.DetailViewModel;

public class DetailActivity extends AppCompatActivity {

    private DetailViewModel viewModel;
    private TextView name;
    private TextView age;
    private TextView colour;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        String id = getIntent().getStringExtra("id");

        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        colour = findViewById(R.id.colour);

        viewModel = new ViewModelProvider(this).get(DetailViewModel.class);

        viewModel.getUnicornDetail(id);
        observable();
    }

    private void observable() {
        viewModel.unicornLiveData.observe(this, unicorn -> {
            if (unicorn != null) {
                name.setText(unicorn.getName());
                age.setText(unicorn.getAge());
                colour.setText(unicorn.getColour());
            }
        });
    }
}
