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

public class ComingSoonFragment extends Fragment implements MovieAdapter.OnBookListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coming_soon, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerComingSoon);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie("a quiet place", "Sci-Fi | 2h 45m", "a quiet place", "https://www.youtube.com/watch?v=TcMBFSGVi1c", false));
        movies.add(new Movie("quiet place 2", "Action | 3h 10m", "quiet place 2", "https://www.youtube.com/watch?v=TcMBFSGVi1c", false));
        movies.add(new Movie("gossip girl", "Drama | 2h 20m", "gossip girl", "https://www.youtube.com/watch?v=TcMBFSGVi1c", false));

        MovieAdapter adapter = new MovieAdapter(getContext(), movies, this);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onBookClick(Movie movie) {
        SeatSelectionFragment fragment = new SeatSelectionFragment();
        Bundle args = new Bundle();
        args.putString("movieName", movie.getTitle());
        args.putBoolean("isComingSoon", true);
        fragment.setArguments(args);

        ((MainActivity) requireActivity()).getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}