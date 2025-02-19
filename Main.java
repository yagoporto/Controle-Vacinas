package br.com.api;

import spark.Spark; 
import spark.Request;
import spark.Response;
import spark.Route;

public class Main {
    
    private static final ServicoUsuario servicoUsuario = new ServicoUsuario();
    
    public static void main(String[] args) {
        Spark.port(8080);

        //Habilitar CORS
        //Assista https://www.youtube.com/watch?v=1V1qkh6K8Gg para entender o que é
        Spark.options("/*", new Route() {
            @Override
            public Object handle(Request requisicaoHttp, Response respostaHttp) throws Exception {

                String accessControlRequestHeaders = requisicaoHttp.headers("Access-Control-Request-Headers");
                
                if (accessControlRequestHeaders != null) 
                    respostaHttp.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
                
                String accessControlRequestMethod = requisicaoHttp.headers("Access-Control-Request-Method");

                if (accessControlRequestMethod != null) 
                    respostaHttp.header("Access-Control-Allow-Methods", accessControlRequestMethod);
                
                return "OK";
            }
        });

        //Informando o Browser que é aceito os metodos HTTP OPTIONS, GET, POST, PUT, DELETE para qualquer endereço
        Spark.before(new spark.Filter() {
            @Override
            public void handle(Request requisicaoHttp, Response respostaHttp) throws Exception {
                respostaHttp.header("Access-Control-Allow-Origin", "*"); // Permite todas as origens
                respostaHttp.header("Access-Control-Allow-Methods", "OPTIONS, GET, POST, PUT, DELETE");
                respostaHttp.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
            }
        });

        RotasVacina.processarRotas(servicoUsuario);

    }
}