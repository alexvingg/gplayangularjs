package util;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

import java.util.Date;

public class SerializeUtil {

  private static final DateTransformer DATE_TRANSFORMER = new DateTransformer("dd/MM/yyyy");

  public static JSONSerializer serializerGeral(){



    return new JSONSerializer().transform(DATE_TRANSFORMER, Date.class)
    .exclude("*.class","*.entityId","*.persistent")
    .include("*");
    
  }
  
  public static JSONSerializer serializerExcludeGeral(){
    
    return new JSONSerializer()
    .exclude("*.class","*.entityId","*.persistent").transform(DATE_TRANSFORMER, Date.class);
    
  }
  
}
