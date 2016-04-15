package controllers;

import com.google.gson.GsonBuilder;
import models.Projeto;
import models.Requisito;
import org.apache.commons.codec.binary.Base64;
import play.mvc.Controller;
import util.DateDeserializer;
import util.ObjetoRetorno;
import util.SerializeUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * Created by alexcosta on 4/7/16.
 */
public class Projetos extends Controller {

    public static void listar() throws NoSuchFieldException, ClassNotFoundException {
        List<Projeto> projetoList = Projeto.findAll();
        ObjetoRetorno objetoRetorno = new ObjetoRetorno(null, projetoList, false);
        renderJSON(SerializeUtil.serializerExcludeGeral().include("objeto.analistas").exclude("objeto.requisitos").serialize(objetoRetorno));
    }

    public static void save(){
        GsonBuilder builder = new GsonBuilder();

        builder.registerTypeAdapter(Date.class, new DateDeserializer());

        Projeto projeto = builder.create().fromJson(request.params.get("body"), Projeto.class);

        for (Requisito requisito : projeto.requisitos) {
            requisito.projeto = projeto;
            projeto.requisitos.add(requisito);
        }

        projeto.save();


        ObjetoRetorno objetoRetorno = new ObjetoRetorno("Registro incluído com sucesso", null, false);
        renderJSON(objetoRetorno);
    }

    public static void delete(Long id) {

        ObjetoRetorno ob = new ObjetoRetorno();

        try {
            Projeto projeto = Projeto.findById(id);
            projeto.delete();
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
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, new DateDeserializer());
        Projeto projeto = builder.create().fromJson(request.params.get("body"), Projeto.class);

        Projeto projetoVO = Projeto.findById(projeto.id);
        projetoVO.nome = projeto.nome;
        projetoVO.dataInicio = projeto.dataInicio;
        projetoVO.dataFim = projeto.dataFim;
        projetoVO.descricao = projeto.descricao;
        projetoVO.analistas = projeto.analistas;

        projetoVO.save();

        ObjetoRetorno objetoRetorno = new ObjetoRetorno("Registro alterado com sucesso", null, false);
        renderJSON(SerializeUtil.serializerGeral().serialize(objetoRetorno));
    }

    public static void download() throws IOException {

        File f = new File("/Users/alexcosta/Documents/Pessoal/Alex_Costa.pdf");

        byte[] bytes = loadFile(f);
        byte[] encoded = Base64.encodeBase64(bytes);
        String encodedString = new String(encoded);

        renderText(encodedString);
    }

    private static byte[] loadFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);

        long length = file.length();
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }
        byte[] bytes = new byte[(int)length];

        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }

        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }

        is.close();
        return bytes;
    }

}
