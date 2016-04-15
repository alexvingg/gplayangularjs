package models;

import play.data.validation.MinSize;
import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Entity;

/**
 * Created by alexcosta on 4/6/16.
 */
@Entity(name="cargos")
public class Cargo extends Model {

    @Required(message="O campo Nome é obrigatório")
    @MinSize(value = 2)
    public String nome;


}