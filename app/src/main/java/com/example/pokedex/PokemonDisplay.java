package com.example.pokedex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class PokemonDisplay extends AppCompatActivity {

    ImageView pokemonIndImage;
    TextView pokemonIndName;
    ImageView pokemonIndType1;
    ImageView pokemonIndType2;
    TextView pokemonSpecies;
    TextView pokemonAttack;
    TextView pokemonDefense;
    TextView pokemonSpecAtk;
    TextView pokemonSpecDef;
    TextView pokemonHP;
    TextView pokemonSpeed;
    TextView pokemonTotal;
    TextView pokemonFlavorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_display);

        Intent mIntent = getIntent();

        int position = mIntent.getIntExtra("pokemonObject", 0);

        Pokemon individualPokemon = PokemonAdapter.mPokemonItems.get(position);

        pokemonIndImage = (ImageView) findViewById(R.id.pokemonIndImage);
        pokemonIndName = (TextView) findViewById(R.id.pokemonIndName);
        pokemonIndType1 = (ImageView) findViewById(R.id.pokemonIndType1);
        pokemonIndType2 = (ImageView) findViewById(R.id.pokemonIndType2);
        pokemonSpecies = (TextView) findViewById(R.id.pokemonSpecies);
        pokemonAttack = (TextView) findViewById(R.id.pokemonAttack);
        pokemonDefense = (TextView) findViewById(R.id.pokemonDefense);
        pokemonSpecAtk = (TextView) findViewById(R.id.pokemonSpecAtk);
        pokemonSpecDef = (TextView) findViewById(R.id.pokemonSpecDef);
        pokemonHP = (TextView) findViewById(R.id.pokemonHP);
        pokemonSpeed = (TextView) findViewById(R.id.pokemonSpeed);
        pokemonTotal = (TextView) findViewById(R.id.pokemonTotal);
        pokemonFlavorText = (TextView) findViewById(R.id.pokemonFlavorText);

        String imageURL = individualPokemon.getPictureLink();
        Glide.with(this.pokemonIndImage.getContext())
                .load(imageURL)
                .into(this.pokemonIndImage);

        pokemonIndName.setText(individualPokemon.getName());

        if(individualPokemon.getType().size() == 1){
            int id = this.getResources().getIdentifier(individualPokemon.getType().get(0).toLowerCase(), "drawable", this.getPackageName());
            pokemonIndType1.setImageResource(id);
        }

        else{
                int id0 = this.getResources().getIdentifier(individualPokemon.getType().get(0).toLowerCase(), "drawable", this.getPackageName());
                pokemonIndType1.setImageResource(id0);

                int id1 = this.getResources().getIdentifier(individualPokemon.getType().get(1).toLowerCase(), "drawable", this.getPackageName());
                pokemonIndType2.setImageResource(id1);
        }

        pokemonSpecies.setText("Species: " + individualPokemon.getSpecies());
        pokemonAttack.setText("Attack: " + individualPokemon.getAttackRating());
        pokemonDefense.setText("Defense: " + individualPokemon.getDefenseRating());
        pokemonSpecAtk.setText("Special Attack: " + individualPokemon.getSpAtk());
        pokemonSpecDef.setText("Special Defense: " + individualPokemon.getSpDef());
        pokemonHP.setText("HP: " + individualPokemon.getHp());
        pokemonSpeed.setText("Speed: " + individualPokemon.getSpeed());
        pokemonTotal.setText("Total: " + individualPokemon.getTotal());
        pokemonFlavorText.setText(individualPokemon.getFlavorText());

    }
}
