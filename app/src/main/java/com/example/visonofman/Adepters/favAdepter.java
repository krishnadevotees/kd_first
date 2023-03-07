package com.example.visonofman.Adepters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visonofman.ModelClass.favModel;
import com.example.visonofman.R;

import java.util.List;

public class favAdepter extends RecyclerView.Adapter<favAdepter.ViewHolder> {

    private List<favModel> list ;

    public favAdepter(List<favModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public favAdepter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_layout, parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull favAdepter.ViewHolder holder, int position) {
        favModel item = list.get(position);
        holder.textViewVerse.setText(item.getVerse());
        holder.textViewTranslation.setText(item.getTranslate());
        holder.textViewDescription.setText(item.getDescription());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewId;
        TextView textViewVerse;
        TextView textViewTranslation;
        TextView textViewDescription;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewVerse = itemView.findViewById(R.id.textViewVerse);
            textViewTranslation = itemView.findViewById(R.id.textViewTranslation);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
        }
    }
}
