package com.br.unicornlover.view;

import android.content.Intent;
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
import com.br.unicornlover.viewmodel.MainActivityViewModel;

import java.util.Collections;

public class MainActivity extends AppCompatActivity implements UnicornAdapter.Callback {

    private MainActivityViewModel viewModel;
    private UnicornAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        getUnicorns();
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
            //TODO save on room
        }
        return true;
    }

    private void init() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        adapter = new UnicornAdapter(Collections.emptyList(), this);
        RecyclerView recyclerView = findViewById(R.id.unicorn_list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getUnicorns() {
        viewModel.getUnicornsResponseLiveData().observe(this, unicorns -> {
            if (unicorns != null) {
                adapter.update(unicorns);
            }
        });
    }

    @Override
    public void onDeleteClick(String id) {
        viewModel.deleteUnicorn(id);
    }

    @Override
    public void onEditClick(String id) {
        viewModel.editUnicorn(id);
    }

    @Override
    public void onContainerClick(String id) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}