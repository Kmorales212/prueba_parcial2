package cl.duocuc.evaluacion2.dto;

public class RegistroUsuarioCompletoDTO {
    private String rutUsur;
    private String nombreUsur;
    private String apellidoUsur;
    private String correoUsur;

    private String nombDireccion;
    private int numDireccion;

    private String nombreComuna;
    private String nombreCiudad;

    // Getters y setters (o usar Lombok)

    public String getRutUsur() { return rutUsur; }
    public void setRutUsur(String rutUsur) { this.rutUsur = rutUsur; }

    public String getNombreUsur() { return nombreUsur; }
    public void setNombreUsur(String nombreUsur) { this.nombreUsur = nombreUsur; }

    public String getApellidoUsur() { return apellidoUsur; }
    public void setApellidoUsur(String apellidoUsur) { this.apellidoUsur = apellidoUsur; }

    public String getCorreoUsur() { return correoUsur; }
    public void setCorreoUsur(String correoUsur) { this.correoUsur = correoUsur; }

    public String getNombDireccion() { return nombDireccion; }
    public void setNombDireccion(String nombDireccion) { this.nombDireccion = nombDireccion; }

    public int getNumDireccion() { return numDireccion; }
    public void setNumDireccion(int numDireccion) { this.numDireccion = numDireccion; }

    public String getNombreComuna() { return nombreComuna; }
    public void setNombreComuna(String nombreComuna) { this.nombreComuna = nombreComuna; }

    public String getNombreCiudad() { return nombreCiudad; }
    public void setNombreCiudad(String nombreCiudad) { this.nombreCiudad = nombreCiudad; }
}