package com.br.unicornlover.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.br.unicornlover.R;
import com.br.unicornlover.model.Unicorn;

import java.util.List;

public class UnicornAdapter extends RecyclerView.Adapter<UnicornAdapter.ViewHolder> {

    List<Unicorn> unicorns;
    Callback callback;

    public UnicornAdapter(List<Unicorn> unicorns, Callback callback) {
        this.unicorns = unicorns;
        this.callback = callback;
    }

    @NonNull
    @Override
    public UnicornAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.unicorns_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(unicorns.get(position).getName());
        holder.age.setText(unicorns.get(position).getAge());
        holder.colour.setText(unicorns.get(position).getColour());
        holder.colour.setText(unicorns.get(position).getColour());
        holder.edit.setOnClickListener(view -> {
            callback.onEditClick(unicorns.get(position).getId());
        });
        holder.delete.setOnClickListener(view -> {
            callback.onDeleteClick(unicorns.get(position).getId());
        });
        holder.container.setOnClickListener(view -> {
            callback.onContainerClick(unicorns.get(position).getId());
        });

    }

    @Override
    public int getItemCount() {
        return unicorns.size();
    }

    public void update(List<Unicorn> unicornList) {
        this.unicorns = unicornList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView name;
        private final TextView age;
        private final TextView colour;
        private final ImageView delete;
        private final ImageView edit;
        private final ConstraintLayout container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            age = itemView.findViewById(R.id.age);
            colour = itemView.findViewById(R.id.colour);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
            container = itemView.findViewById(R.id.container);

        }
    }

    public interface Callback {
        void onDeleteClick(String id);

        void onEditClick(String id);

        void onContainerClick(String id);
    }
}
