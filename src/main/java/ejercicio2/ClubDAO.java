package ejercicio2;

import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;

public class ClubDAO {

    private String host;
    private String base_datos;
    private String usuario;
    private String password;
    Connection conexion=null;
    PreparedStatement sentencia=null;
    ResultSet resultado = null;


    public ClubDAO(String host, String base_datos, String usuario, String password) {
        this.host = host;
        this.base_datos = base_datos;
        this.usuario = usuario;
        this.password = password;
    }
    public Connection establecerConexion(){
        try {
            conexion= DriverManager.getConnection("jdbc:mysql://localhost/club","root","");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conexion;
    }

    public void crearEvento(String nombre,String fecha){
        try{
            conexion=establecerConexion();
            String sql_select="SELECT * from eventos " +
                    "WHERE nombre=?";
            sentencia=conexion.prepareStatement(sql_select);
            sentencia.setString(1,nombre);
            resultado=sentencia.executeQuery();
            if (!resultado.next()){
                String sql_crearEvent="INSERT INTO eventos " +
                        "VALUES (NULL,?,?)";
                sentencia=conexion.prepareStatement(sql_crearEvent);
                sentencia.setString(1,nombre);
                sentencia.setString(2,fecha);
                sentencia.executeUpdate();
                System.out.println("El evento a sido creado con exito");}
            else {
                System.out.println("Evento ya registrado con ese nombre");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void añadirSocio(String nombre){
        try{
            conexion=establecerConexion();
            String sql_select="SELECT nombre from socios " +
                    "WHERE nombre=?";
            sentencia=conexion.prepareStatement(sql_select);
            sentencia.setString(1,nombre);
            resultado=sentencia.executeQuery();
            if (!resultado.next()){
                String sql_crearSocio="INSERT INTO socios " +
                        "VALUES (NULL,?,?)";
                sentencia=conexion.prepareStatement(sql_crearSocio);
                sentencia.setString(1,nombre);
                sentencia.setDate(2,Date.valueOf(LocalDate.now()));
                sentencia.executeUpdate();
                System.out.println("El socio a sido introducido con exito");
            }
            else {
                System.out.println("Socio ya registrado con ese nombre");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    public void apuntarseEvento(String socio,String evento){

        try{
            conexion=establecerConexion();
            String sql_selectSocio="SELECT id from socios " +
                    "WHERE nombre=? ";
            sentencia=conexion.prepareStatement(sql_selectSocio);
            sentencia.setString(1,socio);
            resultado=sentencia.executeQuery();
            if (resultado.next()){
                String idSocio=resultado.getString("id");
                String sql_selectEvent="SELECT id from eventos " +
                        "WHERE nombre=? ";
                sentencia=conexion.prepareStatement(sql_selectEvent);
                sentencia.setString(1,evento);
                resultado=sentencia.executeQuery();
                if (resultado.next()){
                    String idEvent=resultado.getString("id");
                    String sql_selectEvento="INSERT INTO inscripciones " +
                            "VALUES (NULL,?,?)";
                    sentencia=conexion.prepareStatement(sql_selectEvento);
                    sentencia.setString(1,idSocio);
                    sentencia.setString(2,idEvent);
                    sentencia.executeUpdate();

                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public String eventosSocio(String socio){
        String res="";
        try{

            conexion=establecerConexion();
            String sql_select="SELECT e.nombre from eventos e " +
                    "LEFT JOIN inscripciones i " +
                    "On e.id=i.evento " +
                    "LEFT JOIN socios s " +
                    "ON s.id=i.socio " +
                    "WHERE s.nombre=?";
            sentencia=conexion.prepareStatement(sql_select);
            sentencia.setString(1,socio);
            resultado=sentencia.executeQuery();

            while (resultado.next()){
                res+=resultado.getString(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return res;
    }

    public String resumentEventos(){
        String res="";
        try{
            conexion=establecerConexion();

            String sql_select="SELECT e.nombre, e.fecha, s.nombre from eventos as e " +
                    "LEFT JOIN inscripciones as i " +
                    "on e.id=i.evento " +
                    "LEFT JOIN socios as s " +
                    "on s.id=i.socio " +
                    "ORDER BY e.nombre";
            sentencia=conexion.prepareStatement(sql_select);
            resultado= sentencia.executeQuery();

            while (resultado.next()){
                res+=resultado.getString(1);
                res+="\n";
                res+=resultado.getString(2);
                res+="\n";
                res+=resultado.getString(3);
                res+="\n";
                res+="=====================";
                res+="\n";
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return res;
    }

    public String valoracionesEvento(String evento){
        String res="";
        try{
            conexion=establecerConexion();
            String sql_select="SELECT e.nombre, re.comentario from resenas_eventos as re " +
                    "LEFT JOIN eventos as e " +
                    "On e.id=re.evento_id " +
                    "where e.nombre=?";
            sentencia=conexion.prepareStatement(sql_select);
            sentencia.setString(1,evento);
            resultado=sentencia.executeQuery();

            while (resultado.next()){
                res+= resultado.getString(1);
                res+="\n";
                res+= resultado.getString(2);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return res;
    }

    public String eventoMultitudinario(){
        String res="";
        try {
            conexion=establecerConexion();
            String sql_select="SELECT e.nombre from eventos as e " +
                    "LEFT JOIN inscripciones as i " +
                    "on e.id=i.evento " +
                    "ORDER BY i.socio DESC " +
                    "LIMIT 1";
            sentencia=conexion.prepareStatement(sql_select);
            resultado=sentencia.executeQuery();

            while (resultado.next()){
                res+=resultado.getString(1);
                res+="\n==============";
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public String sinSocios(){
        String res="";
        try{
            conexion=establecerConexion();
            String sql_select="SELECT e.nombre from eventos as e " +
                    "LEFT JOIN inscripciones as i " +
                    "on e.id=i.evento " +
                    "where i.socio is null";
            sentencia=conexion.prepareStatement(sql_select);
            resultado=sentencia.executeQuery();

            while (resultado.next()){
                res+=resultado.getString(1);
                res+="\n";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public String mejorValorado(){
        String res="";
        try{
            conexion=establecerConexion();
            String sql_select="SELECT e.nombre, AVG(re.puntuacion) from eventos as e " +
                    "LEFT JOIN resenas_eventos as re " +
                    "on e.id=re.evento_id " +
                    "ORDER BY AVG(re.puntuacion)";
            sentencia=conexion.prepareStatement(sql_select);
            resultado= sentencia.executeQuery();

            while (resultado.next()){
                res+=resultado.getString(1);
                res+="\n";
                res+=resultado.getString(2);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public void borrarEventos(Integer año){
        try{
            conexion=establecerConexion();
            String sql_delete="DELETE eventos from eventos " +
                    "WHERE YEAR(eventos.fecha)<?";
            sentencia=conexion.prepareStatement(sql_delete);
            sentencia.setInt(1,año);
            sentencia.executeQuery();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
