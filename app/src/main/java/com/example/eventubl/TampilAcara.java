package com.example.eventubl;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eventubl.app.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class TampilAcara extends AppCompatActivity implements ListView.OnItemClickListener{
    private ListView listView;
    SharedPreferences sharedpreferences;
    String nip;
    private String JSON_STRING;
    Boolean session = false;
    public static final String my_shared_preferences = "my_shared_preferences";
    public static final String session_status = "session_status";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_acara);
        listView = findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        getJSON();

        nip = getIntent().getStringExtra(Server.TAG_NIP);

        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean(session_status, false);
        nip = sharedpreferences.getString(Server.TAG_NIP, null);
    }
    private void showPemesanan(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Server.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String kd = jo.getString(Server.TAG_KDACARA);
                String nm = jo.getString(Server.TAG_NMACARA);

                HashMap<String,String> pemesanans = new HashMap<>();
                pemesanans.put(Server.TAG_KDACARA,kd);
                pemesanans.put(Server.TAG_NMACARA,nm);
                list.add(pemesanans);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(TampilAcara.this, list, R.layout.list_item,
                new String[]{Server.TAG_KDACARA,Server.TAG_NMACARA},
                new int[]{R.id.kd, R.id.nm});

        listView.setAdapter(adapter);
    }
    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilAcara.this,"Mengambil Data","Mohon Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showPemesanan();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Server.URL_GET_ALL);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(TampilAcara.this,TampilDetilAcara.class);
        HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
        String empId = map.get(Server.TAG_KDACARA).toString();
        intent.putExtra(Server.ACR_KD,empId);
        intent.putExtra(Server.TAG_NIP, nip);
       startActivity(intent);
    }

    public void keluar(View view) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean(LoginActivity.session_status, false);
        editor.commit();
        Intent intent = new Intent(TampilAcara.this, LoginActivity.class);
        finish();
        startActivity(intent);
    }
}