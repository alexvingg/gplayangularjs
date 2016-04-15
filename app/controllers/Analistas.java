package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.Analista;
import models.Cargo;
import play.mvc.Controller;
import util.HibernateProxyTypeAdapter;
import util.ObjetoRetorno;

import java.util.List;

/**
 * Created by alexcosta on 4/7/16.
 */
public class Analistas extends Controller {

    public static void index(){
        render();
    }

    public static void listar(){
        List<Analista> analistasList = Analista.findAll();

        ObjetoRetorno objetoRetorno = new ObjetoRetorno(null, analistasList, false);

        GsonBuilder gson = new GsonBuilder();
        gson.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY);
        String json = gson.create().toJson(objetoRetorno);

        renderJSON(json);
    }

    public static void save(){
        Analista analista = new GsonBuilder().create().fromJson(request.params.get("body"), Analista.class);
        analista.save();
        ObjetoRetorno objetoRetorno = new ObjetoRetorno("Registro incluído com sucesso", analista, false);
        renderJSON(objetoRetorno);
    }

    public static void delete(Long id) {
        ObjetoRetorno ob = new ObjetoRetorno();
        try {
            Analista analista = Analista.findById(id);
            analista.delete();
        } catch (Exception ex){
            ob.setMensagem("Erro ao excluir registro");
            ob.setError(true);
            renderJSON(ob);
        }
        ob.setError(false);
        ob.setMensagem("Registro excluido com sucesso !");
        renderJSON(ob);
    }

    public static void edit(){
        Analista analista = new GsonBuilder().create().fromJson(request.params.get("body"), Analista.class);
        Analista analistaVo = Analista.findById(analista.id);

        analistaVo.nome = analista.nome;
        analistaVo.especialidade = analista.especialidade;
        analistaVo.cargo = analista.cargo;

        analistaVo.save();
        ObjetoRetorno objetoRetorno = new ObjetoRetorno("Registro alterado com sucesso", analistaVo, false);

        GsonBuilder gson = new GsonBuilder();
        gson.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY);
        String json = gson.create().toJson(objetoRetorno);

        renderJSON(json);
    }

}
