package com.example.pokedex;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Pokemon {

    private String name;
    private int pokemonNumber;
    private int attackRating;
    private int defenseRating;
    private String flavorText;
    private int hp;
    private int spAtk;
    private int spDef;
    private String species;
    private int speed;
    private int total;
    private ArrayList<String> type;
    private String pictureLink;

    public Pokemon(String name, JSONObject obj){

        try {
            JSONObject extractPokemonData = obj.getJSONObject(name);
            this.name = name;
            pokemonNumber = extractPokemonData.getInt("#");
            attackRating = extractPokemonData.getInt("Attack");
            defenseRating = extractPokemonData.getInt("Defense");
            flavorText = extractPokemonData.getString("FlavorText");
            hp = extractPokemonData.getInt("HP");
            spAtk = extractPokemonData.getInt("Sp. Atk");
            spDef = extractPokemonData.getInt("Sp. Def");
            species = extractPokemonData.getString("Species");
            speed = extractPokemonData.getInt("Speed");
            total = extractPokemonData.getInt("Total");
            JSONArray typeArr = extractPokemonData.getJSONArray("Type");
            type = new ArrayList<String>();

            for(int i = 0; i < typeArr.length(); i++){
                type.add(typeArr.getString(i));
            }

            pictureLink = "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/"
                    + extractPokemonData.getString("#") + ".png";
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getName(){return name;}
    public int getPokemonNumber(){return pokemonNumber;}
    public int getAttackRating(){return attackRating;}
    public int getDefenseRating(){return defenseRating;}
    public String getFlavorText(){return flavorText;}
    public int getHp(){return hp;}
    public int getSpAtk(){return spAtk; }
    public int getSpDef(){ return spDef;}
    public String getSpecies() {
        return species;
    }
    public int getSpeed() { return speed; }
    public int getTotal() {
        return total;
    }
    public ArrayList<String> getType() {
        return type;
    }
    public String getPictureLink() {
        return pictureLink;
    }
}
