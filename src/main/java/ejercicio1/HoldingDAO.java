package ejercicio1;

import java.sql.*;

public class HoldingDAO {

    private String host;
    private String base_datos;
    private String usuario;
    private String password;


    public HoldingDAO(String host, String base_datos, String usuario, String password) {
        this.host = host;
        this.base_datos = base_datos;
        this.usuario = usuario;
        this.password = password;
    }

    public void agregarEmpleado(String nombre, String apellidos, String fecha_nacimiento, String categoria, String email, String contratacion, Double salario, String empresa) {
        Connection conexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            conexion = establecerConexion();
            Integer id_empresa = devolverIdEmpresaNombre(empresa, conexion);

            if (id_empresa != null){
                String existe  = "SELECT email FROM empleados WHERE email=?";
                preparedStatement = conexion.prepareStatement(existe);
                preparedStatement.setString(1, email);
                resultSet = preparedStatement.executeQuery();


                if (!resultSet.next()){
            String sql = "INSERT INTO empleados (fecha_nacimiento, category, email, nombre, apellidos, fecha_contratacion, salario, empresa_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                preparedStatement = conexion.prepareStatement(sql);
                preparedStatement.setString(1, fecha_nacimiento);
                preparedStatement.setString(2, categoria);
                preparedStatement.setString(3, email);
                preparedStatement.setString(4, nombre);
                preparedStatement.setString(5, apellidos);
                preparedStatement.setString(6, contratacion);
                preparedStatement.setDouble(7, salario);
                preparedStatement.setInt(8, id_empresa);

                preparedStatement.executeUpdate();
                System.out.println("Empleado agregado con éxito.");

                }
                else {
                    System.out.println("Este empleado ya existe");
                }
            }else {
                System.out.println("No se encuentra a la empresa");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            cerrarConexion(conexion, preparedStatement, resultSet);
        }
    }



    public void subirSueldo(String empresa,Double subida) {
        Connection conexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            conexion = establecerConexion();
            Integer id_empresa = devolverIdEmpresaNombre(empresa, conexion);

            if (id_empresa != null) {

                    String updatesql = "UPDATE empleados SET salario = salario + ? WHERE empresa_id=?";
                    preparedStatement = conexion.prepareStatement(updatesql);
                    preparedStatement.setDouble(1, subida);
                    preparedStatement.setInt(2, id_empresa);
                    int filas =preparedStatement.executeUpdate();
                    System.out.println("Sueldo actualizado con éxito.");

                    if (filas>0){
                        System.out.println("Se han actualizado " + filas + " filas");
                    }else{
                        System.out.println("No se ha actualizado ninguna fila");
                    }

            }else{
                    System.out.println("Esta empresa no existe");
                }
        } catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            cerrarConexion(conexion, preparedStatement, resultSet);
        }
    }

    public void trasladarEmpleado(String emplead,String empresa){

    }

    public String empleadosEmpresa(String empresa){
        return null;
    }

    public void crearCoche(String modelo,String fabricante,Double cc,String lanzamiento,Integer año,String empleado){

    }

    public Double costeProyecto(String proyecto){
        return null;
    }

    public String resumenProyectos(){
        return null;
    }

    public Integer empleadosSinCoche(){
        return null;
    }

    public void BorrarProyectosSinEmp(){

    }

    public void BorrarAño(Integer año){

    }

    private Integer devolverIdEmpresaNombre(String empresa, Connection conexion) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Integer id = null;
        String existe = "SELECT id FROM empresas WHERE razon_social=?";
        try {
            statement = conexion.prepareStatement(existe);
            statement.setString(1, empresa);
            resultSet = statement.executeQuery();
            if (resultSet.next()) id = resultSet.getInt("id");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    public Connection establecerConexion() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://" + this.host + "/" + this.base_datos, this.usuario, this.password);
    }
    public void cerrarConexion(Connection conexion, PreparedStatement sentencia, ResultSet resultado) {
        try {
            if (resultado != null) resultado.close();
            if (sentencia != null) sentencia.close();
            if (conexion != null) conexion.close();
        } catch (SQLException exception) {
            System.out.println("Error al cerrar la conexión\n" + exception.getMessage());
            exception.printStackTrace();
        }
    }

}
