package ontologyAPI;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;

public class CreateOntology {
    public static void main(String[] args){

    }

    //读取本体模型
    public OntModel ReadOntology(String url){
        /**默认创造的模型有以下的能力
         * OWL-Full language
         * in-memory storage
         * RDFS inference, which principally produces entailments from the sub-class and sub-property hierarchies.
         */
        OntModel model = ModelFactory.createOntologyModel();//最简单创建model的方式
        //完全没有推理能力的模型
        OntModel model_owl_men = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM );
        return model;
    }
}
