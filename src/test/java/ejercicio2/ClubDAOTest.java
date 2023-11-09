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
    }

    @Test
    void añadirSocio() {
    }

    @Test
    void apuntarseEvento() {
    }

    @Test
    void eventosSocio() {
    }

    @Test
    void sociosEvento() {
    }

    @Test
    void resumentEventos() {
    }

    @Test
    void valoracionesEvento() {
    }

    @Test
    void eventoMultitudinario() {
    }

    @Test
    void sinSocios() {
    }

    @Test
    void mejorValorado() {
    }

    @Test
    void borrarEventos() {
    }
}