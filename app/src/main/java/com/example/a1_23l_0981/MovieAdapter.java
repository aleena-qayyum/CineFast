package com.example.a1_23l_0981;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private final Context context;
    private final List<Movie> movies;
    private final OnBookListener listener;

    public interface OnBookListener {
        void onBookClick(Movie movie);
    }

    public MovieAdapter(Context context, List<Movie> movies, OnBookListener listener) {
        this.context = context;
        this.movies = movies;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie m = movies.get(position);
        holder.tvTitle.setText(m.getTitle());
        holder.tvGenre.setText(m.getGenreDuration());

        int resId = context.getResources().getIdentifier(m.getPoster(), "drawable", context.getPackageName());
        if (resId != 0) holder.ivPoster.setImageResource(resId);

        holder.btnTrailer.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(m.getTrailerUrl()));
            context.startActivity(i);
        });

        holder.btnBook.setOnClickListener(v -> listener.onBookClick(m));
    }

    @Override
    public int getItemCount() { return movies.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPoster;
        TextView tvTitle, tvGenre;
        Button btnTrailer, btnBook;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvGenre = itemView.findViewById(R.id.tvGenre);
            btnTrailer = itemView.findViewById(R.id.btnTrailer);
            btnBook = itemView.findViewById(R.id.btnBook);
        }
    }
}