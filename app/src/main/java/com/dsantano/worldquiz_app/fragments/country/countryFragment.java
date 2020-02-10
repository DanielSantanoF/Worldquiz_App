package com.dsantano.worldquiz_app.fragments.country;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.dsantano.worldquiz_app.Interfaces.ICountryListener;
import com.dsantano.worldquiz_app.R;
import com.dsantano.worldquiz_app.models.Country;
import com.dsantano.worldquiz_app.retrofit.generator.CountryGenerator;
import com.dsantano.worldquiz_app.retrofit.services.CountryService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class countryFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private ICountryListener mListener;
    private MycountryRecyclerViewAdapter adapter;
    private CountryService countryService;
    private View view;
    private List<Country> lista;
    private CountryService service;
    private Context context;
    private  RecyclerView recyclerView;


    public countryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.country_menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.filter);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_country_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

        }

        service = CountryGenerator.createService(CountryService.class);

        Call<List<Country>> call = service.allCountry();

        call.enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                if (response.isSuccessful()) {
                    lista = response.body();
                    cargarDatos();
                } else {
                    Toast.makeText(context, "Error al realizar la petición", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {
                Log.e("Network Failure", t.getMessage());
                Toast.makeText(context, "Error al realizar la petición", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ICountryListener) {
            mListener = (ICountryListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ICountryListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void cargarDatos(){

        adapter = new MycountryRecyclerViewAdapter(
                context,
                R.layout.fragment_country,
                lista
        );
        recyclerView.setAdapter(adapter);
    }

}
