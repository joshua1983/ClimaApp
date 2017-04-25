package josue.climaapp.modelos;

/**
 * Created by josue on 4/25/17.
 */

public class Ciudad {
    private String nombre = "";
    private String temperatura = "";
    private String descripcion = "";
    private String humedad = "";
    private String velocidad_viento = "";

    public Ciudad(String nombre, String temperatura){
        this.setNombre(nombre);
        this.setTemperatura(temperatura);
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
}
