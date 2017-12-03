package desafio.pitang.robson.controller;

import android.app.ProgressDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import desafio.pitang.robson.MoviesAdapter;
import net.accedegh.robson.R;
import desafio.pitang.robson.api.Service;
import desafio.pitang.robson.api.Client;
import desafio.pitang.robson.model.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Robson Cunha on 02/12/2017.
 */
public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Movie> movieArrayList;
     TextView Disconnected;
    private Movie mov;
    ProgressDialog pd;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // Disconnected.setVisibility(View.INVISIBLE);
        initViews();
        //https://guides.codepath.com/android/Implementing-Pull-to-Refresh-Guide
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);


        swipeContainer.setColorSchemeResources(android.R.color.holo_orange_dark);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadJSON();
                Toast.makeText(MainActivity.this,"Refresh na lista.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initViews(){
        pd = new ProgressDialog(this);
        pd.setMessage("Carregando dados de videos...");
        pd.setCancelable(false);
        pd.show();
        recyclerView=(RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.smoothScrollToPosition(0);
        loadJSON();
    }


    private void loadJSON() {
        Disconnected = (TextView) findViewById(R.id.disconnected);
        try {
            Client Client = new Client();
            Service request = desafio.pitang.robson.api.Client.retrofit.create(Service.class);
            Call<List<Movie>> call = request.getMovies();
            call.enqueue(new Callback<List<Movie>>() {

                @Override
                public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                    movieArrayList = response.body();
                    recyclerView.setAdapter(new MoviesAdapter(getApplicationContext(),movieArrayList));
                    recyclerView.smoothScrollToPosition(0);

                    swipeContainer.setRefreshing(false);
                    pd.hide();
                }

                @Override
                public void onFailure(Call<List<Movie>> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(MainActivity.this, "Error ao buscar os dados!", Toast.LENGTH_SHORT).show();
                    Disconnected.setVisibility(View.VISIBLE);
                    pd.hide();
                }

            });

        } catch (Exception e) {
            Log.d("Error", e.getMessage());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

}
