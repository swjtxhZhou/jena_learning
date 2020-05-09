package rdf;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.VCARD;

public class RDFBasis {
    /**
     * 自主创建一个RDF模型，需要自己定义url
     */
    private String personURI = "http://somewhere/JohnSmith";
    private String fullName = "John Smith";
    private String givenName = "John";
    private String familyName = "Smith";
    private Model model;

    public Model getModel(){
        return this.model;
    }

    /**
     * 构造函数
     * 从ModelFactory中拿默认的model模型
     */
    public RDFBasis(){
        this.model = ModelFactory.createDefaultModel();
        //resource表示这是一个RDF资源，可以往里面添加Property
        Resource johnSmith = model.createResource(personURI);
        johnSmith.addProperty(VCARD.FN, fullName)
                .addProperty(VCARD.N, model.createResource()
                        .addProperty(VCARD.Given, givenName)
                        .addProperty(VCARD.Family, familyName));

    }

    public static void main(String[] args) {

        Model model = new RDFBasis().getModel();
        model.write(System.out);
    }
}
