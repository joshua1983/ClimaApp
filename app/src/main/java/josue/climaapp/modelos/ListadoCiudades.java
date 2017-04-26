package josue.climaapp.modelos;

import java.util.ArrayList;

/**
 * Created by josue on 4/25/17.
 */

public class ListadoCiudades {
    private static ListadoCiudades instancia = null;
    private ArrayList<Ciudad> datos = new ArrayList<Ciudad>();

    protected ListadoCiudades(){
        loadData();
    }

    public static ListadoCiudades getInstancia(){
        if (instancia == null){
            instancia = new ListadoCiudades();
        }
        return  instancia;
    }

    private void loadData(){
        for (int i=0; i<10; i++){
            datos.add(new Ciudad("ciudad "+i, "temperatura "+i));
        }
    }

    public ArrayList<Ciudad> getCiudades(){
        return this.datos;
    }

    public Ciudad getCiudad(int indice){
        return datos.get(indice);
    }
}
