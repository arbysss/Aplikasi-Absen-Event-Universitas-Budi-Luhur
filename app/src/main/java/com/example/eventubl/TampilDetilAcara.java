package com.example.eventubl;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.eventubl.app.AppController;
import com.example.eventubl.app.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TampilDetilAcara extends AppCompatActivity {
    private TextView textViewKd;
    private TextView textViewTgl;
    private TextView textViewJam;
    private TextView textViewNm;
    private TextView textViewLokasi;
    private TextView textViewNip;
    private String kd, nip, kdacara,nipdosen;
    SharedPreferences sharedPreferences;
    Boolean session = false;
    public static final String my_shared_preferences = "my_shared_preferences";
    public static final String session_status = "session_status";
    ProgressDialog pDialog;
    Button btn_absen;
    Intent intent;
    int success;
    ConnectivityManager conMgr;
    private String url = Server.URL + "absen.php";
    private static final String TAG = TampilDetilAcara.class.getSimpleName();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    String tag_json_obj = "json_obj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_detil_acara);
        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
            } else {
                Toast.makeText(getApplicationContext(), "No Internet Connection",
                        Toast.LENGTH_LONG).show();
            }
        }

        Intent intent = getIntent();

        btn_absen = (Button) findViewById(R.id.btn_absen);
        kd = intent.getStringExtra(Server.ACR_KD);
        textViewKd = (TextView) findViewById(R.id.textViewKd);
        textViewTgl = (TextView) findViewById(R.id.textViewTgl);
        textViewJam = (TextView) findViewById(R.id.textViewJam);
        textViewNm = (TextView) findViewById(R.id.textViewNm);
        textViewLokasi = (TextView) findViewById(R.id.textViewLokasi);
        textViewNip = (TextView) findViewById(R.id.textViewNip);
        textViewKd.setText(kd);

        sharedPreferences = getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
        nip = getIntent().getStringExtra(Server.TAG_NIP);
        textViewNip.setText(nip);

        sharedPreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedPreferences.getBoolean(session_status, false);
        nip = sharedPreferences.getString(Server.TAG_NIP, null);

        getPemesanan();

        btn_absen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String kdacara = textViewKd.getText().toString();
                String nipdosen = textViewNip.getText().toString();
                if (conMgr.getActiveNetworkInfo() != null
                        && conMgr.getActiveNetworkInfo().isAvailable()
                        && conMgr.getActiveNetworkInfo().isConnected()) {
                    checkAbsen(kdacara, nipdosen);
                } else {
                    Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getPemesanan() {
        class GetPemesanan extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilDetilAcara.this, "Fetching...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showPemesanan(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Server.URL_GET_ACR, kd);
                return s;
            }
        }
        GetPemesanan ge = new GetPemesanan();
        ge.execute();
    }

    private void showPemesanan(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Server.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            String kd = c.getString(Server.TAG_KDACARA);
            String nm = c.getString(Server.TAG_NMACARA);
            String tgl = c.getString(Server.TAG_ACARATGL);
            String jam = c.getString(Server.TAG_ACARAJAM);
            String lokasi = c.getString(Server.TAG_LOKASI);
            textViewKd.setText(kd);
            textViewNm.setText(nm);
            textViewTgl.setText(tgl);
            textViewJam.setText(jam);
            textViewLokasi.setText(lokasi);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void back(View view) {
        startActivity(new Intent(TampilDetilAcara.this,TampilAcara.class));
    }

    private void checkAbsen(final String kdacara, final String nipdosen) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Absen ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Booking Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Check for error node in json
                    if (success == 1) {

                        Log.e("Successfully Absen!", jObj.toString());

                        Toast.makeText(getApplicationContext(),
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                        textViewKd.setText("");
                        textViewNip.setText("");
                    } else {
                        Toast.makeText(getApplicationContext(),
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Pemesanan Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

                hideDialog();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("kdacara", kdacara);
                params.put("nipdosen", nipdosen);
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}