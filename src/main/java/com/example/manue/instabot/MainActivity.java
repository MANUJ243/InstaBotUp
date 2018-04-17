package com.example.manue.instabot;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    EditText inputValue = null;
    CircleImageView iconoPerfil;
    TextView nombrePerfil;
    View header;
    Integer doubledValue = 0;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        header = navigationView.getHeaderView(0);

        iconoPerfil = header.findViewById(R.id.profile_image);
        iconoPerfil.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Uri uri = Uri.parse("http://instagram.com/manummore/");
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.instagram.android");

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://instagram.com/manummore/")));
                }
            }
        });

        nombrePerfil = header.findViewById(R.id.profile_name);
        nombrePerfil.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Uri uri = Uri.parse("http://instagram.com/manummore/");
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.instagram.android");

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://instagram.com/manummore/")));
                }
            }
        });

        inputValue = (EditText) findViewById(R.id.hastag1);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void metodoLiker(View view) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.1.39:12839/PruebaServer2/PruebaServlet",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error--> ",error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String, String> parametros = new HashMap<>();
                parametros.put("nombre",inputValue.getText().toString());
                return parametros;
            }
        };

        requestQueue.add(stringRequest);









        /*new Thread(new Runnable() {
            public void run() {

                try {
                    URL url = new URL("http://localhost:8087/MyServletProject/DoubleMeServlet");
                    URLConnection connection = url.openConnection();

                    String inputString = inputValue.getText().toString();
                    inputString = URLEncoder.encode(inputString, "UTF-8");

                    Log.d("inputString", inputString);

                    connection.setDoOutput(true);
                    OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
                    out.write(inputString);
                    out.close();

                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    String returnString = "";
                    doubledValue = 0;

                    while ((returnString = in.readLine()) != null) {
                        doubledValue = Integer.parseInt(returnString);
                    }
                    in.close();

                    runOnUiThread(new Runnable() {
                        public void run() {
                            inputValue.setText(doubledValue.toString());
                        }
                    });
                } catch (Exception e) {
                    Log.d("Exception", e.toString());
                }
            }
        }).start();*/
    }

}
