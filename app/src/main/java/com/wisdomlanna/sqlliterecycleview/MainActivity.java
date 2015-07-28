package com.wisdomlanna.sqlliterecycleview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private Button btnAdd;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private SqlLiteFavoritePlaces db;
    private List<FavoritePlacesModel> list;
    private static final String USER_ID = "002";
    private int lastPosition = -1;
    private int s;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new SqlLiteFavoritePlaces(this);

       /* db.addFavoritePlaces(new FavoritePlacesModel("001", "CentralFestival Chiangmai"
                , "Fa Ham, Chiang Mai, Thailand"
                , "http://maps.gstatic.com/mapfiles/place_api/icons/lodging-71.png", "18.1", "18.1.1"));
        db.addFavoritePlaces(new FavoritePlacesModel("001", "Central Department Store (Chiang Mai Branch)"
                , "Su Thep, Chiang Mai, Thailand"
                , "http://maps.gstatic.com/mapfiles/place_api/icons/lodging-71.png", "18.2", "18.2.2"));
        db.addFavoritePlaces(new FavoritePlacesModel("001", "Central Hill Place"
                , "ตำบล สุเทพ, เชียงใหม่, ประเทศไทย"
                , "http://maps.gstatic.com/mapfiles/place_api/icons/lodging-71.png", "18.3", "18.3.3"));*/

        list = db.getAllBooks(USER_ID);
        Log.i("Result", "size : " + String.valueOf(list.size()));
        s = list.size();
        for (int counter = 0; counter < s; counter++) {
            //db.deleteBook(list.get(counter));
            String result = String.valueOf(list.get(counter));
            Log.i("Result", "result : " + result);
            String[] split = result.split(">");
            String id = split[0];
            String userId = split[1];
            String name = split[2];
            String address = split[3];
            String pathImage = split[4];
            String lat = split[5];
            String lng = split[6];
            Log.i("Result", "----------" + "\n" + "id :" + id + "\n" + "userId :" + userId + "\n" + "name :" + name
                    + "\n" + "address :" + address + "\n" + "pathImage :" + pathImage
                    + "\n" + "lat :" + lat + "\t" + "lng :" + lng);
        }

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.addFavoritePlaces(new FavoritePlacesModel("002", "MAYA Lifestyle Shopping Center"
                        , "Chang Phueak, Chiang Mai, Thailand"
                        , "http://maps.gstatic.com/mapfiles/place_api/icons/lodging-71.png", "18.4", "18.4.4"));
                //recyclerView.getAdapter().notifyDataSetChanged();
                list = db.getAllBooks(USER_ID);
                Log.i("Result", "size : " + String.valueOf(list.size()));
                s = list.size();
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.nearby_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()
                , LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new ListDataPlacesAdapter());
       /* recyclerView.setItemAnimator(new FadeInAnimator());
        final MyAdapter adapter = new MyAdapter(this, list);
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(adapter);
        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(alphaAdapter);
        scaleAdapter.setFirstOnly(false);
        recyclerView.setAdapter(scaleAdapter);*/
    }

    class ViewHolderFrom extends RecyclerView.ViewHolder {

        private TextView tvName;
        private TextView tvAddress;
        private ImageView imIcon;
        View parentView;
        FrameLayout container;

        public ViewHolderFrom(final View itemView) {
            super(itemView);
            this.parentView = itemView;
            tvName = (TextView) itemView.findViewById(R.id.tvNameList);
            tvAddress = (TextView) itemView.findViewById(R.id.tvAddress);
            imIcon = (ImageView) itemView.findViewById(R.id.imIcon);
            container = (FrameLayout) itemView.findViewById(R.id.container);
        }
    }

    class ListDataPlacesAdapter extends RecyclerView.Adapter<ViewHolderFrom> {

        @Override
        public ViewHolderFrom onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new ViewHolderFrom(LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_list, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(final ViewHolderFrom viewHolderFrom, final int i) {
            String result = String.valueOf(list.get(i));
            String[] split = result.split(">");
            final String id = split[0];
            final String userId = split[1];
            final String name = split[2];
            final String address = split[3];
            final String pathImage = split[4];
            final String lat = split[5];
            final String lng = split[6];

            progressBar.setVisibility(View.INVISIBLE);
            viewHolderFrom.tvName.setText(name);
            viewHolderFrom.tvAddress.setText(address);

            if (pathImage.length() > 0) {
                Picasso.with(getApplicationContext())
                        .load(pathImage)
                        .resize(50, 50)
                        .centerCrop()
                        .into(viewHolderFrom.imIcon);
            } else {
                Picasso.with(getApplicationContext())
                        .load(R.drawable.pin_location_gray)
                        .resize(50, 50)
                        .centerCrop()
                        .into(viewHolderFrom.imIcon);
            }

            viewHolderFrom.parentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "id :" + id + "\n"
                            + "userId :" + userId + "\n" + "name :" + name + "\n"
                            + "address :" + address + "\n" + "lat :" + lat + "\t" + "long :" + lng
                            , Toast.LENGTH_SHORT).show();
                }
            });

            int duration = 1000;
            setAnimation(viewHolderFrom.container, i, duration);
        }

        @Override
        public int getItemCount() {
            return s;
        }
    }

    private void setAnimation(View viewToAnimate, int position, int duration) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext()
                    , R.anim.anim_list);
            animation.setDuration(duration);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
}
