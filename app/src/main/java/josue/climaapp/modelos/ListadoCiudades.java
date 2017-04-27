package josue.climaapp.modelos;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

/**
 * Created by josue on 4/25/17.
 */

public class ListadoCiudades {
    private static ListadoCiudades instancia = null;

    private ArrayList<Ciudad> datos = new ArrayList<Ciudad>();
    private Context context;

    private double LATITUD = 0.0;
    private double LONGITUD = 0.0;
    private int CANTIDAD = 10;

    protected ListadoCiudades(Context ctx, double lat, double lon, int cnt){

        this.setLATITUD(lat);
        this.setLONGITUD(lon);
        this.setCANTIDAD(cnt);
        this.context = ctx;
    }

    public static ListadoCiudades getInstancia(Context ctx, double lat, double lon, int cnt){
        if (instancia == null){
            instancia = new ListadoCiudades(ctx, lat, lon, cnt);
            instancia.setLATITUD(lat);
            instancia.setLONGITUD(lon);
            instancia.setCANTIDAD(cnt);
        }
        return  instancia;
    }

    public void loadData(){

        String url= "http://api.openweathermap.org/data/2.5/find?lat="+this.getLATITUD()+"&lon="+this.getLONGITUD()
                +"&cnt="+this.getCANTIDAD()+
                "&appid=107dfba9fe9b744afcb3b76565658436";

        RequestQueue queue = Volley.newRequestQueue(this.context);
        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        JsonObjectRequest request = new JsonObjectRequest(url, new JSONObject(), future, future);
        queue.add(request);

        try {
            JSONObject response = future.get(30, TimeUnit.SECONDS); // this will block
            setData(response);
        } catch (InterruptedException e) {
            Log.e("CLIMA", e.getMessage());
        } catch (ExecutionException e) {
            Log.e("CLIMA", e.getMessage());
        } catch (TimeoutException e) {
            Log.e("CLIMA", e.getMessage());
        }

    }


    public void setData(JSONObject response){
        try {
            //JSONObject principal = new JSONObject(response);
            JSONArray lista = response.getJSONArray("list");
            for (int i=0; i<lista.length(); i++){
                JSONObject ciudad = (JSONObject) lista.get(i);
                JSONObject main = ciudad.getJSONObject("main");
                JSONObject wind = ciudad.getJSONObject("wind");
                JSONArray weather = ciudad.getJSONArray("weather");
                JSONObject clima1 = (JSONObject)weather.get(0);
                Ciudad city = new Ciudad(String.valueOf(ciudad.getInt("id")));
                city.setNombre(ciudad.getString("name"));
                city.setTemperatura(main.getString("temp"));
                city.setHumedad(main.getString("humidity"));
                city.setVelocidad_viento(wind.getString("speed"));
                city.setDescripcion(clima1.getString("description"));
                datos.add(city);
            }


        } catch (JSONException e) {
            Log.i("CLIMA", e.getMessage());
        }
    }

    public ArrayList<Ciudad> getCiudades(){
        if (this.datos.size() == 0){
            loadData();
        }
        return this.datos;
    }


    public Ciudad getCiudad(int indice){
        return datos.get(indice);
    }

    public double getLATITUD() {
        return LATITUD;
    }

    public void setLATITUD(double LATITUD) {
        this.LATITUD = LATITUD;
    }

    public double getLONGITUD() {
        return LONGITUD;
    }

    public void setLONGITUD(double LONGITUD) {
        this.LONGITUD = LONGITUD;
    }

    public int getCANTIDAD() {
        return CANTIDAD;
    }

    public void setCANTIDAD(int CANTIDAD) {
        this.CANTIDAD = CANTIDAD;
    }
}
