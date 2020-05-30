package com.example.pokedex;

public class PokemonItem {
    private String mImageResource;
    private String mpokemonName;
    private String mpokemonInfo;

    public PokemonItem(String imageResource, String pokemonName, String pokemonInfo){
        mImageResource = imageResource;
        mpokemonName = pokemonName;
        mpokemonInfo = pokemonInfo;
    }

    public String getImageResource(){
        return mImageResource;
    }

    public String getPokemonName(){
        return mpokemonName;
    }

    public String getPokemonInfo(){
        return mpokemonInfo;
    }

}
