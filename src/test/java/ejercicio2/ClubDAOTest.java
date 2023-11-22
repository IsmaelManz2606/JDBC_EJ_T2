package ejercicio2;

import ejercicio1.HoldingDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utilidades.BasesDatos;

import static org.junit.jupiter.api.Assertions.*;

class ClubDAOTest {


    ClubDAO dao;
    @BeforeEach
    void setUp() {
        String ruta_scrip = "src/main/java/ejercicio2/script_datos.sql";
        BasesDatos.borrarDatos("club");
        BasesDatos.volcarDatos(ruta_scrip,"club");

        dao = new ClubDAO("localhost", "club", "root", "");

    }

    @Test
    void crearEvento() {
        dao.crearEvento("4X4", "2023-11-30");
    }

    @Test
    void añadirSocio() {
        dao.añadirSocio("Ismael", "2003-06-26");
    }

    @Test
    void apuntarseEvento() {
        dao.apuntarseEvento("Jose","Partido de futbol sala");
    }

    @Test
    void eventosSocio() {
        String eventos = dao.eventosSocio("Juan");
        System.out.println("Eventos de Juan:\n" + eventos);

        assertEquals("Fiesta de la espuma\n", eventos);
        assertEquals("Cata de vinos\n", eventos);
    }

    @Test
    void resumenEventos() {
        String resumen = dao.resumenEventos();
        System.out.println("Resumen de Eventos:\n" + resumen);

    }

    @Test
    void valoracionesEvento() {
    }

    @Test
    void eventoMultitudinario() {
        String valor_esperado="Fiesta de la espuma";

        assertEquals(valor_esperado,dao.eventoMultitudinario());
    }

    @Test
    void sinSocios() {
    }

    @Test
    void mejorValorado() {
        String valor_esperado="Partido de futbol sala";

        assertEquals(valor_esperado,dao.mejorValorado());
    }

    @Test
    void borrarEventos() {
    }
}