package com.wisdomlanna.sqlliterecycleview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by suraphol on 7/28/15 AD.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private Context context;
    private List<FavoritePlacesModel> mDataSet;

    public MyAdapter(Context context, List<FavoritePlacesModel> dataSet) {
        this.context = context;
        this.mDataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String result = String.valueOf(mDataSet.get(position));
        String[] split = result.split(">");
        final String id = split[0];
        final String userId = split[1];
        final String name = split[2];
        final String address = split[3];
        final String pathImage = split[4];
        final String lat = split[5];
        final String lng = split[6];

        holder.tvName.setText(name);
        holder.tvAddress.setText(address);

        if (pathImage.length() > 0) {
            Picasso.with(context)
                    .load(pathImage)
                    .resize(50, 50)
                    .centerCrop()
                    .into(holder.imIcon);
        } else {
            Picasso.with(context)
                    .load(R.drawable.pin_location_gray)
                    .resize(50, 50)
                    .centerCrop()
                    .into(holder.imIcon);
        }

        holder.parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "id :" + id + "\n"
                        + "userId :" + userId + "\n" + "name :" + name + "\n"
                        + "address :" + address + "\n" + "lat :" + lat + "\t" + "long :" + lng
                        , Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void remove(int position) {
        mDataSet.remove(position);
        notifyItemRemoved(position);
    }

    public void add(FavoritePlacesModel text, int position) {
        mDataSet.add(position, text);
        notifyItemInserted(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvAddress;
        private ImageView imIcon;
        View parentView;
        //FrameLayout container;

        public ViewHolder(View itemView) {
            super(itemView);
            this.parentView = itemView;
            tvName = (TextView) itemView.findViewById(R.id.tvNameList);
            tvAddress = (TextView) itemView.findViewById(R.id.tvAddress);
            imIcon = (ImageView) itemView.findViewById(R.id.imIcon);
            //container = (FrameLayout) itemView.findViewById(R.id.container);
        }
    }
}
