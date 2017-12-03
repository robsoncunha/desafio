package desafio.pitang.robson.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import net.accedegh.robson.R;
import desafio.pitang.robson.api.Client;
import desafio.pitang.robson.api.Service;
import desafio.pitang.robson.model.MovieDetail;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Robson Cunha on 02/12/2017.
 */

public class DetailActivity extends AppCompatActivity {


    TextView Title,Year ,Plot, Directors, Actors;
    Toolbar mActionBarToolbar;
    ImageView imageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String _id = getIntent().getExtras().getString("movie_id");
        loadJSON(_id);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setContentView(R.layout.activity_detail);



        imageView = (ImageView) findViewById(R.id.movie_image_header);

        Plot = (TextView) findViewById(R.id.plot);


        String title = getIntent().getExtras().getString("movie_title");
        String coverUrl =getIntent().getExtras().getString("movie_cover");
        String movie_descript = getIntent().getExtras().getString("movie_descript");

        Plot.setText(movie_descript);

        Glide.with(this)
                .load(coverUrl)

                .placeholder(R.drawable.load)
                .into(imageView);


        //set toolbar title
        getSupportActionBar().setTitle(title);




    }

    private void loadJSON(String id) {
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

                }

                @Override
                public void onFailure(Call<MovieDetail> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(DetailActivity.this, "Erro ao carregar os dados", Toast.LENGTH_SHORT).show();

                }

            });

        } catch (Exception e) {
            Log.d("Error", e.getMessage());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
