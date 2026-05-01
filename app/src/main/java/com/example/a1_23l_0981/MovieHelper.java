package com.example.a1_23l_0981;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MovieHelper {

    public static List<Movie> loadMovies(Context context, String category) {
        List<Movie> Movies = new ArrayList<>();
        try {
            // read from assets/movies.json
            InputStream is = context.getAssets().open("Movies.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String json = new String(buffer, "UTF-8");
            JSONObject root = new JSONObject(json);
            JSONArray array = root.getJSONArray(category); // "now_showing" or "coming_soon"
            Log.d("MovieHelper", "Loaded " + Movies.size() + " movies from: " + category);

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                Movies.add(new Movie(
                        obj.getString("title"),
                        obj.getString("genre_duration"),
                        obj.getString("poster"),
                        obj.getString("trailer_url"),
                        obj.getBoolean("is_now_showing")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Movies;
    }
}