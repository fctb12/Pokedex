package com.example.pokedex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, FilterDialog.FilterDialogListener {

    private RecyclerView mRecyclerView;
    private RecyclerView gridRecyclerView;
    private PokemonAdapter mAdapter;
    private PokemonGridAdapter gridAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.LayoutManager gridLayoutManager;
    public int view = 0;
    ImageView list;
    ImageView grid;
    ImageView homeImage;
    SearchView search;
    Button filterButton;
    private ArrayList<Pokemon> pokemonList = new ArrayList<>();

    JSONObject obj;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeImage = (ImageView) findViewById(R.id.banner);
        homeImage.setImageResource(R.drawable.banner);

        list = (ImageView) findViewById(R.id.listLayout);
        list.setImageResource(R.drawable.list);

        grid = (ImageView) findViewById(R.id.gridLayout);
        grid.setImageResource(R.drawable.grid);

        filterButton = (Button) findViewById(R.id.filter);

        JSONObject pokemonData = read_json();

        ArrayList<String> pokemonNames = getPokemon(pokemonData);

        for(int i = 0; i < pokemonNames.size(); i++) {
            Pokemon pokemon = new Pokemon(pokemonNames.get(i), obj);
            pokemonList.add(pokemon);
        }

        mRecyclerView = findViewById(R.id.pokemonList);
        gridRecyclerView = findViewById(R.id.pokemonList);
        //mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        gridLayoutManager = new GridLayoutManager(this, 3);


        mAdapter = new PokemonAdapter(pokemonList, this);
        gridAdapter = new PokemonGridAdapter(pokemonList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnPokemonClickListener(new PokemonAdapter.OnPokemonClickListener() {
            @Override
            public void onPokemonClick(int position) {
                Intent pokemonDisplay = new Intent(getBaseContext(), PokemonDisplay.class);
                pokemonDisplay.putExtra("pokemonObject", position);
                startActivity(pokemonDisplay);
            }
        });

        gridAdapter.setOnPokemonClickListener(new PokemonGridAdapter.OnPokemonClickListener() {
            @Override
            public void onPokemonClick(int position) {
                Intent pokemonDisplay = new Intent(getBaseContext(), PokemonDisplay.class);
                pokemonDisplay.putExtra("pokemonObject", position);
                startActivity(pokemonDisplay);
            }
        });

        list.setOnClickListener(this);
        grid.setOnClickListener(this);
        filterButton.setOnClickListener(this);
        beginSearch();

    }

    public void beginSearch(){

        search = (SearchView) findViewById(R.id.search);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if(view == 0) {
                    mAdapter.getFilter().filter(newText);
                    return false;
                }
                else {
                    gridAdapter.getFilter().filter(newText);
                    return false;
                }
            }
        });
    }

    @Override
    public void onClick(View v){

        switch (v.getId()){

            case R.id.listLayout:
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setAdapter(mAdapter);
                view = 0;
                beginSearch();
                break;

            case R.id.gridLayout:
                mRecyclerView.setLayoutManager(gridLayoutManager);
                mRecyclerView.setAdapter(gridAdapter);
                view = 1;
                beginSearch();
                break;

            case R.id.filter:
                openDialog();
                break;

            default:
                break;
        }

    }

    public void openDialog(){
        FilterDialog filterDialog = new FilterDialog();
        filterDialog.show(getSupportFragmentManager(), "Filter dialog");
    }

    public JSONObject read_json(){
        String json;

        try{
            InputStream is = getAssets().open("pokeData.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");

            obj = new JSONObject(json);

        } catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static ArrayList<String> getPokemon(JSONObject obj){
        ArrayList<String> pokemon = new ArrayList<>();
        Iterator pokemonNames = obj.keys();
        while (pokemonNames.hasNext()){
            String name = pokemonNames.next().toString();
            pokemon.add(name);

        }
        return pokemon;
    }

    @Override
    public void applyTypes(HashMap<String, Boolean> switchList, ArrayList<String> filters) {

        String apply = "";
        for(HashMap.Entry element : switchList.entrySet()){
            if((boolean) element.getValue()) {
                apply += element.getKey().toString();
            }
        }

        if (apply.equals("")){
            apply += "bugdarkdragonelectricfairyfightingfireflyingghostgrassgroundicenormalpoisonpsychicrocksteelwater";
        }

        for(int i = 0; i < filters.size(); i++){

            if(filters.get(i).length() == 1){
                apply += "00" + filters.get(i);
            }

            else if (filters.get(i).length() == 2){
                apply += "0" + filters.get(i);
            }

            else{
                apply += filters.get(i);
            }
        }
        PokemonAdapter.filter = true;

        if(view == 0) {
            mAdapter.getFilter().filter(apply);
        }
        else{
            gridAdapter.getFilter().filter(apply);
        }

    }

    @Override
    public void clear(){
        if(view == 0) {
            mAdapter.getFilter().filter("");
        }
        else{
            gridAdapter.getFilter().filter("");
        }
    }

}