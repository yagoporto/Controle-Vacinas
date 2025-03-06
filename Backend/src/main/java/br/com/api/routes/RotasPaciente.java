package br.com.api.routes;

import spark.Spark;
import br.com.api.service.ServicoPaciente;


public class RotasPaciente {

    public static void processarRotasPaciente(){

        Spark.get("/consultar", ServicoPaciente.consultarTodosPacientes());
        Spark.get("/consultar/:id", ServicoPaciente.consultarPacientePorId());
        Spark.post("/cadastrar/paciente", ServicoPaciente.cadastrarPaciente());
        Spark.put("/alterar/paciente/:id", ServicoPaciente.alterarUsuario());
        Spark.delete("/excluir/paciente/:id", ServicoPaciente.excluirPaciente());
    }
    
}
