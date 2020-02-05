package com.dsantano.worldquiz_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;

import com.dsantano.worldquiz_app.models.Country;
import com.dsantano.worldquiz_app.models.UnsplashPhotosResult;
import com.dsantano.worldquiz_app.retrofit.generator.CountryGenerator;
import com.dsantano.worldquiz_app.retrofit.services.CountryService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Response;

public class QuizActivity extends AppCompatActivity {

    List<Country> listCountrys;
    CountryService service;
    int numCountrysForQuiz;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        listCountrys = new ArrayList<Country>();
        uid = getIntent().getExtras().get("uid").toString();

        service = CountryGenerator.createService(CountryService.class);
        new DownloadCountrys().execute();

    }

    public class DownloadCountrys extends AsyncTask<Void, Void, List<Country>>{

        List<Country> result;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Country> doInBackground(Void... voids) {
            Call<List<Country>> callAllCountrys = service.allCountry();
            Response<List<Country>> responseAllCountrys = null;
            try {
                responseAllCountrys = callAllCountrys.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (responseAllCountrys.isSuccessful()) {
                result = responseAllCountrys.body();
            }
            return result;
        }

        @Override
        protected void onPostExecute(List<Country> countries) {
            for(int i = 0; i<numCountrysForQuiz; i++){
                Random r = new Random();
                listCountrys.add(countries.get(r.nextInt()));
            }
        }
    }

}
