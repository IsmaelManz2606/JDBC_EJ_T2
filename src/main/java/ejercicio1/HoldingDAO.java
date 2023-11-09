package ejercicio1;

public class HoldingDAO {

    private String host;
    private String base_datos;
    private String usuario;
    private String password;

    private String pistolas;


    public HoldingDAO(String host, String base_datos, String usuario, String password) {
        this.host = host;
        this.base_datos = base_datos;
        this.usuario = usuario;
        this.password = password;
    }

    public void agregarEmpleado(String nombre,String apellidos,String fecha_nacimiento,String categoria,String email,String contratacion,Double salario,String empresa){

    }

    public void subirSueldo(String empresa,Double subida){

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

}
