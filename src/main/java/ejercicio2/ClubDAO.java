package ejercicio2;

public class ClubDAO {

    private String host;
    private String base_datos;
    private String usuario;
    private String password;


    public ClubDAO(String host, String base_datos, String usuario, String password) {
        this.host = host;
        this.base_datos = base_datos;
        this.usuario = usuario;
        this.password = password;
    }

    public void crearEvento(String nombre,String fecha){

    }

    public void añadirSocio(String nombre){

    }


    public void apuntarseEvento(String socio,String evento){

    }

    public String eventosSocio(String socio){
        return null;
    }

    public String resumentEventos(){
        return null;
    }

    public String valoracionesEvento(String evento){
        return null;
    }

    public String eventoMultitudinario(){
        return null;
    }

    public String sinSocios(){
        return null;
    }

    public String mejorValorado(){
        return null;
    }

    public void borrarEventos(Integer año){

    }

}
