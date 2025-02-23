package br.com.api.routes;

import static spark.Spark.*;
import com.google.gson.Gson;
import java.util.List;
import br.com.api.dao.DAOVacina;
import br.com.api.model.Vacinas;
import com.google.gson.JsonObject;
    

public class RotasVacina{

public static void processarRotas() {
    
    get("/vacinas/consultar", (req, res) -> {
    res.type("application/json");
        Gson gson = new Gson();
    try {
        List<Vacinas> vacinas = DAOVacina.consultarTodasVacinas();
        if (vacinas.isEmpty()) {
                res.status(404);
    JsonObject jsonResponse = new JsonObject();
    jsonResponse.addProperty("message", "Nenhuma vacina encontrada.");
    return gson.toJson(jsonResponse);
    }
                    res.status(200);
                return gson.toJson(vacinas);
    } catch (Exception e) {
    res.status(500);
    JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("error", e.getMessage());
    return gson.toJson(jsonResponse);
    }
    });
        
    get("/vacinas/consultar/faixa_etaria/:faixa", (req, res) -> {
    res.type("application/json");
        Gson gson = new Gson();
        String faixa = req.params(":faixa").toUpperCase();
        try {
        List<Vacinas> vacinas = DAOVacina.consultarVacinasPorFaixa(faixa);
        if (vacinas.isEmpty()) {
        res.status(404);
    JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("message", "Nenhuma vacina encontrada para a faixa " + faixa);
        return gson.toJson(jsonResponse);
        }
    res.status(200);
    return gson.toJson(vacinas);
    } catch (Exception e) {
    res.status(500);
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("error", e.getMessage());
    return gson.toJson(jsonResponse);
    }
    });
        
        
        get("/vacinas/consultar/idade_maior/:meses", (req, res) -> {
        res.type("application/json");
    Gson gson = new Gson();
    try {
        int meses = Integer.parseInt(req.params(":meses"));
        List<Vacinas> vacinas = DAOVacina.consultarVacinasPorIdade(meses);
        if (vacinas.isEmpty()) {
        res.status(404);
    JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("message", "Nenhuma vacina recomendada encontrada para idade acima de " + meses + " meses.");
        return gson.toJson(jsonResponse);
    }
    res.status(200);
        return gson.toJson(vacinas);
        } catch (NumberFormatException nfe) {
    res.status(400);
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("error", "O parâmetro 'meses' deve ser um número inteiro.");
        return gson.toJson(jsonResponse);
        } catch (Exception e) {
        res.status(500);
    JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("error", e.getMessage());
        return gson.toJson(jsonResponse);
        }
        });
        
    get("/vacinas/consultar/nao_aplicaveis/paciente/:id", (req, res) -> {
    res.type("application/json");
        Gson gson = new Gson();
    try {
    int idPaciente = Integer.parseInt(req.params(":id"));
    int idadePacienteMeses = 180; // Lógica fictícia. Deve ser ajustada.
    List<Vacinas> vacinas = DAOVacina.consultarVacinasNaoAplicaveis(idadePacienteMeses);
    if (vacinas.isEmpty()) {
    res.status(404);
        JsonObject jsonResponse = new JsonObject();
    jsonResponse.addProperty("message", "Nenhuma vacina não aplicável encontrada para o paciente de id " + idPaciente);
    return gson.toJson(jsonResponse);
        }
        res.status(200);
    return gson.toJson(vacinas);
    } catch (NumberFormatException nfe) {
    res.status(400);
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("error", "O parâmetro 'id' deve ser um número inteiro.");
        return gson.toJson(jsonResponse);
        } catch (Exception e) {
        res.status(500);
        JsonObject jsonResponse = new JsonObject();
    jsonResponse.addProperty("error", e.getMessage());
    return gson.toJson(jsonResponse);
        }
    });
    }
}


