package ontologyAPI.ontology;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;

import java.util.Iterator;

public class Basis {

    public static  void  main (String[]args) {

        // 创建使用OWL语言的内存模型
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);

        // 加载模型
        ontModel.read("data/Creatrue.ttl");//.ttl是Turtle格式的文件属于RDF文件

        // 定义一个类作为模型中的Animal类的等价类
        OntClass cls = ontModel.createClass("DongWuClass");
        cls.addComment("the EquivalentClass of Animal..", "EN");//这个方法是什么意思，第一个参数和第二个参数?这个方法是对新建的类添加评语，第一个参数是字符串，第二个参数表示前面字符串是英文

        // 通过完整的URI取得模型中的Animal类
        //这个url在其他电脑上也能打开么？
        OntClass oc = ontModel.getOntClass("http://www.semanticweb.org/zhang/ontologies/2016/10/untitled-ontology-6#Animal");
        oc.addEquivalentClass(cls);//将DongWuClass和Animal视为等级别的类

        // 迭代显示模型中的类，
        Iterator it = ontModel.listClasses();//从模型中拿到所有的类
        while(it.hasNext()){
            OntClass c = (OntClass)it.next();//这里需要强制转型，next（）方法返回的是Object类
            // 如果不是匿名类
            if(!c.isAnon()){
                System.out.print("Class");

                // 获取类的URI，并输出
                // 在输出时，省略命名空间前缀
                System.out.println(c.getModel().getGraph().getPrefixMapping().shortForm(c.getURI()));
            }

            // 处理Animal类
            if(c.getLocalName().equals("Animal")) {
                System.out.println("URI@" + c.getURI());
                System.out.println("Animal's EquivalentClass is" + c.getEquivalentClass());

                // 输出等价类的注释
                System.out.println("[comments:" + c.getEquivalentClass().getComment("EN") + "]");
            }
        }


    }

}
