package com.br.unicornlover.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.br.unicornlover.R;
import com.br.unicornlover.model.Unicorn;
import com.br.unicornlover.viewmodel.CreateViewModel;

public class CreateUnicornActivity extends AppCompatActivity {

    private EditText name;
    private EditText age;
    private EditText colour;
    private CreateViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_unicorn);

        name = findViewById(R.id.name);
        colour = findViewById(R.id.colour);
        age = findViewById(R.id.age);
        Button submit = findViewById(R.id.submit);

        viewModel = new ViewModelProvider(this).get(CreateViewModel.class);

        submit.setOnClickListener(view -> {
            Unicorn unicorn = new Unicorn();
            unicorn.setName(name.getText().toString());
            unicorn.setColour(colour.getText().toString());
            unicorn.setAge(Integer.parseInt(age.getText().toString()));
            viewModel.createUnicorn(unicorn);
        });
    }
}