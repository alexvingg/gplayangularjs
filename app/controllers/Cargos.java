package controllers;

import com.google.gson.GsonBuilder;
import models.Cargo;
import play.mvc.Controller;
import util.ObjetoRetorno;

import java.util.List;

/**
 * Created by alexcosta on 4/6/16.
 */
public class Cargos extends Controller {

    public static void index(){
        render("main.html");
    }

    public static void listar(){
        List<Cargos> cargosList = Cargo.all().fetch();
        ObjetoRetorno objetoRetorno = new ObjetoRetorno(null, cargosList, false);
        renderJSON(objetoRetorno);
    }

    public static void save(){
        Cargo cargo = new GsonBuilder().create().fromJson(request.params.get("body"), Cargo.class);
        cargo.save();
        ObjetoRetorno objetoRetorno = new ObjetoRetorno("Registro incluído com sucesso", cargo, false);
        renderJSON(objetoRetorno);
    }

    public static void delete(Long id) {

        ObjetoRetorno ob = new ObjetoRetorno();

        try {
            Cargo c = Cargo.findById(id);
            c.delete();
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
        Cargo cargo = new GsonBuilder().create().fromJson(request.params.get("body"), Cargo.class);

        Cargo c = Cargo.findById(cargo.id);
        c.nome = cargo.nome;
        c.save();
        ObjetoRetorno objetoRetorno = new ObjetoRetorno("Registro alterado com sucesso", c, false);
        renderJSON(objetoRetorno);
    }
}
