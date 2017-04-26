package josue.climaapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.os.AsyncTaskCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import josue.climaapp.modelos.Ciudad;
import josue.climaapp.modelos.ListadoCiudades;

/**
 * Created by josue on 4/25/17.
 */

public class DetalleClima extends Fragment {

    Ciudad city;
    int INDICE;
    public String DATOS_API = "";
    ProgressDialog dialogo_espere;

    TextView txt_nombre;
    TextView txt_descripcion;
    TextView txt_temperatura;
    TextView txt_humedad;
    TextView txt_velocidad;

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        dialogo_espere = new ProgressDialog(getContext());
        dialogo_espere.setMessage("Loading...");

        Bundle bundle = this.getArguments();
        if (bundle != null){
            int indice = bundle.getInt("indice");
            ListadoCiudades ciudades = ListadoCiudades.getInstancia();
            this.city = ciudades.getCiudad(indice);
            cargarTodosDatos();
        }
    }

    private void cargarTodosDatos(){
        String url= "http://api.openweathermap.org/data/2.5/weather?id="+this.city.getId()
                +"&appid=107dfba9fe9b744afcb3b76565658436";

        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        setDatos(response);
                        Log.i("CLIMA", response);
                        dialogo_espere.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("CLIMA", error.getMessage());
                dialogo_espere.dismiss();
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
        dialogo_espere.show();
        //new TareaWSciudad(dialogo_espere).execute(url);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View componentes = inflater.inflate(R.layout.detalle_clima, container, false);



        txt_nombre = (TextView) componentes.findViewById(R.id.detalle_nombre_ciudad);
        txt_descripcion = (TextView) componentes.findViewById(R.id.detalle_descripcion_clima);
        txt_temperatura = (TextView) componentes.findViewById(R.id.detalle_temperatura);
        txt_humedad = (TextView) componentes.findViewById(R.id.detalle_humedad);
        txt_velocidad = (TextView) componentes.findViewById(R.id.detalle_viento);

        txt_nombre.setText(this.city.getNombre());
        txt_descripcion.setText(this.city.getDescripcion());
        txt_temperatura.setText(this.city.getTemperatura());
        txt_humedad.setText(this.city.getHumedad());
        txt_velocidad.setText(this.city.getVelocidad_viento());

        return componentes;
    }


    private class TareaWSciudad extends AsyncTask<String, Integer, Boolean>{

        ProgressDialog mensaje;

        public TareaWSciudad(ProgressDialog mensaje){
            this.mensaje = mensaje;
        }

        @Override
        protected void onPreExecute(){
            mensaje.show();
        }

        @Override
        protected void onPostExecute(Boolean result){
            mensaje.dismiss();
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            boolean result = true;

            Log.i("CLIMA", strings[0]);

            RequestQueue queue = Volley.newRequestQueue(getContext());
            StringRequest stringRequest = new StringRequest(Request.Method.GET, strings[0],
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.
                            setDatos(response);
                            Log.i("CLIMA", response);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(),error.getLocalizedMessage(), Toast.LENGTH_LONG);
                    Log.e("CLIMA", error.getMessage());
                }
            });
// Add the request to the RequestQueue.
            queue.add(stringRequest);
            return result;
        }
    }

    private void setDatos(String response) {
        try {
            JSONObject general = new JSONObject(response);
            JSONArray weatherArray  = general.getJSONArray("weather");
            JSONObject weather = (JSONObject)weatherArray.get(0);
            JSONObject main = general.getJSONObject("main");
            JSONObject wind = general.getJSONObject("wind");

            this.city.setNombre(general.getString("name"));
            this.city.setDescripcion(weather.getString("description"));
            this.city.setHumedad(main.getString("humidity"));
            this.city.setTemperatura(main.getString("temp"));
            this.city.setVelocidad_viento(wind.getString("speed"));

            txt_nombre.setText(this.city.getNombre());
            txt_descripcion.setText(this.city.getDescripcion());
            txt_temperatura.setText(this.city.getTemperatura());
            txt_humedad.setText(this.city.getHumedad());
            txt_velocidad.setText(this.city.getVelocidad_viento());

        } catch (JSONException e) {
            Log.e("CLIMA", e.getMessage());

        }

    }

}
