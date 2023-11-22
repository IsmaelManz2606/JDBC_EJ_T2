package ejercicio2;

import utilidades.BasesDatos;

import java.sql.*;

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

    public void crearEvento(String nombre, String fecha) {
        Connection conexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            conexion = establecerConexion();
            System.out.println("Conexión establecida correctamente.");

            Integer id_evento = devolverIdEventoNombre(nombre, conexion);

            if (id_evento == null) {
                String sql = "INSERT INTO EVENTOS VALUES (DEFAULT, ?, ?)";
                preparedStatement = conexion.prepareStatement(sql);
                preparedStatement.setString(1, nombre);
                preparedStatement.setString(2, fecha);

                preparedStatement.executeUpdate();
                System.out.println("Evento creado con éxito.");
            } else {
                System.out.println("Este evento ya existe");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion(conexion, preparedStatement, resultSet);
        }
    }


    public void añadirSocio(String nombre, String alta) {
        Connection conexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            conexion = establecerConexion();
            System.out.println("Conexión establecida correctamente.");

            // Verificar si el socio ya existe utilizando el método de búsqueda
            Integer id_socio = buscarSocioPorNombre(nombre, conexion);

            if (id_socio == null) {
                // Si el socio no existe, insertarlo en la tabla
                String sql = "INSERT INTO socios VALUES (DEFAULT, ?, ?)";
                preparedStatement = conexion.prepareStatement(sql);
                preparedStatement.setString(1, nombre);
                preparedStatement.setString(2, alta);

                preparedStatement.executeUpdate();
                System.out.println("Socio añadido con éxito.");
            } else {
                System.out.println("Este socio ya existe con ID: " + id_socio);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion(conexion, preparedStatement, resultSet);
        }
    }

    public void apuntarseEvento(String socio,String evento){
        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            conexion = BasesDatos.establecerConexion(this.host,this.base_datos,this.usuario,this.password);
            // Realizar operaciones con la base de datos usando la conexión y la sentencia
            String sql="SELECT id FROM socios WHERE nombre=?";
            sentencia=conexion.prepareStatement(sql);
            sentencia.setString(1,socio);
            resultado=sentencia.executeQuery();
            if(!resultado.next()){
                throw new RuntimeException("El socio no existe");
            }

            int id_socio=resultado.getInt(1);

            sql="SELECT id FROM eventos WHERE nombre=?";
            sentencia= conexion.prepareStatement(sql);
            sentencia.setString(1,evento);
            resultado=sentencia.executeQuery();
            if(!resultado.next()){
                throw new RuntimeException("El evento no existe");
            }

            int id_evento=resultado.getInt(1);

            String sql_insert="INSERT INTO inscripciones " +
                    "VALUES (NULL,?,?)";
            sentencia=conexion.prepareStatement(sql_insert);
            sentencia.setInt(1,id_socio);
            sentencia.setInt(2,id_evento);
            sentencia.executeUpdate();

        } catch (SQLException exception) {
            System.out.println("Error de SQL\n" + exception.getMessage());
            exception.printStackTrace();
        } finally {
            BasesDatos.cerrarConexion(conexion, sentencia, resultado);
        }
    }


    public String eventosSocio(String socio) {
        Connection conexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        StringBuilder eventos = new StringBuilder();

        try {
            conexion = establecerConexion();
            System.out.println("Conexión establecida correctamente.");

            Integer idSocio = buscarSocioPorNombre(socio, conexion);

            if (idSocio != null) {
                String sql = "SELECT e.nombre FROM EVENTOS e " +
                            "JOIN INSCRIPCIONES i ON e.id = i.evento " +
                            "WHERE i.socio = ?";
                preparedStatement = conexion.prepareStatement(sql);
                preparedStatement.setInt(1, idSocio);
                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    eventos.append(resultSet.getString("nombre")).append("\n");
                }

                if (eventos.length() == 0) {
                    eventos.append("El socio no está apuntado a ningún evento.");
                }
            } else {
                eventos.append("Socio no encontrado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion(conexion, preparedStatement, resultSet);
        }

        return eventos.toString();
    }
    public String resumenEventos() {
        Connection conexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        StringBuilder resumen = new StringBuilder();

        try {
            conexion = establecerConexion();
            System.out.println("Conexión establecida correctamente.");

            String sql = "SELECT e.id, e.nombre AS evento, e.fecha, s.nombre AS asistente " +
                    "FROM EVENTOS e " +
                    "LEFT JOIN INSCRIPCIONES i ON e.id = i.evento " +
                    "LEFT JOIN SOCIOS s ON i.socio = s.id " +
                    "ORDER BY e.id";  // Ordenar por id del evento para agrupar resultados

            preparedStatement = conexion.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            int eventoActual = -1;  // Para controlar cambios de evento en el bucle

            while (resultSet.next()) {
                int idEvento = resultSet.getInt("id");
                String nombreEvento = resultSet.getString("evento");
                String fechaEvento = resultSet.getString("fecha");
                String asistente = resultSet.getString("asistente");

                if (idEvento != eventoActual) {
                    // Nuevo evento, agregar información básica del evento
                    resumen.append("Evento: ").append(nombreEvento).append("\n");
                    resumen.append("Fecha: ").append(fechaEvento).append("\n");
                    resumen.append("Asistentes:\n");
                    eventoActual = idEvento;
                }

                // Agregar nombres de asistentes si no es nulo
                if (asistente != null) {
                    resumen.append("- ").append(asistente).append("\n");
                }
            }

            if (resumen.length() == 0) {
                resumen.append("No hay eventos registrados.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion(conexion, preparedStatement, resultSet);
        }

        return resumen.toString();
    }



    public String valoracionesEvento(String evento){
        return null;
    }

    public String eventoMultitudinario(){
        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        String evento=null;
        try {
            conexion = BasesDatos.establecerConexion(this.host,this.base_datos,this.usuario,this.password);
            // Realizar operaciones con la base de datos usando la conexión y la sentencia
            String sql="SELECT e.nombre FROM eventos e " +
                    "JOIN inscripciones i ON e.id=i.evento "+
                    "GROUP BY i.evento"+
                    "ORDER BY count(*) DESC" +
                    "LIMIT 1";
            sentencia=conexion.prepareStatement(sql);
            resultado=sentencia.executeQuery();
            resultado.next();
            evento=resultado.getString(1);


        } catch (SQLException exception) {
            System.out.println("Error de SQL\n" + exception.getMessage());
            exception.printStackTrace();
        } finally {
            BasesDatos.cerrarConexion(conexion, sentencia, resultado);
        }
        return evento;
    }

    public String sinSocios(){
        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            conexion = BasesDatos.establecerConexion(this.host,this.base_datos,this.usuario,this.password);
            // Realizar operaciones con la base de datos usando la conexión y la sentencia


        } catch (SQLException exception) {
            System.out.println("Error de SQL\n" + exception.getMessage());
            exception.printStackTrace();
        } finally {
            BasesDatos.cerrarConexion(conexion, sentencia, resultado);
        }
        return null;
    }

    public String mejorValorado(){
        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        String mejor_valorado=null;
        try {
            conexion = BasesDatos.establecerConexion(this.host,this.base_datos,this.usuario,this.password);
            // Realizar operaciones con la base de datos usando la conexión y la sentencia
            String sql="SELECT e.nombre FROM eventos e " +
                    "JOIN resenas_eventos r ON e.id=r.evento_id "+
                    "GROUP BY r.evento_id "+
                    "ORDER BY avg(r.puntuacion) DESC " +
                    "LIMIT 1";
            sentencia=conexion.prepareStatement(sql);
            resultado=sentencia.executeQuery();
            resultado.next();
            mejor_valorado=resultado.getString(1);
            System.out.println(mejor_valorado);
        } catch (SQLException exception) {
            System.out.println("Error de SQL\n" + exception.getMessage());
            exception.printStackTrace();
        } finally {
            BasesDatos.cerrarConexion(conexion, sentencia, resultado);
        }
        return mejor_valorado;
    }

    public void borrarEventos(Integer año){
        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            conexion = BasesDatos.establecerConexion(this.host,this.base_datos,this.usuario,this.password);
            // Realizar operaciones con la base de datos usando la conexión y la sentencia


        } catch (SQLException exception) {
            System.out.println("Error de SQL\n" + exception.getMessage());
            exception.printStackTrace();
        } finally {
            BasesDatos.cerrarConexion(conexion, sentencia, resultado);
        }
    }

    private Integer buscarSocioPorNombre(String nombre, Connection conexion) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Integer id = null;
        String sql = "SELECT id FROM socios WHERE nombre=?";
        try {
            statement = conexion.prepareStatement(sql);
            statement.setString(1, nombre);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }


    private Integer devolverIdEventoNombre(String evento, Connection conexion) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Integer id = null;
        String existe = "SELECT id FROM EVENTOS WHERE nombre=?";
        try {
            statement = conexion.prepareStatement(existe);
            statement.setString(1, evento);
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
