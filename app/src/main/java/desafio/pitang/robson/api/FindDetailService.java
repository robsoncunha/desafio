package desafio.pitang.robson.api;

import android.util.Log;

import desafio.pitang.robson.model.MovieDetail;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by robson on 02/12/17.
 */

public class FindDetailService {
    public static String description;



    public static void loadJSONDataMoviedetail(String id) {
        Log.d("Error", id);

        try {
            Client Client = new Client();
            Service request = desafio.pitang.robson.api.Client.retrofit.create(Service.class);
            Call<MovieDetail> call = request.getMovieDetail(id);
            call.enqueue(new Callback<MovieDetail>() {


                @Override
                public void onResponse(Call<MovieDetail> call, Response<MovieDetail> response) {
                    MovieDetail movieDetail = new MovieDetail();

                    movieDetail = response.body();
                    description = movieDetail.getDescription();
                    Log.d("Error", description);

                }

                @Override
                public void onFailure(Call<MovieDetail> call, Throwable t) {
                    Log.d("Error", t.getMessage());

                }

            });

        } catch (Exception e) {
            Log.d("Error", e.getMessage());

        }


    }
}
