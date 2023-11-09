package ejercicio1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utilidades.BasesDatos;

class HoldingDAOTest {

    HoldingDAO dao;

    @BeforeEach
    void setUp() {
        String ruta_scrip = "src/main/java/ejercicio1/script_datos.sql";
        BasesDatos.borrarDatos("holding");
        BasesDatos.volcarDatos(ruta_scrip,"holding");

        dao = new HoldingDAO("localhost", "holding", "root", "");
    }

    @Test
    void agregarEmpleado() {
        dao.agregarEmpleado("Ismael", "Manzano", "2003-06-26", "Desarrollador",
                "ismaelmanzanoatlantida@gmail.com", "2023/11/08", 2000.0, "CodeCrafters");
    }

    @Test
    void subirSueldo() {
    }

    @Test
    void trasladarEmpleado() {
    }

    @Test
    void empleadosEmpresa() {
    }

    @Test
    void crearCoche() {
    }

    @Test
    void costeProyecto() {
    }

    @Test
    void resumenProyectos() {
    }

    @Test
    void empleadosSinCoche() {
    }

    @Test
    void borrarProyectosSinEmp() {
    }

    @Test
    void borrarAño() {
    }
}