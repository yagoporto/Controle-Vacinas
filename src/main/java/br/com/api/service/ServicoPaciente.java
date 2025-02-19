package br.com.api.service;

import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.api.dao.DAOPaciente;
import br.com.api.model.PacienteModel.Paciente;
import spark.Request;
import spark.Response;
import spark.Route;

public class ServicoPaciente {

    //  public static Route cadastrarPaciente() {
    //     return new Route() {
    //         @Override
    //         public Object handle(Request request, Response response) throws Exception {
    //             //extrai os parametros do boddy da requisicao http  
    //             String nome = request.queryParams("nome");
    //             String cpf = request.queryParams("cpf");
    //             String sexo = request.queryParams("sexo");
    //             String data_nascimento = request.queryParams("data_nascimento");

    //             //executa o metodo de adicionar o contato no array list
    //             Paciente paciente = new Paciente(nome, cpf, sexo, data_nascimento)

    //             try {
    //                 //passa o objeto para o DAO realizar a insercao no banco de dados
    //                 //e recebe o id gerado no banco de dados
    //                 int idPaciente = DAOPaciente.inserir(paciente);
                    
    //                 //defini o status code do httpd
    //                 response.status(201); // 201 Created
                    
    //                 //possiveis opcoes Classe Anonima, HashMap, Classe interna, 
    //                 // Object mensagem = new Object() {
    //                 //     public String message = "Usuário criado com o ID " + idUsuario + " com sucesso." ;
    //                 // };
                    
    //                 // //retorna um array list vazio no formato json
    //                 // return converteJson.writeValueAsString(mensagem);
                    
    //                 //retorna o id criado e retorna via http response
    //                 return "{\"message\": \"Usuário criado com o ID " + idUsuario + " com sucesso.\"}" ;
                        
    //             } catch (Exception e) {
    //                 response.status(500); // 500 Erro no servidor
    //                 //Retorna a excecao gerado pelo DAOUsuario caso exista
    //                 return "{\"message\": \"" + e.getMessage() + "\"}" ;
    //             }
    //         }
    //     };
    // }
    

      // Método para lidar com a rota de buscar todos os usuários
    public static Route consultarTodosPacientes() {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                ObjectMapper converteJson = new ObjectMapper();

                //busca todos os contatos cadastrados no array list
                //é necessário realizar um cast explicito para converter o retorno 
                //do servicoUsuario
                ArrayList<Paciente> pacientes = DAOPaciente.consultarTodos();

                //se o arraylist estiver vazio
                if (pacientes.isEmpty()) {
                    response.status(200); // 209 
                    return "{\"message\": \"Nenhum paciente encontrado no banco de dados.\"}";
                } else {
                    //se nao estiver vazio devolve o arraylist convertido para json
                    response.status(200); // 200 Ok
                    return converteJson.writeValueAsString(pacientes);
                }
            }
        };
    }

    // Método para lidar com a rota de buscar usuário por ID
    public static Route consultarPacientePorId() {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                //classe para converter objeto para json
                ObjectMapper converteJson = new ObjectMapper();

                int id;

                try {
                    //extrai o parametro id da URL (header http), e converte para inteiro
                    id = Integer.parseInt(request.params(":id"));

                    //busca o contato no array list pela id
                    Paciente paciente = DAOPaciente.consultarPorID(id);

                    if (paciente != null) {
                        //defini o http status code
                        response.status(200); // 200 OK

                        //retorna o objeto encontrado no formato json
                        return converteJson.writeValueAsString(paciente);
                    } else {
                        //defini o http status code
                        response.status(209); // 209 Consulta realizada com sucesso mas nao tem nenhum registro no banco
                        return "{\"message\": \"Nenhum paciente encontrado com este ID.\"}" ;
                    }
                } catch (NumberFormatException e) {
                    //defini o http status code
                    response.status(400); // 400 Requisicao incorreta, foi fornecido um id que nao pode ser convertido para inteiro
                    return "{\"message\": \"ID fornecido está no formato incorreto.\"}" ;
                }
            }
        };
    }

    // Método para lidar com a rota de excluir usuário
    public static Route excluirPaciente() {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                try {     
                    //extrai o parametro id da URL (header http), e converte para inteiro
                    int id = Integer.parseInt(request.params(":id"));
                    
                    //envia o id a ser excluida para o DAO e recebe a quantidade de linhas excluidas
                    int linhasExcluidas = DAOPaciente.excluirPorID(id);

                    //se a quantidade de linhas for maior que 0 significa que o usuario existia no banco de dados
                    if (linhasExcluidas > 0) {
                        response.status(200); //exclusão com sucesso
                        return "{\"message\": \"Paciente com id " + id + " foi excluído com sucesso.\"}" ;
                    //se nao forma maior que 0 o usuario nao existia no banco de dados
                    } else {
                        response.status(209); //id não encontrado
                        return "{\"message\": \"Paciente com id " + id + " foi encontrado no banco de dados.\"}" ;
                    }
                } catch (NumberFormatException e) { //alguma excecao na conversado do id fornecido na URL
                    response.status(400);
                    return "{\"message\": \"ID fornecido está no formato incorreto.\"}" ;
                }
                                    
            }
        };
    }


    
}
