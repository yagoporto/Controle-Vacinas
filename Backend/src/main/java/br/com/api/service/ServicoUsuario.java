package br.com.api.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.api.config.Conexao;
import br.com.api.dao.DAOUsuario;
import br.com.api.dto.DTOUsuarioPedido;
import br.com.api.model.Usuario;
import spark.Request;
import spark.Response;
import spark.Route;

public class ServicoUsuario {

    static class UsuarioPedido{
        public int idPedido;
        public String nomeCliente;
        public String email;
        public String dataCompra;
        public int qtde;
        public String descricaoProduto;
        public float valorProduto;
        public float valorTotalProduto;
    }

    // Método para lidar com a rota de adicionar usuário
    public static Route cadastrarUsuario() {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                //extrai os parametros do boddy da requisicao http  
                String nome = request.queryParams("nome");
                String email = request.queryParams("email");

                //executa o metodo de adicionar o contato no array list
                Usuario usuario = new Usuario(nome, email);

                try {
                    //passa o objeto para o DAO realizar a insercao no banco de dados
                    //e recebe o id gerado no banco de dados
                    int idUsuario = DAOUsuario.inserir(usuario);
                    
                    //defini o status code do httpd
                    response.status(201); // 201 Created
                    
                    //possiveis opcoes Classe Anonima, HashMap, Classe interna, 
                    // Object mensagem = new Object() {
                    //     public String message = "Usuário criado com o ID " + idUsuario + " com sucesso." ;
                    // };
                    
                    // //retorna um array list vazio no formato json
                    // return converteJson.writeValueAsString(mensagem);
                    
                    //retorna o id criado e retorna via http response
                    return "{\"message\": \"Usuário criado com o ID " + idUsuario + " com sucesso.\"}" ;
                        
                } catch (Exception e) {
                    response.status(500); // 500 Erro no servidor
                    //Retorna a excecao gerado pelo DAOUsuario caso exista
                    return "{\"message\": \"" + e.getMessage() + "\"}" ;
                }
            }
        };
    }


    // Método para lidar com a rota de buscar usuário por ID
    public static Route consultarUsuarioPorId() {
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
                    Usuario usuario = DAOUsuario.consultarPorID(id);

                    if (usuario != null) {
                        //defini o http status code
                        response.status(200); // 200 OK

                        //retorna o objeto encontrado no formato json
                        return converteJson.writeValueAsString(usuario);
                    } else {
                        //defini o http status code
                        response.status(209); // 209 Consulta realizada com sucesso mas nao tem nenhum registro no banco
                        return "{\"message\": \"Nenhum usuário encontrado com este ID.\"}" ;
                    }
                } catch (NumberFormatException e) {
                    //defini o http status code
                    response.status(400); // 400 Requisicao incorreta, foi fornecido um id que nao pode ser convertido para inteiro
                    return "{\"message\": \"ID fornecido está no formato incorreto.\"}" ;
                }
            }
        };
    }
    
    // Método para lidar com a rota de buscar todos os usuários
    public static Route consultarTodosUsuarios() {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                ObjectMapper converteJson = new ObjectMapper();

                //busca todos os contatos cadastrados no array list
                //é necessário realizar um cast explicito para converter o retorno 
                //do servicoUsuario
                ArrayList<Usuario> usuarios = DAOUsuario.consultarTodos();

                //se o arraylist estiver vazio
                if (usuarios.isEmpty()) {
                    response.status(200); // 209 
                    return "{\"message\": \"Nenhum usuário encontrado no banco de dados.\"}";
                } else {
                    //se nao estiver vazio devolve o arraylist convertido para json
                    response.status(200); // 200 Ok
                    return converteJson.writeValueAsString(usuarios);
                }
            }
        };
    }
    
    // Método para lidar com a rota de atualizar usuário
    public static Route alterarUsuario() {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                try {
                    //extrai os parametros do boddy da requisicao http  
                    int id = Integer.parseInt(request.params(":id"));
                    String nome = request.queryParams("nome");
                    String email = request.queryParams("email");
    
                    //cria o objeto usuario na memoria
                    Usuario usuario = new Usuario(id, nome, email);
    
                    //envia o objeto para ser inserido no banco de dados pelo DAO 
                    //e armazena a quantidade de linhas alteradas
                    int qtdeLinhasAlteradas = DAOUsuario.atualizar(usuario);
    
                    //se a quantidade de linhas alteradas for maior que 0 significa se existia o usuario no banco de dados
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

    // Método para lidar com a rota de excluir usuário
    public static Route excluirUsuario() {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                try {     
                    //extrai o parametro id da URL (header http), e converte para inteiro
                    int id = Integer.parseInt(request.queryParams("id"));
                    
                    //envia o id a ser excluida para o DAO e recebe a quantidade de linhas excluidas
                    int linhasExcluidas = DAOUsuario.excluirPorID(id);

                    //se a quantidade de linhas for maior que 0 significa que o usuario existia no banco de dados
                    if (linhasExcluidas > 0) {
                        response.status(200); //exclusão com sucesso
                        return "{\"message\": \"Usuário com id " + id + " foi excluído com sucesso.\"}" ;
                    //se nao forma maior que 0 o usuario nao existia no banco de dados
                    } else {
                        response.status(209); //id não encontrado
                        return "{\"message\": \"Usuário com id " + id + " foi encontrado no banco de dados.\"}" ;
                    }
                } catch (NumberFormatException e) { //alguma excecao na conversado do id fornecido na URL
                    response.status(400);
                    return "{\"message\": \"ID fornecido está no formato incorreto.\"}" ;
                }
            }
        };
    }

    public static Route consultarComprasPorUsuarioIdv1() {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                //classe para converter objeto para json
                ObjectMapper converteJson = new ObjectMapper();

                int id;

                try {
                    //extrai o parametro id da URL (header http), e converte para inteiro
                    id = Integer.parseInt(request.params(":id"));

                    //busca os pedidos do usuario
                    ArrayList<DTOUsuarioPedido> listaCompras = DAOUsuario.consultarComprasPorUsuario(id);

                    if (listaCompras != null) {
                        //defini o http status code
                        response.status(200); // 200 OK

                        //retorna o objeto encontrado no formato json
                        return converteJson.writeValueAsString(listaCompras);
                    } else {
                        //defini o http status code
                        response.status(209); // 209 Consulta realizada com sucesso mas nao tem nenhum registro no banco
                        return "{\"message\": \"Nenhum pedido encontrado para este usuário\"}" ;
                    }
                } catch (NumberFormatException e) {
                    //defini o http status code
                    response.status(400); // 400 Requisicao incorreta, foi fornecido um id que nao pode ser convertido para inteiro
                    return "{\"message\": \"ID fornecido está no formato incorreto.\"}" ;
                }
            }
        };
    }

    
    public static Route consultarComprasPorUsuarioIdv2() {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                // Classe para converter objeto para JSON
                ObjectMapper converteJson = new ObjectMapper();

                int id;

                // Define o SQL
                String sql = """
                    SELECT 	p.id AS id_pedido,
                            u.nome as nome_cliente,
                            u.email,
                            p.data_compra,
                            ip.qtde,
                            pr.descricao as nome_produto,
                            pr.preco_unitario,
                            ROUND((pr.preco_unitario * ip.qtde),2) as total_produto
                    FROM pedido AS p
                    INNER JOIN usuario AS u ON u.id = p.id_usuario
                    INNER JOIN item_pedido AS ip ON ip.id_pedido = p.id
                    INNER JOIN produto AS pr ON pr.id = ip.id_produto
                    WHERE u.id = ?
                """;

                try (
                    Connection conexao = Conexao.getConexao(); 
                    PreparedStatement comando = conexao.prepareStatement(sql)
                ) {
                    // Extrai o parâmetro id da URL (header HTTP) e converte para inteiro
                    id = Integer.parseInt(request.params(":id"));

                    // Substitui a ? pelo código do usuário
                    comando.setInt(1, id);

                    // Executa o comando SQL
                    ResultSet resultado = comando.executeQuery();

                    // Lista para armazenar os pedidos
                    ArrayList<UsuarioPedido> listaPedidos = new ArrayList<UsuarioPedido>(); 

                    while (resultado.next()) {
                        UsuarioPedido usuarioPedido = new UsuarioPedido();
                        usuarioPedido.idPedido = resultado.getInt("id_pedido");
                        usuarioPedido.nomeCliente = resultado.getString("nome_cliente");
                        usuarioPedido.email = resultado.getString("email");
                        usuarioPedido.dataCompra = resultado.getString("data_compra");
                        usuarioPedido.qtde = resultado.getInt("qtde");
                        usuarioPedido.descricaoProduto = resultado.getString("nome_produto");
                        usuarioPedido.valorProduto = resultado.getFloat("preco_unitario");
                        usuarioPedido.valorTotalProduto = resultado.getFloat("total_produto");

                        // Adiciona o pedido na lista
                        listaPedidos.add(usuarioPedido);
                    }

                    if (!listaPedidos.isEmpty()) {
                        // Define o HTTP status code
                        response.status(200); // 200 OK

                        // Retorna a lista de pedidos no formato JSON
                        return converteJson.writeValueAsString(listaPedidos);
                    } else {
                        // Define o HTTP status code
                        response.status(209); // 209 Consulta realizada com sucesso mas não tem nenhum registro no banco
                        return "{\"message\": \"Nenhum pedido encontrado para este usuário\"}";
                    }
                } catch (NumberFormatException e) {
                    // Define o HTTP status code
                    response.status(400); // 400 Requisição incorreta, foi fornecido um ID que não pode ser convertido para inteiro
                    return "{\"message\": \"ID fornecido está no formato incorreto.\"}";
                }
            }
        };
    }
}
