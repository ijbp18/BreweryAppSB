package com.home.frvajoao.breweryappsb.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.home.frvajoao.breweryappsb.DetailBreweryActivity.BreweryDetailActivity;
import com.home.frvajoao.breweryappsb.Interface.IOnRecyclerViewClickListener;
import com.home.frvajoao.breweryappsb.Model.Brewery;
import com.home.frvajoao.breweryappsb.Model.EventBus.BreweryDetailEvent;
import com.home.frvajoao.breweryappsb.R;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyBrewerydapter extends RecyclerView.Adapter<MyBrewerydapter.MyViewHolder> {

    Context context;
    List<Brewery> breweryList;

    public MyBrewerydapter(Context context, List<Brewery> breweryList) {
        this.context = context;
        this.breweryList = breweryList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView  = LayoutInflater.from(context)
                .inflate(R.layout.layout_brewery, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Picasso.get().load(breweryList.get(position).getImage()).into(holder.img_brewery);

        holder.txt_brewery_name.setText(new StringBuilder(breweryList.get(position).getName()));
        holder.txt_brewery_address.setText(new StringBuilder(breweryList.get(position).getStreet()));
        holder.txt_brewery_city.setText(new StringBuilder(breweryList.get(position).getCity() != null ? breweryList.get(position).getCity(): context.getString(R.string.no_city))
                .append(" - ")
                .append(breweryList.get(position).getState()!= null ?breweryList.get(position).getState(): context.getString(R.string.no_state)));

        holder.setListener((view, position1) -> {

            context.startActivity(new Intent(context, BreweryDetailActivity.class));
            EventBus.getDefault().postSticky(new BreweryDetailEvent(true, breweryList.get(position)));

        });

    }

    @Override
    public int getItemCount() {
        return breweryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.txt_brewery_name)
        TextView txt_brewery_name;
        @BindView(R.id.txt_brewery_address)
        TextView txt_brewery_address;
        @BindView(R.id.txt_brewery_city)
        TextView txt_brewery_city;
        @BindView(R.id.breweryList)
        ImageView img_brewery;

        IOnRecyclerViewClickListener listener;

        public void setListener(IOnRecyclerViewClickListener listener) {
            this.listener = listener;
        }

        Unbinder unbinder;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }
    }

}
