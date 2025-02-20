package br.com.api.service;

import java.sql.Date;
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
                Long idPaciente = Long.parseLong(request.queryParams("idPaciente"));
                Long idDose = Long.parseLong(request.queryParams("idDose"));
                Date dataAplicacao = Date.valueOf(request.queryParams("dataAplicacao"));
                String fabricante = request.queryParams("fabricante");
                String lote = request.queryParams("lote");
                String localAplicacao = request.queryParams("localAplicacao");
                String profissionalAplicador = request.queryParams("profissionalAplicador");

                //Executar o metodod de adicionar imunizaçao no array list
                Imunizacao imunizacao = new Imunizacao( idPaciente, idDose, dataAplicacao, fabricante, lote, localAplicacao, profissionalAplicador);
                
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
    public static Route alterarImunizacao(){
        return new Route(){
            public Object handle(Request request, Response response) throws Exception{
                
                try{
                    //Extrair os parametros do body da requisicao http
                    Long id = Long.parseLong(request.params(":id"));
                    Long idPaciente = Long.parseLong(request.queryParams("idPaciente"));
                    Long idDose = Long.parseLong(request.queryParams("idDose"));
                    Date dataAplicacao = Date.valueOf(request.queryParams("dataAplicacao"));
                    String fabricante = request.queryParams("fabricante");
                    String lote = request.queryParams("lote");
                    String localAplicacao = request.queryParams("localAplicacao");
                    String profissionalAplicador = request.queryParams("profissionalAplicador");
    
                    //Executar o metodod de adicionar imunizaçao no array list
                    Imunizacao imunizacao = new Imunizacao(id, idPaciente, idDose, dataAplicacao, fabricante, lote, localAplicacao, profissionalAplicador);
                    
                    
                    int qtdeLinhasAlteradas = DAOImunizacao.alterarImunizacao(imunizacao);

                    if (qtdeLinhasAlteradas > 0){
                        response.status(200); // 200 Ok
                        return "{\"message\": \"Usuário com id " + id + " foi atualizado com sucesso.\"}";
                    //se nao for maior que 0 nao existia o usuario no banco de dados
                    } else {
                        response.status(209); // 404 Not Found
                        return "{\"message\": \"O usuário com id " + id + " não foi encontrado.\"}";
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

    public static Route excluirImunizacao(){
        return new Route(){
            public Object handle(Request request, Response response) throws Exception{
                try{
                    //Extrair o id da requisicao http
                    Long id = Long.parseLong(request.params(":id"));
    
                    //Executar o metodo de excluir usuario no array list
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
                    Long idPaciente = Long.parseLong(request.queryParams("idPaciente"));
    
                    //Executar o metodo de excluir usuario no array list
                    int qtdeLinhasExcluidas = DAOImunizacao.excluirTodos(idPaciente);
    
                    if (qtdeLinhasExcluidas > 0){
                        response.status(200); // 200 Ok
                        return "{\"message\": \"Imunizações do paciente com id " + idPaciente + " foram excluídas com sucesso.\"}";
                    //se nao for maior que 0 nao existia o usuario no banco de dados
                    } else {
                        response.status(209); // 404 Not Found
                        return "{\"message\": \"O paciente com id " + idPaciente + " não foi encontrado.\"}";
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
                    Long id = Long.parseLong(request.params(":id"));
    
                    //Executar o metodo de excluir usuario no array list
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
                    // Extrair o id da requisicao http
                    Long id = Long.parseLong(request.params(":id"));

                    // Executar o metodo de consultar imunizacao por id
                    Imunizacao imunizacao = DAOImunizacao.consultarTodasImunizacaoPorId(id);

                    if (imunizacao != null) {
                        response.status(200); // 200 Ok
                        return new ObjectMapper().writeValueAsString(imunizacao);
                    } else {
                        response.status(404); // 404 Not Found
                        return "{\"message\": \"A imunização com id " + id + " não foi encontrada.\"}";
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
                    Long id = Long.parseLong(request.params(":id"));

                    // Executar o metodo de consultar imunizacao por id
                    Imunizacao imunizacao = DAOImunizacao.consultarImunizacaoPorPaciente(id);

                    if (imunizacao != null) {
                        response.status(200); // 200 Ok
                        return new ObjectMapper().writeValueAsString(imunizacao);
                    } else {
                        response.status(404); // 404 Not Found
                        return "{\"message\": \"A imunização com id " + id + " não foi encontrada.\"}";
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

    public static Route consultarImunizacaoPorIntervalo(){
        return new Route() {
            public Object handle(Request request, Response response) throws Exception {
                try {
                    // Extrair o id da requisicao http
                    Long id = Long.parseLong(request.params(":id"));

                    // Executar o metodo de consultar imunizacao por id
                    Imunizacao imunizacao = DAOImunizacao.consultarImunizacoesPorIntervalo(id, Date.valueOf(request.params(":dt_ini")), Date.valueOf(request.params(":dt_fim")));

                    if (imunizacao != null) {
                        response.status(200); // 200 Ok
                        return new ObjectMapper().writeValueAsString(imunizacao);
                    } else {
                        response.status(404); // 404 Not Found
                        return "{\"message\": \"A imunização com id " + id + " não foi encontrada.\"}";
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
}
// private long id;
// private long idPaciente;
// private long idDose;
// private Date dataAplicacao;
// private String fabricante;
// private String lote;
// private String localAplicacao;
// private String profissionalAplicador;