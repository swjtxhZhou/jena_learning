package TDB;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.tdb.TDB;
import org.apache.jena.tdb.TDBFactory;

public class TDBOwlWriteExample {
    public static final String DIR = "dataset";
    public static String uri = "urn:x-hp:eg/";
    public static void WriteOwlFile(Dataset dataset) {
        String owlFile = "data/owlDemoSchema.owl";

        dataset.begin(ReadWrite.WRITE);
        try {
            OntModel ontModel = ModelFactory.createOntologyModel();
            ontModel.read(owlFile);
            dataset.addNamedModel(uri, ontModel);
            dataset.commit();
        }finally {
            dataset.end();
        }
    }
    public static void ReadOwlFile(Dataset  dataset){
        dataset.begin(ReadWrite.READ);
        Model model = dataset.getNamedModel(uri);
        model.write(System.out);
        dataset.end();
    }
    public static void TDBOwlTransaction(){
        Dataset dataset = TDBFactory.createDataset(DIR);
        WriteOwlFile(dataset);
        ReadOwlFile(dataset);
        TDB.sync(dataset);
        TDB.closedown();
    }
    public static void main(String[] args){
        TDBOwlTransaction();
    }
}
