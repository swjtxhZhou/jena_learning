package TDB;

import org.apache.jena.base.Sys;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.tdb.TDB;
import org.apache.jena.tdb.TDBFactory;

public class TDBDataSetReadExample {
    public static final String DIR = "dataset";
    public static void main(String[] args){
        Dataset dataset = TDBFactory.createDataset(DIR);
        dataset.begin(ReadWrite.READ);
        Model modle = dataset.getDefaultModel();
        modle.write(System.out);
        dataset.end();
        TDB.closedown();
    }
}
