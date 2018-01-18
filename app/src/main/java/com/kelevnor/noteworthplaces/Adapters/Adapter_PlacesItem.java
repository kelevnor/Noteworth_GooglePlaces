package com.kelevnor.noteworthplaces.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kelevnor.noteworthplaces.Models.places.Result;
import com.kelevnor.noteworthplaces.R;

import java.util.List;

/**
 * Created by kelevnor on 1/18/18.
 */

public class Adapter_PlacesItem extends RecyclerView.Adapter<Adapter_PlacesItem.ViewHolder> {
    private List<Result> searchList;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;

        public ViewHolder(TextView v) {
            super(v);
            mTextView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public Adapter_PlacesItem(List<Result> searchList) {
        this.searchList = searchList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public Adapter_PlacesItem.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.textview, parent, false);


        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(searchList.get(position).getName());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return searchList.size();
    }
}