package br.com.api.routes;

import br.com.api.routes.Rotas;
import br.com.api.service.ServicoImunizacao;
//import br.com.api.service.ServicoUsuario;

import spark.Spark; 

public class Rotas {
    
    public static void processarRotas(){
        //cadastra no spark quais rotas existem e quais metodos 
        //devem ser executados quando cada rota for requisitada
        // Spark.post("/cadastrar", ServicoUsuario.cadastrarUsuario());
        // Spark.get("/consultar/:id", ServicoUsuario.consultarUsuarioPorId());
        // Spark.get("/consultar", ServicoUsuario.consultarTodosUsuarios());
        // Spark.put("/alterar/:id", ServicoUsuario.alterarUsuario());
        // Spark.delete("/excluir/:id", ServicoUsuario.excluirUsuario());
        
        //Rotas Imunização
        Spark.post("/imunizacao/inserir", ServicoImunizacao.cadastrarImunizacao());
        Spark.put("/imunizacao/alterar", ServicoImunizacao.alterarImunizacao());
        Spark.delete("/imunizacao/excluir", ServicoImunizacao.excluirImunizacao());
        Spark.delete("/imunizacao/excluir/paciente", ServicoImunizacao.excluirTodos());
        Spark.get("/imunizacao/consultarId", ServicoImunizacao.consultarImunizacaoId());
        Spark.get("/imunizacao/consultar", ServicoImunizacao.consultarTodasImunizacoes());
        Spark.get("/imunizacao/consultar/paciente", ServicoImunizacao.consultarImunizacaoPorPaciente());
        Spark.get("/imunizacao/consultar/paciente/periodo", ServicoImunizacao.consultarImunizacaoPorIntervalo());

        
        //Spark.get("/consultar/compras/usuario/:id", ServicoUsuario.consultarComprasPorUsuarioIdv1());
        //Spark.get("/consultar/compras/usuario/:id", ServicoUsuario.consultarComprasPorUsuarioIdv2());
        
        //TO DO: Para criar novas rotas, basta adicionar novas linhas seguindo o padrao abaixo, 
        //onde XXXX e o metodo http (post, get, put ou delete), yyyyyy a url que define a rota
        //e ZZZZZ o metodo a ser executado quando a rota for acionada
        //Spark.XXXXX("YYYYYYY", ZZZZZ);
    }
    
    //Para criar novos metodos basta utilizar o esqueleto abaixo
    //Metodo Esqueleto
    //XXXX: Nome do metodo que o usuario quer criar
    //YYYY: Parametros de entrada para o metodo se existirem
    //ZZZZ: Implementação do método
    //QQQQ: Status code do HTTP
    //SSSS: Informação que será retornado
    //
    // public static Route XXXX(YYYY) {
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
}
