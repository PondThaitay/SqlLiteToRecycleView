package com.wisdomlanna.sqlliterecycleview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private Button btnAdd;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private SqlLiteFavoritePlaces db;
    private List<FavoritePlacesModel> list;
    private static final String USER_ID = "002";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new SqlLiteFavoritePlaces(this);

        /*db.addFavoritePlaces(new FavoritePlacesModel("001", "CentralFestival Chiangmai"
                , "Fa Ham, Chiang Mai, Thailand", "18.1", "18.1.1"));
        db.addFavoritePlaces(new FavoritePlacesModel("001", "Central Department Store (Chiang Mai Branch)"
                , "Su Thep, Chiang Mai, Thailand", "18.2", "18.2.2"));
        db.addFavoritePlaces(new FavoritePlacesModel("001", "Central Hill Place"
                , "ตำบล สุเทพ, เชียงใหม่, ประเทศไทย", "18.3", "18.3.3"));*/

        list = db.getAllBooks(USER_ID);
        Log.i("Result", "size : " + String.valueOf(list.size()));

        /*int s = list.size();
        for(int counter = 0; counter < s; counter++){
            db.deleteBook(list.get(counter));
        }*/

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.addFavoritePlaces(new FavoritePlacesModel("002", "MAYA Lifestyle Shopping Center"
                        , "Chang Phueak, Chiang Mai, Thailand", "18.4", "18.4.4"));
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.nearby_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()
                , LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new ListDataPlacesAdapter());
    }

    class ViewHolderFrom extends RecyclerView.ViewHolder {

        private TextView tvName;
        private TextView tvAddress;
        View parentView;

        public ViewHolderFrom(final View itemView) {
            super(itemView);
            this.parentView = itemView;
            tvName = (TextView) itemView.findViewById(R.id.tvNameList);
            tvAddress = (TextView) itemView.findViewById(R.id.tvAddress);
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
            result.replaceAll("\\[|\\]", "");
            String path[] = result.split("/");
            final String userId = path[1];
            final String name = path[2];
            final String address = path[3];
            final String lat = path[4];
            final String lng = path[5];

            progressBar.setVisibility(View.INVISIBLE);
            viewHolderFrom.tvName.setText(name);
            viewHolderFrom.tvAddress.setText(address);

            viewHolderFrom.parentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "userId :" + userId + "\n"
                            + "name :" + name + "\n" + "address :" + address + "\n"
                            + "lat :" + lat + "\t" + "long :" + lng, Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }
}
