package desafio.pitang.robson.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Robson Cunha on 02/12/2017.
 */

public class Client {


    public static final String BASE_URL_MOVIE = "https://desafio-android-pitang.herokuapp.com";
    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL_MOVIE)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

}
