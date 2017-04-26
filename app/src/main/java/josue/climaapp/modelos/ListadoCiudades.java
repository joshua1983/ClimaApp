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

        // Bogota
        Ciudad ciudad = new Ciudad("3688685");
        ciudad.setNombre("Bogota");
        datos.add(ciudad);

        // Medellin
        ciudad = new Ciudad("3674962");
        ciudad.setNombre("Medellin");
        datos.add(ciudad);

        // Barranquilla
        ciudad = new Ciudad("3689147");
        ciudad.setNombre("Barranquilla");
        datos.add(ciudad);


    }

    public ArrayList<Ciudad> getCiudades(){
        return this.datos;
    }

    public Ciudad getCiudad(int indice){
        return datos.get(indice);
    }
}
