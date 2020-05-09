package TDB;

import org.apache.jena.base.Sys;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.tdb2.TDB2;
import org.apache.jena.tdb.TDB;
import org.apache.jena.tdb2.TDB2Factory;
import org.apache.jena.util.FileManager;

import javax.xml.crypto.Data;

public class TDBDataSetWriteExample {
    public static final String DIR="dataset";
    static final String uri="http://xmlns.com/foaf/0.1/";
    //拿到指定位置数据库的dataSet
    public static void WriteDataset(Dataset dataset){
        dataset.begin(ReadWrite.WRITE);
        try{
            Model model = FileManager.get().loadModel("data/sparql-example.ttl");
//            dataset.addNamedModel(uri,model);
            Model model1 = dataset.getDefaultModel();
            //这个方法不能往dataset中添加新的model
//            dataset.setDefaultModel(model);
            model1.add(model);
//            write(model);
            dataset.commit();
        }finally{
            dataset.end();
        }
    }
    public static void ReadDataSet(Dataset dataset){
        dataset.begin(ReadWrite.READ);
//        Model model = dataset.getNamedModel(uri);
        Model model = dataset.getDefaultModel();
        model.write(System.out);
        dataset.end();
    }
    public static void WriteATTL2TDB(){

        //把目标文件写入到model中
        Dataset dataset = TDBFactory.createDataset(DIR);
        WriteDataset(dataset);
        ReadDataSet(dataset);
        // 保存到磁盘，避免缓存数据丢失
        TDB.sync(dataset);

        // 释放所有TDB占有的系统资源
        TDB.closedown();
    }
    public static void connect() {
        System.out.println("连接数据库...");
        Dataset dataset = TDBFactory.createDataset(DIR);
        // 打印出数据集
        ReadDataSet(dataset);

        // 释放所有TDB占有的系统资源
        TDB.closedown();
    }
    public static void getNamedModel(String uri){
        /**
         * 如果不是以NamedModel加入到DataSet中的话是无法以uri反向获取到NamedSpace的
         */
        Dataset dataset = TDBFactory.createDataset();
        Model model = dataset.getNamedModel(uri);
        model.write(System.out);
        System.out.println("");
    }

    public static void main(String[] args){
        WriteATTL2TDB();
        connect();
        String uri1 = "http://xmlns.com/foaf/0.1/";
        String uri2 = "urn:x-hp-jena:eg/";
//        getNamedModel(uri1);
//        getNamedModel(uri2);
    }
}
