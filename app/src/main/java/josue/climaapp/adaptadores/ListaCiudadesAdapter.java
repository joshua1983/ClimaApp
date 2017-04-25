package josue.climaapp.adaptadores;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import josue.climaapp.R;
import josue.climaapp.modelos.Ciudad;

/**
 * Created by josue on 4/25/17.
 */

public class ListaCiudadesAdapter extends RecyclerView.Adapter<ListaCiudadesAdapter.ContenidoTarjetaHolder> {

    private ArrayList<Ciudad> datos;

    public ListaCiudadesAdapter(ArrayList<Ciudad> _ciudades){
        this.datos = _ciudades;
    }

    @Override
    public ContenidoTarjetaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tarjeta_city, parent, false);
        ContenidoTarjetaHolder cth = new ContenidoTarjetaHolder(itemView);
        return cth;
    }

    @Override
    public void onBindViewHolder(ContenidoTarjetaHolder holder, int position) {
        Ciudad _ciudad = datos.get(position);
        holder.bindCiudad(_ciudad);
    }


    @Override
    public int getItemCount() {
        return datos.size();
    }

    public static  class ContenidoTarjetaHolder extends ViewHolder{

        private TextView nombre_ciudad;
        private TextView temperatura_ciudad;

        public ContenidoTarjetaHolder(View itemView) {
            super(itemView);

            nombre_ciudad = (TextView) itemView.findViewById(R.id.card_titulo_ciudad);
            temperatura_ciudad = (TextView) itemView.findViewById(R.id.card_temperatura);
        }

        public void bindCiudad(Ciudad ciudad){
            nombre_ciudad.setText(ciudad.getNombre());
            temperatura_ciudad.setText(ciudad.getTemperatura());
        }
    }
}
