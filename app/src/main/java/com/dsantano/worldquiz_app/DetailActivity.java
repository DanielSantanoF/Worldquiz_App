package com.dsantano.worldquiz_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dsantano.worldquiz_app.models.Country;
import com.dsantano.worldquiz_app.retrofit.generator.CountryGenerator;
import com.dsantano.worldquiz_app.retrofit.services.CountryService;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    String alpha;
    TextView nombre, capital, poblacion, lat1 , lat2 , region,extension;
    ImageView foto, bandera;
    private CountryService service;
    Country c;
    Context ctx;
    SliderView sliderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ctx = this;

        sliderView = findViewById(R.id.imageSlider);

        List<String>  lista= new ArrayList<String>();
        lista.add("https://www.fundacioncarolina.es/wp-content/uploads/2018/03/Madrid_undia_1397223554.735.jpg");
        lista.add("https://a.cdn-hotels.com/gdcs/production9/d1285/34108d80-9beb-11e8-a942-0242ac110007.jpg");
        lista.add("https://gacetinmadrid.com/wp-content/uploads/2019/05/madrid-sol-centro-gente-personas-paseantes-peatones-3221.jpg");
        lista.add("https://www.esmadrid.com/sites/default/files/styles/content_type_full/public/editorial/descubremadrid/eligetumadrid/6-el_madrid_mas_clasico_2.jpg?itok=I7Rurbya");
        sliderView = findViewById(R.id.imageSlider);

        SliderAdapterExample adapter = new SliderAdapterExample(this,lista);

        sliderView.setSliderAdapter(adapter);

        sliderView.setIndicatorAnimation(IndicatorAnimations.DROP); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.FADETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
        sliderView.startAutoCycle();



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

        Call<Country> call = service.getCountry(alpha);

        call.enqueue(new Callback<Country>() {
            @Override
            public void onResponse(Call<Country> call, Response<Country> response) {
                if (response.isSuccessful()) {
                    c  = response.body();
                    nombre.setText(response.body().getName());
                    capital.setText(response.body().getCapital());
                    poblacion.setText(response.body().getPopulation()+"");
                    if (response.body().getLatlng().size() != 0){
                        lat1.setText(response.body().getLatlng().get(0)+"");
                        lat2.setText(response.body().getLatlng().get(1)+"");
                    }
                    region.setText(response.body().getRegion());
                    extension.setText(response.body().getArea()+"");


                    Glide.with(ctx)
                            .load("https://www.countryflags.io/"+response.body().alpha2Code+"/flat/64.png")
                            .into(bandera);
                } else {
                    Toast.makeText(ctx, "Error al realizar la petición", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Country> call, Throwable t) {
                Log.e("Network Failure", t.getMessage());
                Toast.makeText(ctx, "Error al realizar la petición", Toast.LENGTH_SHORT).show();
            }
        });





    }
}
