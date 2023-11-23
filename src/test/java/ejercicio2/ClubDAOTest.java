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
        dao.crearEvento("jauja","1990-03-15");
    }

    @Test
    void añadirSocio() {
        dao.añadirSocio("PACO");
    }

    @Test
    void apuntarseEvento() {
        dao.apuntarseEvento("Juan","Partido de futbol sala");
    }

    @Test
    void eventosSocio() {
        String res=dao.eventosSocio("Juan");
        String esp="Fiesta de la espumaCata de vinos";
        assertEquals(res,esp);
    }

    @Test
    void sociosEvento() {

    }

    @Test
    void resumentEventos() {
        String res=dao.resumentEventos();
        String espe="Cata de vinos\n" +
                "2020-11-30\n" +
                "Jose\n" +
                "=====================\n" +
                "Cata de vinos\n" +
                "2020-11-30\n" +
                "Juan\n" +
                "=====================\n" +
                "Fiesta de la espuma\n" +
                "2021-12-20\n" +
                "Antonio\n" +
                "=====================\n" +
                "Fiesta de la espuma\n" +
                "2021-12-20\n" +
                "Juan\n" +
                "=====================\n" +
                "Maraton de cine\n" +
                "2022-05-30\n" +
                "null\n" +
                "=====================\n" +
                "Partido de futbol sala\n" +
                "2023-06-30\n" +
                "Antonio\n" +
                "=====================\n";
        assertEquals(res,espe);
    }

    @Test
    void valoracionesEvento() {
        String res="Fiesta de la espuma\n" +
                "Gran evento, mucha diversiónFiesta de la espuma\n" +
                "Divertido pero podría haber más actividades";
        String esp=dao.valoracionesEvento("Fiesta de la espuma");
        assertEquals(res,esp);
    }

    @Test
    void eventoMultitudinario() {
        String res="Cata de vinos\n" +
                "==============";
        String esp=dao.eventoMultitudinario();
        assertEquals(esp,res);
    }

    @Test
    void sinSocios() {
        String res= "Maraton de cine\n";
        String esp=dao.sinSocios();
        assertEquals(esp,res);
    }

    @Test
    void mejorValorado() {
        String res="Fiesta de la espuma\n" +
                "4.2000";
        String esp=dao.mejorValorado();
        assertEquals(esp,res);
    }

    @Test
    void borrarEventos() {
    }
}