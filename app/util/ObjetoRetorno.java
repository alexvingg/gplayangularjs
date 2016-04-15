package util;

import com.google.gson.annotations.Expose;

/**
 * Created by alexcosta on 4/6/16.
 */
public class ObjetoRetorno {

    @Expose
    private String mensagem;

    @Expose
    private Object objeto;

    @Expose
    private boolean isError;

    public ObjetoRetorno() {
    }

    public ObjetoRetorno(String mensagem, Object objeto, boolean isError) {
        this.mensagem = mensagem;
        this.objeto = objeto;
        this.isError = isError;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Object getObjeto() {
        return objeto;
    }

    public void setObjeto(Object objeto) {
        this.objeto = objeto;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }
}
