package josue.climaapp.modelos;

/**
 * Created by josue on 4/25/17.
 */

public class Ciudad {
    private String id = "";
    private String nombre = "";
    private String temperatura = "";
    private String descripcion = "";
    private String humedad = "";
    private String velocidad_viento = "";


    public Ciudad(String id){
        this.setId(id);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTemperatura() {

        return temperatura;
    }

    public void setTemperatura(String temperatura) {
        if (temperatura != "") {
            double centigrados = Double.parseDouble(temperatura) - 273.15;
            temperatura = Double.toString(centigrados);
        }
        this.temperatura = temperatura;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getHumedad() {
        return humedad;
    }

    public void setHumedad(String humedad) {
        this.humedad = humedad;
    }

    public String getVelocidad_viento() {
        return velocidad_viento;
    }

    public void setVelocidad_viento(String velocidad_viento) {
        this.velocidad_viento = velocidad_viento;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
