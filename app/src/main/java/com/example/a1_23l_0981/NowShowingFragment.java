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

        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie("The Devil Wears Prada", "Rom Com | 2h 49m", "the_devil_wears_prada", "https://www.youtube.com/watch?v=zSWdZVtXT7E", true));
        movies.add(new Movie("27 Dresses", "Action | 3h 1m", "dresses_27", "https://www.youtube.com/watch?v=TcMBFSGVi1c", true));
        movies.add(new Movie("How to Lose a Guy in 10 Days", "Horror | 1h 52m", "how_to_lose_a_guy_in_10_days", "https://www.youtube.com/watch?v=k10ETZ41q5o", true));

        MovieAdapter adapter = new MovieAdapter(getContext(), movies, this);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onBookClick(Movie movie) {
        ((MainActivity) requireActivity()).openSeatSelection(movie);
    }
}