package br.com.api.service;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import br.com.api.dao.DAOImunizacao;
import br.com.api.model.Imunizacao;
import spark.Request;
import spark.Response;
import spark.Route;

public class ServicoImunizacao {
    // Método para lidar com a rota de adicionar imunizacao
    public static Route cadastrarImunizacao(){
        return new Route() {

            @Override
            public Object handle(Request request, Response response) throws Exception {
                //extrai os parametros do boddy da requisicao http
                int id_paciente = Integer.parseInt(request.queryParams("id_paciente"));
                int id_dose = Integer.parseInt(request.queryParams("id_dose"));
                Date data_aplicacao = Date.valueOf(request.queryParams("data_aplicacao"));
                String fabricante = request.queryParams("fabricante");
                String lote = request.queryParams("lote");
                String local_aplicacao = request.queryParams("local_aplicacao");
                String profissional_aplicador = request.queryParams("profissional_aplicador");

                //Executar o metodod de adicionar imunizaçao no array list
                Imunizacao imunizacao = new Imunizacao( id_paciente, id_dose, data_aplicacao, fabricante, lote, local_aplicacao, profissional_aplicador);
                
                try{
                    int idImunizacao = DAOImunizacao.adicionarImunizacao(imunizacao);

                    response.status(201);

                    return "{\"message\": \"Usuário criado com o ID " + idImunizacao + " com sucesso.\"}" ;
                } catch (Exception e) {
                    response.status(500); // 500 Erro no servidor
                    //Retorna a excecao gerado pelo DAOUsuario caso exista
                    return "{\"message\": \"" + e.getMessage() + "\"}" ;
                }
            }
        };
    }

    //Metodo para alterar a imunizacao
    public static Route alterarImunizacao() {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                try {
                    // Extrair os parâmetros do body da requisição HTTP
                    int id = Integer.parseInt(request.queryParams("id"));
                    int id_paciente = Integer.parseInt(request.queryParams("id_paciente"));
                    int id_dose = Integer.parseInt(request.queryParams("id_dose"));
                    Date data_aplicacao = Date.valueOf(request.queryParams("data_aplicacao"));
                    String fabricante = request.queryParams("fabricante");
                    String lote = request.queryParams("lote");
                    String local_aplicacao = request.queryParams("local_aplicacao");
                    String profissional_aplicador = request.queryParams("profissional_aplicador");

                    // Executar o método de adicionar imunização no array list
                    Imunizacao imunizacao = new Imunizacao(id, id_paciente, id_dose, data_aplicacao, fabricante, lote, local_aplicacao, profissional_aplicador);

                    int qtdeLinhasAlteradas = DAOImunizacao.alterarImunizacao(imunizacao);

                    if (qtdeLinhasAlteradas > 0) {
                        response.status(200); // 200 Ok
                        return "{\"message\": \"Usuário com id " + id + " foi atualizado com sucesso.\"}";
                    } else {
                        response.status(404); // 404 Not Found
                        return "{\"message\": \"O usuário com id " + id + " não foi encontrado.\"}";
                    }
                } catch (NumberFormatException e) { // Algum erro de conversão do ID passado na URL
                    response.status(400);
                    return "{\"message\": \"ID fornecido está no formato incorreto.\"}";
                } catch (Exception e) {
                    response.status(500);
                    return "{\"message\": \"Erro ao processar a requisição.\"}";
                }
            }
        };
    }

    public static Route excluirImunizacao(){
        return new Route(){
            @Override
            public Object handle(Request request, Response response) throws Exception{
                try{
                    //Extrair o id da requisicao http
                    int id = Integer.parseInt(request.queryParams("id"));
    
                    //Executar o metodo de excluir imunizacao no array list
                    int qtdeLinhasExcluidas = DAOImunizacao.excluirImunizacao(id);
    
                    if (qtdeLinhasExcluidas > 0){
                        response.status(200); // 200 Ok
                        return "{\"message\": \"Imunização com id " + id + " foi excluído com sucesso.\"}";
                    //se nao for maior que 0 nao existia o usuario no banco de dados
                    } else {
                        response.status(209); // 404 Not Found
                        return "{\"message\": \"O Imunização  com id " + id + " não foi encontrado.\"}";
                    }
                } catch (NumberFormatException e) { //algum erro de conversao do id passado na url
                    response.status(400);
                    return "{\"message\": \"ID fornecido está no formato incorreto.\"}" ;
                } catch (Exception e) {
                    response.status(500);
                    return "{\"message\": \"Erro ao processar a requisição.\"}";
                }
            }
        };
    }

    public static Route excluirTodos(){
        return new Route(){
            public Object handle(Request request, Response response) throws Exception{
                try{
                    //Extrair o id da requisicao http
                    int id_paciente = Integer.parseInt(request.queryParams("id_paciente"));
    
                    //Executar o metodo de excluir usuario no array list
                    int qtdeLinhasExcluidas = DAOImunizacao.excluirTodos(id_paciente);
    
                    if (qtdeLinhasExcluidas > 0){
                        response.status(200); // 200 Ok
                        return "{\"message\": \"Imunizações do paciente com id " + id_paciente + " foram excluídas com sucesso.\"}";
                    //se nao for maior que 0 nao existia o usuario no banco de dados
                    } else {
                        response.status(209); // 404 Not Found
                        return "{\"message\": \"O paciente com id " + id_paciente + " não foi encontrado.\"}";
                    }
                } catch (NumberFormatException e) { //algum erro de conversao do id passado na url
                    response.status(400);
                    return "{\"message\": \"ID fornecido está no formato incorreto.\"}" ;
                } catch (Exception e) {
                    response.status(500);
                    return "{\"message\": \"Erro ao processar a requisição.\"}";
                }
            }
        };
    }

    public static Route consultarImunizacaoId(){
        return new Route(){
            public Object handle(Request request, Response response) throws Exception{
                try{
                    //Extrair o id da requisicao http
                    int id = Integer.parseInt(request.queryParams("id"));
    
                    
                    Imunizacao imunizacao = DAOImunizacao.consultarImunizacaoPorId(id);
    
                    if (imunizacao != null){
                        response.status(200); // 200 Ok
                        return new ObjectMapper().writeValueAsString(imunizacao);
                    //se nao for maior que 0 nao existia o usuario no banco de dados
                    } else {
                        response.status(209); // 404 Not Found
                        return "{\"message\": \"O Imunização com id " + id + " não foi encontrado.\"}";
                    }
                } catch (NumberFormatException e) { //algum erro de conversao do id passado na url
                    response.status(400);
                    return "{\"message\": \"ID fornecido está no formato incorreto.\"}" ;
                } catch (Exception e) {
                    response.status(500);
                    return "{\"message\": \"Erro ao processar a requisição.\"}";
                }
            }
        };
    }

    public static Route consultarTodasImunizacoes(){
        return new Route() {
            public Object handle(Request request, Response response) throws Exception {
                try {

                    // Executar o metodo de consultar imunizacao por id
                    Imunizacao imunizacao = DAOImunizacao.consultarTodasImunizacao();

                    if (imunizacao != null) {
                        response.status(200); // 200 Ok
                        return new ObjectMapper().writeValueAsString(imunizacao);
                    } else {
                        response.status(404); // 404 Not Found
                        return "{\"message\": \"Nenhuma imunizacão encotrada\"}";
                    }
                } catch (NumberFormatException e) { // algum erro de conversao do id passado na url
                    response.status(400);
                    return "{\"message\": \"ID fornecido está no formato incorreto.\"}";
                } catch (Exception e) {
                    response.status(500);
                    return "{\"message\": \"Erro ao processar a requisição.\"}";
                }
            }
        };
    }

    public static Route consultarImunizacaoPorPaciente(){
        return new Route() {
            public Object handle(Request request, Response response) throws Exception {
                try {
                    // Extrair o id da requisicao http
                    int id_paciente = Integer.parseInt(request.queryParams("id_paciente"));

                    // Executar o metodo de consultar imunizacao por id
                    Imunizacao imunizacao = DAOImunizacao.consultarImunizacaoPorPaciente(id_paciente);

                    if (imunizacao != null) {
                        response.status(200); // 200 Ok
                        return new ObjectMapper().writeValueAsString(imunizacao);
                    } else {
                        response.status(404); // 404 Not Found
                        return "{\"message\": \"A imunização com id " + id_paciente + " não foi encontrada.\"}";
                    }
                } catch (NumberFormatException e) { // algum erro de conversao do id passado na url
                    response.status(400);
                    return "{\"message\": \"ID fornecido está no formato incorreto.\"}";
                } catch (Exception e) {
                    response.status(500);
                    return "{\"message\": \"Erro ao processar a requisição.\"}";
                }
            }
        };
    }

    public static Route consultarImunizacaoPorIntervalo() {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                try {
                    // Extrair o id da requisicao http
                    String idPacienteParam = request.queryParams("id_paciente");
                    if (idPacienteParam == null) {
                        response.status(400);
                        return "{\"message\": \"ID do paciente não fornecido.\"}";
                    }
                    int id_paciente = Integer.parseInt(idPacienteParam);

                    String dtIniParam = request.queryParams("dt_ini");
                    String dtFimParam = request.queryParams("dt_fim");

                    if(dtIniParam == null || dtFimParam == null){
                        response.status(400);
                        return "{\"message\": \"Data inicial ou final não fornecida.\"}";
                    }

                    // Executar o metodo de consultar imunizacao por id
                    List<Imunizacao> imunizacoes = DAOImunizacao.consultarImunizacoesPorIntervalo(id_paciente, Date.valueOf(dtIniParam), Date.valueOf(dtFimParam));

                    if (imunizacoes != null && !imunizacoes.isEmpty()) {
                        response.status(200); // 200 Ok
                        return new ObjectMapper().writeValueAsString(imunizacoes);
                    } else {
                        response.status(404); // 404 Not Found
                        return "{\"message\": \"Nenhuma imunização encontrada para o paciente com id " + id_paciente + " no período especificado.\"}";
                    }
                } catch (NumberFormatException e) { // algum erro de conversao do id passado na url
                    response.status(400);
                    return "{\"message\": \"ID do paciente fornecido está no formato incorreto.\"}";
                } catch (IllegalArgumentException e){
                    response.status(400);
                    return "{\"message\": \"Data fornecida está no formato incorreto, o formato correto é aaaa-MM-dd.\"}";
                } catch (Exception e) {
                    response.status(500);
                    return "{\"message\": \"Erro ao processar a requisição.\"}";
                }
            }
        };
}   
}
// private int id;
// private int id_paciente;
// private int id_dose;
// private Date data_aplicacao;
// private String fabricante;
// private String lote;
// private String local_aplicacao;
// private String profissional_aplicador;