package com.br.unicornlover.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.br.unicornlover.R;
import com.br.unicornlover.adapter.UnicornAdapter;
import com.br.unicornlover.databinding.ActivityMainBinding;
import com.br.unicornlover.model.Unicorn;
import com.br.unicornlover.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements UnicornAdapter.Callback {

    private MainActivityViewModel viewModel;
    private final UnicornAdapter adapter = new UnicornAdapter(Collections.emptyList(), this);
    private final List<Unicorn> unicornsList = new ArrayList<>();
    private SharedPreferences preferences;
    private Date lastTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        setSupportActionBar(binding.toolbar);
        initSharedPreferences();
        binding.unicornList.setAdapter(adapter);
        getUnicorns(new Date().after(lastTime));
        observable();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.create_unicorn) {
            Intent intent = new Intent(this, CreateUnicornActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.save_unicorn) {
            viewModel.saveFile(unicornsList);
        }
        return true;
    }

    private void initSharedPreferences() {
        preferences = this.getPreferences(MODE_PRIVATE);
        lastTime = new Date(preferences.getLong("date", 0));
    }

    private void getUnicorns(boolean shouldCallFromApi) {
        viewModel.getAllUnicorns(!shouldCallFromApi);
    }

    private void observable() {
        viewModel.unicornList.observe(this, unicorns -> {
            saveLastTimeToGetRetrofitData();
            viewModel.cacheUnicornList(unicorns);
            adapter.update(unicorns);
        });
    }

    private void saveLastTimeToGetRetrofitData() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong("date", (System.currentTimeMillis() + (60 * 1000)));
        editor.apply();
    }

    @Override
    public void onDeleteClick(String id) {
        viewModel.deleteUnicorn(id);
    }

    @Override
    public void onEditClick(String id) {
        Intent intent = new Intent(this, EditUnicornActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    @Override
    public void onContainerClick(String id) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}