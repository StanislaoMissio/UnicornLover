package com.br.unicornlover.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.br.unicornlover.R;
import com.br.unicornlover.adapter.UnicornAdapter;
import com.br.unicornlover.model.Unicorn;
import com.br.unicornlover.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements UnicornAdapter.Callback {

    private MainActivityViewModel viewModel;
    private UnicornAdapter adapter;
    private final List<Unicorn> unicornsList = new ArrayList<>();
    private SharedPreferences preferences;
    private Date lastTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        init();

        if (new Date().after(lastTime)) {
            getUnicorns();
        } else {
            getCachedUnicorns();
        }
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

    private void init() {
        preferences = this.getPreferences(MODE_PRIVATE);
        lastTime = new Date(preferences.getLong("date", 0));
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        adapter = new UnicornAdapter(Collections.emptyList(), this);
        RecyclerView recyclerView = findViewById(R.id.unicorn_list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getUnicorns() {
        viewModel.getAllUnicorns();
    }

    private void getCachedUnicorns() {
        viewModel.getCachedUnicorns();
    }

    private void observable() {
        viewModel.unicornList.observe(this, unicorns -> {
            saveLastTimeToGetRetrofitData();
            viewModel.cacheUnicornList(unicorns);
            adapter.update(unicorns);
            unicornsList.addAll(unicorns);
        });
        viewModel.cachedUnicornList.observe(this, unicorns -> {
            adapter.update(unicorns);
            unicornsList.addAll(unicorns);
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