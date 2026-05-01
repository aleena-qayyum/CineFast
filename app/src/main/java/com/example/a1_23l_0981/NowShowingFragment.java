package com.example.a1_23l_0981;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class NowShowingFragment extends Fragment implements MovieAdapter.OnBookListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_now_showing, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<Movie> movies = MovieHelper.loadMovies(getContext(), "now_showing");
        MovieAdapter adapter = new MovieAdapter(getContext(), movies, this);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onBookClick(Movie movie) {
        ((MainActivity) requireActivity()).openSeatSelection(movie);
    }
}