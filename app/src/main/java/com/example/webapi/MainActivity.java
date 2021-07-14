package com.example.webapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button botoleer;
    TextView txtdatos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.txtdatos = findViewById(R.id.txtdatos);
        this.botoleer = findViewById(R.id.botonleerservicio);

    }
    public void leerServicio(View view) {
        try {
            String url = "https://webapidepartamentosxamarin.azurewebsites.net/api/Departamentos";
            new HttpAsyncTask().execute(url);
        } catch (Exception e) {
// manage exceptions
            txtdatos.setText(e.getMessage());
        }
    }
    public String recuperarContenido(String url) {
        HttpClient httpclient = new DefaultHttpClient();
        String resultado = null;
        HttpGet httpget = new HttpGet(url);
        HttpResponse respuesta = null;
        InputStream stream = null;
        try {
            respuesta = httpclient.execute(httpget);
            HttpEntity entity = respuesta.getEntity();

            if (entity != null) {
                stream = entity.getContent();
                resultado = convertirInputToString(stream);
            }
        } catch (Exception e) {
            txtdatos.setText(e.getMessage());
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (Exception e) {
                txtdatos.setText(e.getMessage());
            }
        }
        return resultado;
    }
    private String convertirInputToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String resultado = "";
        while ((line = bufferedReader.readLine()) != null)
            resultado += line;
        inputStream.close();
        return resultado;
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return recuperarContenido(urls[0]);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Datos recibidos!", Toast.LENGTH_LONG).show();

            try {
                JSONArray jsonarray = new JSONArray(result);
                List<Departamento> lista = convertirJsonDepartamentos(jsonarray);
                String datos = "";
                for (Departamento d : lista) {
                    datos += d.toString() + "\n";
                }
                txtdatos.setText(datos);
            } catch (JSONException e) {
                txtdatos.setText(e.getMessage());
            }

        }
    }

    public List<Departamento> convertirJsonDepartamentos(JSONArray jsonarray) throws JSONException {
        List<Departamento> lista = new ArrayList<>();
        for (int i = 0; i < jsonarray.length(); i++) {
            Departamento dept = new Departamento();
            String num, nom, loc;
            num = jsonarray.getJSONObject(i).optString("DEPT_NO").toString();
            nom = jsonarray.getJSONObject(i).optString("DNOMBRE").toString();
            loc = jsonarray.getJSONObject(i).optString("LOC").toString();
            dept.setNumero(num);
            dept.setNombre(nom);
            dept.setLocalidad(loc);
            lista.add(dept);
        }
        return lista;
    }


}