package com.br.unicornlover.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.br.unicornlover.R;
import com.br.unicornlover.model.Unicorn;
import com.br.unicornlover.viewmodel.EditUnicornViewModel;

public class EditUnicornActivity extends AppCompatActivity {

    private EditText name;
    private EditText age;
    private EditText colour;
    private Button submit;
    private EditUnicornViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_unicorn);

        final String id = getIntent().getStringExtra("id");

        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        colour = findViewById(R.id.colour);

        submit = findViewById(R.id.submit);

        viewModel = new ViewModelProvider(this).get(EditUnicornViewModel.class);

        submit.setOnClickListener(view -> {
            Unicorn unicorn = new Unicorn();
            unicorn.setAge(Integer.parseInt(age.getText().toString()));
            unicorn.setName(name.getText().toString());
            unicorn.setColour(colour.getText().toString());
            viewModel.editUnicorn(id, unicorn);
        });
    }
}
