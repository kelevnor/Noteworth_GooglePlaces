package com.kelevnor.noteworthplaces.Adapters;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kelevnor.noteworthplaces.Models.places.Result;
import com.kelevnor.noteworthplaces.R;

import java.util.List;

/**
 * Created by kelevnor on 1/18/18.
 */

public class Adapter_PlacesItem extends RecyclerView.Adapter<Adapter_PlacesItem.ViewHolder> {
    private List<Result> searchList;
    Typeface fontAwesome, openSansRegular, openSansSemiBold;
    Activity act;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public RelativeLayout layout;
        public TextView textName;
        public TextView textRating;
        public TextView textPrice;
        public TextView textOpenNow;

        public ViewHolder(RelativeLayout v) {
            super(v);
            layout = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public Adapter_PlacesItem(Activity act, List<Result> searchList) {
        this.searchList = searchList;
        this.act = act;
        fontAwesome = Typeface.createFromAsset(act.getAssets(),"fonts/fontawesome-webfont.ttf");
        openSansRegular = Typeface.createFromAsset(act.getAssets(),"fonts/Open_Sans_Regular.ttf");
        openSansSemiBold = Typeface.createFromAsset(act.getAssets(),"fonts/Open_Sans_SemiBold.ttf");
    }

    // Create new views (invoked by the layout manager)
    @Override
    public Adapter_PlacesItem.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        RelativeLayout v = (RelativeLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_search, parent, false);
        ViewHolder vh = new ViewHolder(v);
        vh.textName = v.findViewById(R.id.tv_name);
        vh.textRating = v.findViewById(R.id.tv_rating);
        vh.textPrice = v.findViewById(R.id.tv_price);
        vh.textOpenNow = v.findViewById(R.id.tv_opennow);

        vh.textName.setTypeface(openSansSemiBold);
        vh.textRating.setTypeface(fontAwesome);
        vh.textPrice.setTypeface(openSansSemiBold);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textName.setText(searchList.get(position).getName());

        if(searchList.get(position).getRating()!=null){
            if(searchList.get(position).getRating()<1.0){
                holder.textRating.setText(act.getResources().getString(R.string.fa_half_star)+" "+act.getResources().getString(R.string.fa_empty_star)+" "+act.getResources().getString(R.string.fa_empty_star)+" "+act.getResources().getString(R.string.fa_empty_star)+" "+act.getResources().getString(R.string.fa_empty_star));
            }
            else if(searchList.get(position).getRating()<2.0){
                holder.textRating.setText(act.getResources().getString(R.string.fa_star)+" "+act.getResources().getString(R.string.fa_half_star)+" "+act.getResources().getString(R.string.fa_empty_star)+" "+act.getResources().getString(R.string.fa_empty_star)+" "+act.getResources().getString(R.string.fa_empty_star));
            }
            else if(searchList.get(position).getRating()<3.0){
                holder.textRating.setText(act.getResources().getString(R.string.fa_star)+" "+act.getResources().getString(R.string.fa_star)+" "+act.getResources().getString(R.string.fa_half_star)+" "+act.getResources().getString(R.string.fa_empty_star)+" "+act.getResources().getString(R.string.fa_empty_star));
            }
            else if(searchList.get(position).getRating()<4.0){
                holder.textRating.setText(act.getResources().getString(R.string.fa_star)+" "+act.getResources().getString(R.string.fa_star)+" "+act.getResources().getString(R.string.fa_star)+" "+act.getResources().getString(R.string.fa_half_star)+" "+act.getResources().getString(R.string.fa_empty_star));
            }
            else if(searchList.get(position).getRating()<5.0){
                holder.textRating.setText(act.getResources().getString(R.string.fa_star)+" "+act.getResources().getString(R.string.fa_star)+" "+act.getResources().getString(R.string.fa_star)+" "+act.getResources().getString(R.string.fa_star)+" "+act.getResources().getString(R.string.fa_half_star));
            }

            if(searchList.get(position).getRating()==5.0){
                holder.textRating.setText(act.getResources().getString(R.string.fa_star)+" "+act.getResources().getString(R.string.fa_star)+" "+act.getResources().getString(R.string.fa_star)+" "+act.getResources().getString(R.string.fa_star)+" "+act.getResources().getString(R.string.fa_star));
            }
        }
        else{
            holder.textRating.setText("NA");
        }

        if(searchList.get(position).getOpeningHours().getOpenNow()){
            holder.textOpenNow.setText("OPEN");
            holder.textOpenNow.setTextColor(act.getResources().getColor(R.color.colorGreen));
        }
        else{
            holder.textOpenNow.setText("CLOSED");
            holder.textOpenNow.setTextColor(act.getResources().getColor(R.color.colorPrimary));
        }




    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return searchList.size();
    }
}