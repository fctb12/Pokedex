package com.example.pokedex;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> implements Filterable{

    public static ArrayList<Pokemon> mPokemonItems;
    private ArrayList<Pokemon> pokemonItemsFull;
    public static boolean filter = false;
    Context ctx;
    private OnPokemonClickListener mlistener;

    public interface OnPokemonClickListener{
        void onPokemonClick(int position);
    }

    public void setOnPokemonClickListener(OnPokemonClickListener listener){
        mlistener = listener;
    }

    public static class PokemonViewHolder extends RecyclerView.ViewHolder {

        public ImageView pokemonPic;
        public TextView pokemonName;
        public ImageView pokemonType1;
        public ImageView pokemonType2;

        public PokemonViewHolder(@NonNull View itemView, final OnPokemonClickListener listener) {
            super(itemView);
            pokemonPic = itemView.findViewById(R.id.pokemonPic);
            pokemonName = itemView.findViewById(R.id.pokemonName);
            pokemonType1 = itemView.findViewById(R.id.type1);
            pokemonType2 = itemView.findViewById(R.id.type2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onPokemonClick(position);
                        }
                    }
                }
            });
        }
    }

    public PokemonAdapter(ArrayList<Pokemon> pokemonItems, Context context){
        mPokemonItems = pokemonItems;
        pokemonItemsFull = new ArrayList<>(pokemonItems);
        ctx = context;
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokemon_item, parent, false);
        PokemonViewHolder pvh = new PokemonViewHolder(v, mlistener);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {

        Pokemon currentItem = mPokemonItems.get(position);
        String imageURL = currentItem.getPictureLink();
        Glide.with(holder.pokemonPic.getContext())
                .load(imageURL)
                .into(holder.pokemonPic);

        holder.pokemonName.setText(currentItem.getName());
        String name = "";
        for(int i = 0; i < currentItem.getType().size(); i++){
            if(i == 0) {
                int id = ctx.getResources().getIdentifier(currentItem.getType().get(i).toLowerCase(), "drawable", ctx.getPackageName());
                holder.pokemonType1.setImageResource(id);
            }
            else if(i == 1) {
                int id = ctx.getResources().getIdentifier(currentItem.getType().get(i).toLowerCase(), "drawable", ctx.getPackageName());
                holder.pokemonType2.setImageResource(id);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mPokemonItems.size();
    }

    @Override
    public Filter getFilter() {
        return pokemonFilter;
    }

    private Filter pokemonFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Pokemon> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                filteredList.addAll(pokemonItemsFull);
            }
            else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                int minAtk;
                int minDef;
                int minHP;
                for (Pokemon item : pokemonItemsFull){

                    if (filter){
                        minAtk = Integer.parseInt(filterPattern.substring(filterPattern.length()-9,filterPattern.length()-6));
                        minDef = Integer.parseInt(filterPattern.substring(filterPattern.length()-6,filterPattern.length()-3));
                        minHP = Integer.parseInt(filterPattern.substring(filterPattern.length()-3));
                    }

                    else {
                        minAtk = 0;
                        minDef = 0;
                        minHP = 0;
                    }
                    System.out.println(filter + " " + item.getAttackRating() + " " + minAtk);

                    if(item.getType().size() == 1) {
                        if (item.getName().toLowerCase().startsWith(filterPattern) || filterPattern.contains(item.getType().get(0).toLowerCase()) && item.getAttackRating() >= minAtk && item.getDefenseRating() >= minDef && item.getHp() >= minHP){
                            filteredList.add(item);
                        }
                    }
                    else{
                        if (item.getName().toLowerCase().startsWith(filterPattern) || (filterPattern.contains(item.getType().get(0).toLowerCase()) || filterPattern.contains(item.getType().get(1).toLowerCase())) && item.getAttackRating() >= minAtk && item.getDefenseRating() >= minDef && item.getHp() >= minHP) {
                            filteredList.add(item);
                        }
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            filter = false;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mPokemonItems.clear();
            mPokemonItems.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };
}
