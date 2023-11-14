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
        Connection conexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            conexion = establecerConexion();
            Integer id_empresa = devolverIdEmpresaNombre(empresa, conexion);

            if (id_empresa != null){
                //aqui ya existe la empresa comprobada
                //actualizo de la tabla empleado y cojo el id de la empresa que es el que tengo que cambiar
                //con el id de empleado que es "id" y abajo añadirlo al preparedstatement
                String updatesql = "UPDATE empleados SET empresa_id =? WHERE nombre =?";
                preparedStatement = conexion.prepareStatement(updatesql);
                preparedStatement.setInt(1, id_empresa);
                preparedStatement.setString(2, emplead);

                int filas =preparedStatement.executeUpdate();
                System.out.println("Empresa actualizada");

                if (filas>0){
                    System.out.println("Se han actualizado " + filas + " filas");
                }else{
                    System.out.println("No se ha actualizado ninguna fila");
                }

            }else{
                System.out.println();
            }




        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            cerrarConexion(conexion, preparedStatement, resultSet);
        }

    }

    public String empleadosEmpresa(String empresa) {
        Connection conexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            conexion = establecerConexion();
            Integer id_empresa = devolverIdEmpresaNombre(empresa, conexion);

            if (id_empresa != null) {
                String sql = "SELECT nombre FROM empleados WHERE empresa_id=?";
                preparedStatement = conexion.prepareStatement(sql);
                preparedStatement.setInt(1, id_empresa);

                resultSet = preparedStatement.executeQuery();

                StringBuilder empleados = new StringBuilder("Empleados de " + empresa + ":\n");

                while (resultSet.next()) {
                    String nombreEmpleado = resultSet.getString("nombre");
                    empleados.append(nombreEmpleado).append("\n");
                }

                System.out.println("Consulta ejecutada correctamente.");

                return empleados.toString();
            } else {
                System.out.println("No se encuentra la empresa.");
                return "No se encuentra la empresa.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al obtener empleados de la empresa.";
        } finally {
            cerrarConexion(conexion, preparedStatement, resultSet);
        }
    }



    public void crearCoche(String modelo, String fabricante, Double cc, String lanzamiento, Integer año, String empleado) {
        Connection conexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            conexion = establecerConexion();
            Integer id_empleado = devolverIdEmpleadoNombre(empleado, conexion);

            if (id_empleado != null) {
                String sql = "INSERT INTO coches (cc, fabricante, modelo, año_lanzamiento, empleado_id) VALUES (?, ?, ?, ?, ?)";
                preparedStatement = conexion.prepareStatement(sql);
                preparedStatement.setDouble(1, cc);
                preparedStatement.setString(2, fabricante);
                preparedStatement.setString(3, modelo);
                preparedStatement.setInt(4, año);
                preparedStatement.setInt(5, id_empleado);

                preparedStatement.executeUpdate();
                System.out.println("Coche creado con éxito.");
            } else {
                System.out.println("No se encuentra el empleado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion(conexion, preparedStatement, resultSet);
        }
    }

    public Double costeProyecto(String proyecto) {
        Connection conexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            conexion = establecerConexion();
            Integer id_proyecto = devolverIdProyectoTitulo(proyecto, conexion);

            if (id_proyecto != null) {
                String sql = "SELECT SUM(empleados.salario) AS coste FROM empleados " +
                        "JOIN empleados_proyectos ON empleados.id = empleados_proyectos.empleado_id " +
                        "WHERE empleados_proyectos.proyecto_id = ?";
                preparedStatement = conexion.prepareStatement(sql);
                preparedStatement.setInt(1, id_proyecto);

                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    Double coste = resultSet.getDouble("coste");
                    System.out.println("Coste del proyecto '" + proyecto + "': " + coste);
                    return coste;
                } else {
                    System.out.println("No hay empleados asignados al proyecto.");
                    return 0.0;
                }
            } else {
                System.out.println("No se encuentra el proyecto.");
                return 0.0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0.0;
        } finally {
            cerrarConexion(conexion, preparedStatement, resultSet);
        }
    }

    public String resumenProyectos() {
        Connection conexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            conexion = establecerConexion();

            String sql = "SELECT proyectos.titulo AS proyecto, GROUP_CONCAT(empleados.nombre) AS empleados, SUM(empleados.salario) AS coste " +
                    "FROM proyectos " +
                    "JOIN empleados_proyectos ON proyectos.id = empleados_proyectos.proyecto_id " +
                    "JOIN empleados ON empleados_proyectos.empleado_id = empleados.id " +
                    "GROUP BY proyectos.titulo";

            preparedStatement = conexion.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            StringBuilder resumen = new StringBuilder();

            while (resultSet.next()) {
                String proyecto = resultSet.getString("proyecto");
                String empleados = resultSet.getString("empleados");
                Double coste = resultSet.getDouble("coste");

                resumen.append("Proyecto: ").append(proyecto).append("\n");
                resumen.append("Empleados: ").append(empleados).append("\n");
                resumen.append("Coste: ").append(coste).append("\n\n");
            }

            System.out.println("Consulta ejecutada correctamente.");

            return resumen.toString();
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al obtener el resumen de proyectos.";
        } finally {
            cerrarConexion(conexion, preparedStatement, resultSet);
        }
    }

    public Integer empleadosSinCoche() {
        Connection conexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            conexion = establecerConexion();

            String sql = "SELECT COUNT(DISTINCT empleados.id) AS numEmpleadosSinCoche " +
                    "FROM empleados " +
                    "LEFT JOIN coches ON empleados.id = coches.empleado_id " +
                    "WHERE coches.id IS NULL";

            preparedStatement = conexion.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("numEmpleadosSinCoche");
            } else {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            cerrarConexion(conexion, preparedStatement, resultSet);
        }
    }



    public void BorrarProyectosSinEmp() {
        Connection conexion = null;
        PreparedStatement preparedStatement = null;

        try {
            conexion = establecerConexion();

            // Obtener proyectos sin empleados
            String sqlProyectosSinEmp = "SELECT id FROM proyectos WHERE id NOT IN (SELECT DISTINCT proyecto_id FROM empleados_proyectos)";
            preparedStatement = conexion.prepareStatement(sqlProyectosSinEmp);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Verificar si los proyectos tienen empleados
            boolean todosConEmpleados = !resultSet.next();

            if (todosConEmpleados) {
                System.out.println("Todos los proyectos tienen empleados asignados. No se eliminaron proyectos.");
            } else {
                // Eliminar proyectos sin empleados
                do {
                    int idProyecto = resultSet.getInt("id");
                    String sqlEliminarProyecto = "DELETE FROM proyectos WHERE id=?";
                    try (PreparedStatement preparedStatementEliminar = conexion.prepareStatement(sqlEliminarProyecto)) {
                        preparedStatementEliminar.setInt(1, idProyecto);
                        preparedStatementEliminar.executeUpdate();
                    }
                } while (resultSet.next());

                System.out.println("Proyectos sin empleados asignados eliminados correctamente.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion(conexion, preparedStatement, null);
        }
    }



    public void BorrarAño(Integer año) {
        Connection conexion = null;
        PreparedStatement preparedStatement = null;

        try {
            conexion = establecerConexion();

            // Obtener proyectos del año específico
            String sqlProyectosAño = "SELECT id FROM proyectos WHERE YEAR(comienzo) = ?";
            preparedStatement = conexion.prepareStatement(sqlProyectosAño);
            preparedStatement.setInt(1, año);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Eliminar asignaciones de empleados en esos proyectos
            while (resultSet.next()) {
                int idProyecto = resultSet.getInt("id");
                String sqlEliminarAsignaciones = "DELETE FROM empleados_proyectos WHERE proyecto_id=?";
                try (PreparedStatement preparedStatementEliminarAsignaciones = conexion.prepareStatement(sqlEliminarAsignaciones)) {
                    preparedStatementEliminarAsignaciones.setInt(1, idProyecto);
                    preparedStatementEliminarAsignaciones.executeUpdate();
                }
            }

            // Eliminar los proyectos del año específico
            String sqlEliminarProyectos = "DELETE FROM proyectos WHERE YEAR(comienzo) = ?";
            try (PreparedStatement preparedStatementEliminarProyectos = conexion.prepareStatement(sqlEliminarProyectos)) {
                preparedStatementEliminarProyectos.setInt(1, año);
                preparedStatementEliminarProyectos.executeUpdate();
            }

            System.out.println("Proyectos del año " + año + " eliminados correctamente.");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion(conexion, preparedStatement, null);
        }
    }



    private Integer devolverIdProyectoTitulo(String proyecto, Connection conexion) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Integer id = null;
        String existe = "SELECT id FROM proyectos WHERE titulo=?";
        try {
            statement = conexion.prepareStatement(existe);
            statement.setString(1, proyecto);
            resultSet = statement.executeQuery();
            if (resultSet.next()) id = resultSet.getInt("id");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }


    private Integer devolverIdEmpleadoNombre(String empleado, Connection conexion) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Integer id = null;
        String existe = "SELECT id FROM empleados WHERE nombre=?";
        try {
            statement = conexion.prepareStatement(existe);
            statement.setString(1, empleado);
            resultSet = statement.executeQuery();
            if (resultSet.next()) id = resultSet.getInt("id");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
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
