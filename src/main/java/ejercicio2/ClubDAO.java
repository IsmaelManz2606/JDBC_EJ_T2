package ejercicio2;

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

    public void apuntarseEvento(String socio, String evento) {
        Connection conexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            conexion = establecerConexion();
            System.out.println("Conexión establecida correctamente.");

            Integer idSocio = buscarSocioPorNombre(socio, conexion);
            Integer idEvento = devolverIdEventoNombre(evento, conexion);

            if (idSocio != null && idEvento != null) {
                // Verificar si ya está inscrito
                if (!estaInscrito(idSocio, idEvento, conexion)) {
                    String sql = "INSERT INTO INSCRIPCIONES VALUES (DEFAULT, ?, ?)";
                    preparedStatement = conexion.prepareStatement(sql);
                    preparedStatement.setInt(1, idSocio);
                    preparedStatement.setInt(2, idEvento);

                    preparedStatement.executeUpdate();
                    System.out.println("Socio inscrito en el evento con éxito.");
                } else {
                    System.out.println("Este socio ya está inscrito en el evento.");
                }
            } else {
                System.out.println("Socio o evento no encontrado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion(conexion, preparedStatement, resultSet);
        }
    }
    private boolean estaInscrito(Integer idSocio, Integer idEvento, Connection conexion) throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        boolean estaInscrito = false;

        String sql = "SELECT id FROM INSCRIPCIONES WHERE socio=? AND evento=?";
        try {
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, idSocio);
            statement.setInt(2, idEvento);
            resultSet = statement.executeQuery();

            estaInscrito = resultSet.next();
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
        }

        return estaInscrito;
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
