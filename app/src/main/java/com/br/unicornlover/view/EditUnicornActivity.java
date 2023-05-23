package com.br.unicornlover.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.br.unicornlover.R;
import com.br.unicornlover.databinding.ActivityEditUnicornBinding;
import com.br.unicornlover.model.Unicorn;
import com.br.unicornlover.viewmodel.EditUnicornViewModel;

public class EditUnicornActivity extends AppCompatActivity {

    private final Unicorn unicorn = new Unicorn();
    private EditUnicornViewModel viewModel;
    private String id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityEditUnicornBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_unicorn);
        binding.setLifecycleOwner(this);

        id = getIntent().getStringExtra("id");

        viewModel = new ViewModelProvider(this).get(EditUnicornViewModel.class);
        binding.setViewModel(viewModel);
        binding.setUnicorn(unicorn);
        viewModel.isSuccess.observe(this, isSuccess -> {
            if (isSuccess) {
                Toast.makeText(this, R.string.successful_edit, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        viewModel.error.observe(this, message -> {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });
    }

    public void onClick(View view) {
        viewModel.editUnicorn(id, unicorn);
    }
}
