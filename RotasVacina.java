package br.com.api;

import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.api.RotasVacina;
import br.com.api.Vacinas.PublicoAlvo;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

public class RotasVacina {
    
    public static void processarRotas(ServicoUsuario servicoUsuario){
        //configura o spark com quais metodos devem ser executados 
        //quando cada rota for requisitada
        Spark.get("/vacinas/consultar", consultarTodasVacinas(servicoUsuario));
        Spark.get("/vacinas/consultar/faixa_etaria/:faixa", consultarVacinasPorFaixa(servicoUsuario));
        Spark.get("/vacinas/consultar/idade_maior/:meses", consultarVacinasRecomendadas(servicoUsuario));
        Spark.get("/vacinas/consultar/nao_aplicaveis/paciente/:id", consultarVacinasNaoAplicaveis(servicoUsuario));
        Spark.post("/vacinas/adicionar",adicionarVacina(servicoUsuario));
        
    }
    

    //Metodo Esqueleto
    //XXXX: Nome do metodo que o usuario quer criar
    //YYYY: Parametros de entrada para o metodo
    //ZZZZ: Implementação do método
    //QQQQ: Status code do HTTP
    //SSSS: Informação que será retornado
    //
    // private static Route XXXX(YYYY) {
    //     return new Route() {
    //         @Override
    //         public Object handle(Request request, Response response) throws Exception {
    //
    //             ZZZZ
    //
    //             response.status(QQQQ); 
    //             return SSSS;
    //         }
    //     };
    // }

    private static Route adicionarVacina(ServicoUsuario servicoUsuario) {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                // Classe para converter objeto para JSON
                ObjectMapper converteJson = new ObjectMapper();
    
                // Extrai os parâmetros do HTTP body
                String vacina = request.queryParams("vacina");
                String limiteAplicacaoStr = request.queryParams("limiteAplicacaoStr");
                String publicoAlvoStr = request.queryParams("publicoAlvoStr");
    
                // Converte o parâmetro 'limiteAplicacao' para int
                int limiteAplicacao = Integer.parseInt(limiteAplicacaoStr);
    
                // Converte a string para o tipo PublicoAlvo (supondo que PublicoAlvo seja um enum)
                PublicoAlvo publicoAlvo = PublicoAlvo.valueOf(publicoAlvoStr);
    
                // Executa o método de adicionar a vacina no array list
                Vacinas usuario = servicoUsuario.adicionar(vacina, limiteAplicacao, publicoAlvo);
    
                // Retorna o objeto convertido para JSON
                return converteJson.writeValueAsString(usuario);
            }
        };
    }
    
    
    
    private static Route consultarTodasVacinas(ServicoUsuario servicoUsuario) {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
    
                ObjectMapper converteJson = new ObjectMapper();


                ArrayList<Vacinas> vacinas = (ArrayList<Vacinas>) servicoUsuario.consultarVacinas();

                if (vacinas.isEmpty()) {
                    response.status(204);
                    return "{\"message\": \"Nenhuma vacina encontrada no banco de dados.\"}";
                }
                else {
                    response.status(200); 
                    return converteJson.writeValueAsString(vacinas);
            }
    
            }
        };
    }

    
    private static Route consultarVacinasPorFaixa(ServicoUsuario servicoUsuario) {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
    
                ObjectMapper converteJson = new ObjectMapper();

                //extrai o parametro id da URL
                String publicoAlvo = request.params(":faixa");


                try {
                PublicoAlvo faixa = PublicoAlvo.valueOf(publicoAlvo.toUpperCase());
                 Vacinas vacinas = servicoUsuario.consultarPorFaixa(faixa);
                


                if (vacinas != null) {

                    response.status(200); // 200 OK
                    return converteJson.writeValueAsString(vacinas);
                } else {
                    
                    response.status(204); 
                    return"";
                    
                }}catch(IllegalArgumentException e){
                    response.status(400);
                    return "{\"message\": \"Faixa etária inválida!\"}";
                }
                
               
            }
        };
    }
    

    private static Route consultarVacinasRecomendadas(ServicoUsuario servicoUsuario) {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
    
                ObjectMapper converteJson =  new ObjectMapper();

                //extrai o parametro id da URL
                String limiteAplicacao = request.params(":meses");

                
                try {
                    int limite = Integer.parseInt(limiteAplicacao);
                   Vacinas vacinas = servicoUsuario.consultarVacinasRecomendadas(limite);

                    if (vacinas == null) {
    
                        response.status(200); 

                        return "{\"message\": \"Nenhuma vacina encontrada para essa faixa-etária no banco de dados.\"}";
                    } else{
                        response.status(200);
                        return converteJson.writeValueAsString(new ArrayList<>());
                        
                        
            }}catch (NumberFormatException e) {
                    response.status(400);
                    return "{\"error\": \"O parâmetro 'meses' deve ser um número inteiro válido.\"}";
                }
                

                } 
               
            };
        }
    

    private static Route consultarVacinasNaoAplicaveis(ServicoUsuario servicoUsuario) {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                ObjectMapper converteJson = new ObjectMapper();

                //extrai o parametro id da URL
                String id = request.params(":id");

                //busca o contato no array list pela id
                Vacinas vacinas = servicoUsuario.consultarVacinasNaoAplicaveis(id);

                if (vacinas != null) {

                    response.status(200); // 200 OK
                    return converteJson.writeValueAsString(vacinas);
                } else {
                    //defini o http status code
                    response.status(404); // 204 No Content

                    return converteJson.writeValueAsString(new ArrayList<>());
                }
            }
        };
    }

    

   
    }

