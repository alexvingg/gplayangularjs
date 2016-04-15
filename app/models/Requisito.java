package models;

import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * Created by alexcosta on 4/7/16.
 */
@Entity(name="requisitos")
public class Requisito  extends Model {

    @Required(message = "O campo Nome é obrigatório")
    public String nome;

    public String descricao;

    @ManyToOne(fetch = FetchType.LAZY)
    public Projeto projeto;
}