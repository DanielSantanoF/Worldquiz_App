package com.dsantano.worldquiz_app;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dsantano.worldquiz_app.models.Country;
import com.dsantano.worldquiz_app.models.UnsplashPhotosResult;
import com.dsantano.worldquiz_app.retrofit.generator.CountryGenerator;
import com.dsantano.worldquiz_app.retrofit.generator.UnsplashGenerator;
import com.dsantano.worldquiz_app.retrofit.services.CountryService;
import com.dsantano.worldquiz_app.retrofit.services.UnspashService;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountryDetailActivity extends AppCompatActivity {

    String alpha, nameCountry;
    TextView nombre, capital, poblacion, lat1 , lat2 , region,extension;
    ImageView foto, bandera;
    private CountryService service;
    UnspashService unspashService;
    Country c;
    SliderView sliderView;
    List<String> listCarrousel;
    SliderAdapterExample adapter;
    int numImgCarrousel = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_detail);

        alpha = getIntent().getExtras().getString("alpha");

        nombre = findViewById(R.id.textViewNameDetail);
        capital = findViewById(R.id.CapitalDetailEdit);
        poblacion = findViewById(R.id.populationDetailEdit);
        lat1 = findViewById(R.id.locationDetailEdit1);
        lat2 = findViewById(R.id.locationDetailEdit2);
        region = findViewById(R.id.regionDetailEdit);
        bandera = findViewById(R.id.imageViewBanderaDetail);
        extension = findViewById(R.id.areaDetailEdit);

        service = CountryGenerator.createService(CountryService.class);
        unspashService = UnsplashGenerator.createService(UnspashService.class);

        Call<Country> call = service.getCountry(alpha);

        call.enqueue(new Callback<Country>() {
            @Override
            public void onResponse(Call<Country> call, Response<Country> response) {
                if (response.isSuccessful()) {
                    c  = response.body();
                    nameCountry = response.body().getName();
                    nombre.setText(nameCountry);
                    capital.setText(response.body().getCapital());
                    poblacion.setText(response.body().getPopulation()+"");
                    if (response.body().getLatlng().size() != 0){
                        lat1.setText(response.body().getLatlng().get(0)+"");
                        lat2.setText(response.body().getLatlng().get(1)+"");
                    }
                    region.setText(response.body().getRegion());
                    extension.setText(response.body().getArea()+"");


                    Glide.with(CountryDetailActivity.this)
                            .load("https://www.countryflags.io/"+response.body().alpha2Code+"/flat/64.png")
                            .into(bandera);
                    new DowloadPhotosOfCountry().execute();
                } else {
                    Toast.makeText(CountryDetailActivity.this, "Error al realizar la petición", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Country> call, Throwable t) {
                Log.e("Network Failure", t.getMessage());
                Toast.makeText(CountryDetailActivity.this, "Error al realizar la petición", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class DowloadPhotosOfCountry extends AsyncTask<Void, Void, UnsplashPhotosResult>{

        UnsplashPhotosResult result;
        @Override
        protected UnsplashPhotosResult doInBackground(Void... voids) {
            Call<UnsplashPhotosResult> callCountryPhotos = unspashService.getEspecificCountryPhotos(nameCountry);
            Response<UnsplashPhotosResult> responseCountryPhotos = null;
            try {
                responseCountryPhotos = callCountryPhotos.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (responseCountryPhotos.isSuccessful()) {
                result = responseCountryPhotos.body();
            }
            return result;
        }

        @Override
        protected void onPostExecute(UnsplashPhotosResult unsplashPhotosResult) {
            sliderView = findViewById(R.id.imageSlider);
            listCarrousel = new ArrayList<String>();
            if(unsplashPhotosResult.results.size() < numImgCarrousel){
                numImgCarrousel = unsplashPhotosResult.results.size();
            }
            for(int i=0; i<numImgCarrousel; i++) {
                listCarrousel.add(unsplashPhotosResult.results.get(i).urls.regular);
            }
            adapter = new SliderAdapterExample(CountryDetailActivity.this,listCarrousel);

            sliderView.setSliderAdapter(adapter);
            sliderView.setIndicatorAnimation(IndicatorAnimations.DROP); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
            sliderView.setSliderTransformAnimation(SliderAnimations.FADETRANSFORMATION);
            sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
            sliderView.setIndicatorSelectedColor(Color.WHITE);
            sliderView.setIndicatorUnselectedColor(Color.GRAY);
            sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
            sliderView.startAutoCycle();
        }
    }

}
