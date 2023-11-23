package ejercicio3;

import java.sql.*;

public class TiendaDAO {
    private String host;
    private String base_datos;
    private String usuario;
    private String password;

    Connection conexion=null;
    PreparedStatement sentencia=null;
    ResultSet resultado=null;


    public TiendaDAO(String host, String base_datos, String usuario, String password) {
        this.host = host;
        this.base_datos = base_datos;
        this.usuario = usuario;
        this.password = password;
    }
    private Connection establecerConexion(){
        try{
            conexion= DriverManager.getConnection("jdbc:mysql://localhost/tienda","root","");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conexion;
    }

    public void añadirVenta(String cliente,String producto, Integer ventas){
        try{
            conexion=establecerConexion();
            String sql_seleVentas="SELECT producto from ventas " +
                    "WHERE producto=?";
            sentencia=conexion.prepareStatement(sql_seleVentas);
            sentencia.setString(1,producto);
            resultado=sentencia.executeQuery();
            if (resultado.next()){
                String sql_seleCliente="SELECT cliente from ventas " +
                        "WHERE cliente=?";
                sentencia=conexion.prepareStatement(sql_seleCliente);
                sentencia.setString(1,cliente);
                resultado=sentencia.executeQuery();
                if (resultado.next()){
                    String sql_insert="INSERT INTO ventas VALUES (NULL,?,?,?,?)";
                    sentencia=conexion.prepareStatement(sql_insert);
                    sentencia.setString(1,producto);
                    sentencia.setString(2,cliente);
                    sentencia.setInt(3,ventas);
                    sentencia.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public String comprasCliente(String cliente){
        String res="";
        try{
            conexion=establecerConexion();
            String sql_select="SELECT p.nombre, v.unidades from productos as p " +
                    "LEFT JOIN ventas as v " +
                    "ON v.producto=p.id " +
                    "LEFT JOIN clientes as c " +
                    "ON c.id=v.cliente " +
                    "WHERE c.nombre =?";
            sentencia=conexion.prepareStatement(sql_select);
            sentencia.setString(1,cliente);
            resultado=sentencia.executeQuery();

            while (resultado.next()){
                res+=resultado.getString(1);
                res+="\n";
                res+= resultado.getString(2);
                res+="\n";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public Double recaudacionTotal(){
        Double res=0.0;
        try{
            conexion=establecerConexion();
            String sql_select="SELECT SUM(p.precio)*v.unidades from productos as p " +
                    "LEFT JOIN ventas as v " +
                    "ON v.producto=p.id";
            sentencia=conexion.prepareStatement(sql_select);
            resultado=sentencia.executeQuery();

            while (resultado.next()){
                res+=resultado.getDouble(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }


    public String porCategorias(){
        String res="";
        try {
            conexion=establecerConexion();
            String sql_select="SELECT SUM(p.precio)*v.unidades, p.categoria_id from productos as p " +
                    "LEFT JOIN ventas as v " +
                    "ON v.producto=p.id " +
                    "GROUP BY p.categoria_id";
            sentencia=conexion.prepareStatement(sql_select);
            resultado=sentencia.executeQuery();

            while (resultado.next()){
                res+=resultado.getString(1);
                res+="\n";
                res+=resultado.getString(2);
                res+="\n";
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public String ultimaVenta(){
        String res="";
        try {
            conexion=establecerConexion();
            String sql_slect="SELECT c.nombre, p.nombre from clientes as c " +
                    "LEFT JOIN ventas as v " +
                    "ON c.id=v.cliente " +
                    "LEFT JOIN productos as p " +
                    "ON p.id=v.producto " +
                    "ORDER BY v.fecha DESC " +
                    "LIMIT 1";
            sentencia=conexion.prepareStatement(sql_slect);
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

    public String masVendido(){
        return null;
    }

    public String sinVentas(){
        return null;
    }


    public void borrarProveedor(String proveedor){
        try{
            conexion=establecerConexion();
            String sql_select="SELECT pr.id from proveedores as pr " +
                    "WHERE pr.nombre=?";
            sentencia=conexion.prepareStatement(sql_select);
            sentencia.setString(1,proveedor);
            resultado=sentencia.executeQuery();

            if (resultado.next()){
                Integer prID=resultado.getInt(1);
                String sql_delete="DELETE from productos WHERE productos.proveedor_id=?";
                sentencia=conexion.prepareStatement(sql_delete);
                sentencia.setInt(1,prID);
                Integer filas_afectadas=sentencia.executeUpdate();
                if (filas_afectadas>0){
                    String sql_deletepr="DELETE FROM proveedores WHERE proveedores.nombre=?";
                    sentencia=conexion.prepareStatement(sql_deletepr);
                    sentencia.setString(1,proveedor);
                    sentencia.executeUpdate();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

