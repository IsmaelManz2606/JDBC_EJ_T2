package ejercicio3;

import ejercicio2.ClubDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utilidades.BasesDatos;

import static org.junit.jupiter.api.Assertions.*;

class TiendaDAOTest {

    TiendaDAO dao;

    @BeforeEach
    void setUp() {

        String ruta_scrip = "src/main/java/ejercicio3/script_datos.sql";
        BasesDatos.borrarDatos("tienda");
        BasesDatos.volcarDatos(ruta_scrip,"tienda");

        dao = new TiendaDAO("localhost", "tienda", "root", "");
    }

    @Test
    void añadirVenta() {
        dao.añadirVenta("Paco Menendez","Plato de macarrones",4);
    }

    @Test
    void comprasCliente() {
        String res="Plato de macarrones\n" +
                "5\n" +
                "Botella de vino\n" +
                "4\n";
        String esp= dao.comprasCliente("Paco Menendez");
        assertEquals(esp,res);
    }

    @Test
    void recaudacionTotal() {
        Double res=275.0;
        Double esp = dao.recaudacionTotal();
        assertEquals(esp,res);
    }

    @Test
    void porCategorias() {
        String res="55.0\n" +
                "1\n" +
                "308.0\n" +
                "2\n";
        String esp=dao.porCategorias();
        assertEquals(esp,res);
    }

    @Test
    void ultimaVenta() {
        String res="Paco Menendez\n" +
                "Botella de vino";
        String esp= dao.ultimaVenta();
        assertEquals(esp,res);
    }

    @Test
    void masVendido() {

    }

    @Test
    void sinVentas() {
    }

    @Test
    void borrarProveedor() {
        dao.borrarProveedor("Innovate Supply Co.");
    }
}