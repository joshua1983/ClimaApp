package josue.climaapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import josue.climaapp.adaptadores.ListaCiudadesAdapter;
import josue.climaapp.modelos.Ciudad;
import josue.climaapp.modelos.ListadoCiudades;

/**
 * Created by josue on 4/25/17.
 */

public class OpcionCiudades extends Fragment  {

    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    ArrayList<Ciudad> datos = new ArrayList<Ciudad>();
    ProgressDialog dialogo_espere;


    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    RecyclerView recView;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected LayoutManagerType mCurrentLayoutManagerType;

    double LATITUD, LONGITUD =0.0;

    public OpcionCiudades(){

    }



    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        Bundle bundle = this.getArguments();
        dialogo_espere = new ProgressDialog(getContext());
        dialogo_espere.setMessage("Loading cities");
        if (bundle != null) {
            LATITUD = bundle.getDouble("lat");
            LONGITUD = bundle.getDouble("lng");
            cargarCiudades();
        }

    }

    public void cargarCiudades(){


        new CargarDatosCiudades().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View componentes = inflater.inflate(R.layout.fragment_ciudades, container, false);
        recView = (RecyclerView) componentes.findViewById(R.id.recycler_lista_ciudades);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);

        recView.setHasFixedSize(true);


        final ListaCiudadesAdapter adaptador_ciudades = new ListaCiudadesAdapter(datos);
        adaptador_ciudades.notifyDataSetChanged();

        recView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false));

        return componentes;
    }

    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        if (recView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) recView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        switch (layoutManagerType) {
            case GRID_LAYOUT_MANAGER:
                mLayoutManager = new GridLayoutManager(getActivity(), 2);
                mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
                break;
            case LINEAR_LAYOUT_MANAGER:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                break;
            default:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        }

        recView.setLayoutManager(mLayoutManager);
        recView.scrollToPosition(scrollPosition);

    }



    private class CargarDatosCiudades extends AsyncTask<Void, Void, Boolean> {



        public  CargarDatosCiudades(){

        }


        @Override
        protected Boolean doInBackground(Void... params) {

            ListadoCiudades ciudades = ListadoCiudades.getInstancia(getContext(), LATITUD, LONGITUD, 10);
            datos = ciudades.getCiudades();

            return true;
        }


        @Override
        protected void onPreExecute() {
            dialogo_espere.show();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            dialogo_espere.dismiss();
            ListaCiudadesAdapter adaptador_ciudades = new ListaCiudadesAdapter(datos);
            adaptador_ciudades.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {

                    DetalleClima detalle = new DetalleClima();
                    Bundle argumentos = new Bundle();
                    argumentos.putInt("indice",recView.getChildAdapterPosition(view));
                    argumentos.putDouble("lat", LATITUD);
                    argumentos.putDouble("lng", LONGITUD);
                    detalle.setArguments(argumentos);

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content_frame, detalle, null)
                            .addToBackStack(null)
                            .commit();

                }
            });
            recView.setAdapter(adaptador_ciudades);
            recView.invalidate();
        }

        @Override
        protected void onCancelled() {
            dialogo_espere.dismiss();
        }
    }

}
