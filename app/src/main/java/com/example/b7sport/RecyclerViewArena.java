package com.example.b7sport;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewArena extends AppCompatActivity {
    private RecyclerView mList;

    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    static List<Arena> groundList;
    private RecyclerView.Adapter adapter;
    //    private ArenaAdapter adapter;
    //https://www.beer-sheva.muni.il/OpenData/Lists/Packages/CustomDispForm.aspx?ID=149
    private String url = "https://br7ckan.blob.core.windows.net/ckanstorage-prod/resources/58f26a74-af55-4823-81d8-17715883acc6/sport.json?sr=b&sp=r&sig=RdGFpINN%2Bn%2B/xsBsRM%2BiM9DcGw5XQ3HFyRw3jgJDi/E%3D&sv=2017-04-17&se=2020-04-19T18%3A16%3A16Z";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_arena);
        mList = findViewById(R.id.main_list);

        groundList = new ArrayList<>();
//        adapter = new ArenaAdapter(getApplicationContext(),groundList);
        adapter = new ArenaAdapter(this ,groundList);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());

        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        mList.addItemDecoration(dividerItemDecoration);
        mList.setAdapter(adapter);
        getData();


    }
    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        Arena arena = new Arena(i) ;
                        arena.setName(jsonObject.getString("Name"));
                        arena.setType(jsonObject.getString("Type"));
                        arena.setStreet(jsonObject.getString("street"));
                        arena.setNeighbor(jsonObject.getString("neighborho"));
                        arena.setHousenumber(jsonObject.getDouble("HouseNumbe"));
                        arena.setLighing(jsonObject.getString("lighting"));
                        arena.setSport_type(jsonObject.getString("SportType"));
                        arena.setLat(jsonObject.getDouble("lat"));
                        arena.setLon(jsonObject.getDouble("lon"));
                        arena.setActivity(jsonObject.getString("Activity"));
                        arena.setId(i);

                        groundList.add(arena);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                progressDialog.dismiss();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }


}

