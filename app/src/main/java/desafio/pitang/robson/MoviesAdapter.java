package desafio.pitang.robson;


/**
 * Created by Robson Cunha on 02/12/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import net.accedegh.robson.R;

import java.util.List;

import desafio.pitang.robson.api.FindDetailService;
import desafio.pitang.robson.controller.DetailActivity;
import desafio.pitang.robson.model.Movie;


public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    private List<Movie> movies;
    private Context context;
   // private Movie movie;

    public MoviesAdapter(Context applicationContext, List<Movie> movieArrayList) {
        this.context =applicationContext;
        this.movies=movieArrayList;
    }

    @Override
    public MoviesAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_movie, viewGroup, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviesAdapter.ViewHolder viewHolder, int i) {
        viewHolder.title.setText(movies.get(i).getName());


        // usando Picasso para carregar imagens da internet
       Picasso.with(context)
                .load(movies.get(i).getUrl())
                .placeholder(R.drawable.load)
               .into(viewHolder.imageView);

    }
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title, year, actors;
        private ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
          //  year = (TextView) view.findViewById(R.id.year);
           // actors = (TextView) view.findViewById(R.id.actors);
            imageView= (ImageView) view.findViewById(R.id.cover);

            // on item click
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {


                    // get position
                    int pos = getAdapterPosition();
                    // check if item still exists
                    if(pos != RecyclerView.NO_POSITION){
                        Movie clickedDataItem = movies.get(pos);
                        FindDetailService.loadJSONDataMoviedetail(movies.get(pos).getId());
                        Intent intent = new Intent(context, DetailActivity.class);
                        intent.putExtra("movie_title", movies.get(pos).getName());
                        intent.putExtra("movie_id", movies.get(pos).getId());

                        intent.putExtra("movie_cover", movies.get(pos).getUrl());

                        intent.putExtra("movie_descript", FindDetailService.description);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        Toast.makeText(v.getContext(), "You clicked " + clickedDataItem.getName(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        }

    /* Within the RecyclerView.Adapter class */
    // Clean all elements of the recycler
    public void clear() {
        movies.clear();
        notifyDataSetChanged();
    }

    //RecyclerView mRecycler;
    // Add a list of ites
    public void addAll(int position, List<Movie> mov) {
        movies.addAll(0,mov);
        notifyItemInserted(0);
        //mRecycler.smoothScrollToPosition(0);
        notifyDataSetChanged();
    }



}