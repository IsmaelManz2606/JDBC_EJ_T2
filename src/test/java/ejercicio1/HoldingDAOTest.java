package ejercicio1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utilidades.BasesDatos;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        dao.subirSueldo("CodeCrafters", 100.0);
    }

    @Test
    void trasladarEmpleado() {
        dao.trasladarEmpleado("Juan", "CodeCrafters");
    }

    @Test
    void empleadosEmpresa() {
        String empleados = dao.empleadosEmpresa("Innovatech Solutions");
        System.out.println(empleados);
    }

    @Test
    void crearCoche() {
        dao.crearCoche("RSQ8", "AUDI", 5000.0, "2022", 2023, "Juan");
    }

    @Test
    void costeProyecto() {
        dao.costeProyecto("CodeFusion");
    }

    @Test
    void resumenProyectos() {
        String resumen = dao.resumenProyectos();
        System.out.println(resumen);
    }

    @Test
    void empleadosSinCoche() {
        Integer numEmpleadosSinCoche = dao.empleadosSinCoche();
        System.out.println("Número de empleados sin coche: " + numEmpleadosSinCoche);
    }


    @Test
    void borrarProyectosSinEmp() {
        dao.BorrarProyectosSinEmp();
    }

    @Test
    void borrarAño() {
        dao.BorrarAño(2022);
    }

}