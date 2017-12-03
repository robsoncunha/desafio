package desafio.pitang.robson.api;

import desafio.pitang.robson.model.Movie;
import desafio.pitang.robson.model.MovieDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Robson Cunha on 02/12/2017.
 */

public interface Service {
    @GET("/movies/list?page=0&size=3")
    Call<List<Movie>> getMovies();

    @GET("/movies/detail/{id}")
    Call<MovieDetail> getMovieDetail(@Path("id") String id);
}


