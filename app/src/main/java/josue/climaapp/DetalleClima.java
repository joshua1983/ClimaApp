package josue.climaapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.util.List;

import josue.climaapp.modelos.Ciudad;
import josue.climaapp.modelos.ListadoCiudades;

/**
 * Created by josue on 4/25/17.
 */

public class DetalleClima extends Fragment {

    Ciudad city;
    int INDICE;


    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);

        Bundle bundle = this.getArguments();
        if (bundle != null){
            int indice = bundle.getInt("indice");
            ListadoCiudades ciudades = ListadoCiudades.getInstancia();
            this.city = ciudades.getCiudad(indice);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View componentes = inflater.inflate(R.layout.detalle_clima, container, false);

        TextView txt_nombre = (TextView) componentes.findViewById(R.id.detalle_nombre_ciudad);
        TextView txt_descripcion = (TextView) componentes.findViewById(R.id.detalle_descripcion_clima);
        TextView txt_temperatura = (TextView) componentes.findViewById(R.id.detalle_temperatura);
        TextView txt_humedad = (TextView) componentes.findViewById(R.id.detalle_humedad);
        TextView txt_velocidad = (TextView) componentes.findViewById(R.id.detalle_viento);

        txt_nombre.setText(this.city.getNombre());
        txt_descripcion.setText(this.city.getDescripcion());
        txt_temperatura.setText(this.city.getTemperatura());
        txt_humedad.setText(this.city.getHumedad());
        txt_velocidad.setText(this.city.getVelocidad_viento());

        return componentes;
    }

}
