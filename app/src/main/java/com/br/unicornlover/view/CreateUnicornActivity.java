package com.br.unicornlover.view;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.br.unicornlover.R;
import com.br.unicornlover.databinding.ActivityCreateUnicornBinding;
import com.br.unicornlover.model.Unicorn;
import com.br.unicornlover.viewmodel.CreateViewModel;

public class CreateUnicornActivity extends AppCompatActivity {

    private final Unicorn unicorn = new Unicorn();
    private CreateViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCreateUnicornBinding binding
                = DataBindingUtil.setContentView(this, R.layout.activity_create_unicorn);
        viewModel = new ViewModelProvider(this).get(CreateViewModel.class);
        binding.setViewModel(viewModel);
        binding.setUnicorn(unicorn);
    }

    public void onClick(View view) {
        viewModel.createUnicorn(unicorn);
    }
}