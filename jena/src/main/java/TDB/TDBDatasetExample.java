package TDB;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.sparql.core.DatasetGraph;
import org.apache.jena.tdb.TDB;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.tdb.TDBLoader;
import org.apache.jena.tdb.store.DatasetGraphTDB;
import org.apache.jena.vocabulary.RDFS;

/**
 *创建、加载TDB数据库、TDB的
 *
 * （TDB是内存数据库，实际上就是把数据集加载到内存，然后进行操作）
 *
 * 构建TDB数据集，主要是通过类TDBFactory的createDataset方法，
 * 这个方法可以有两个参数：
 *
 * 1. 目录名称，存储数据集和一些辅助信息文件
 * 2. 给一个assembler文件，类似于配置文件，文件中存储数据集的相关信息
 *
 *
 *
 */

public class TDBDatasetExample {
    // 需要在根目录下，创建一个目录dataset，作为TDB数据存储的目录
    public static final String DIR = "dataset";

    /**
     * 往TDB数据库中写入数据
     * @param model 需要写入的模型
     */
    public static void write(Model model) {
        String NS = "urn:x-hp-jena:eg/";
        //第一个参数是nameSpace，第二个参数是localName
        Property p = model.createProperty(NS, "p");
        Property q = model.createProperty(NS, "q");
        model.add(p, RDFS.subPropertyOf, q);
        /**
         * Resource的父接口是FrontsNode和RDFNode
         * 除了从ResourceFactory中生产的Resource外，Resourc实例一旦创建就会和一个固定的model捆绑在一起
         * Resource支持一些方法来获取或者修改与它有关的model 比如addProperty（）getProperty()
         * addProperty(Property p,String o)p是需要添加到该resource的property，o是这个property的值
         */
        model.createResource(NS+"a").addProperty(p, "foo");
    }

    /**
     * 写事务操作，在begin() - commit()之间，保证操作的原子性
     *
     * @param dataset
     */

    public static void writeTransaction(Dataset dataset) {
        //进行数据集写操作，写入一些数据
        /**
         * begin（）可以接受两个参数READ或者WRITE,表示开始两种不同的业务操作
         */
        dataset.begin(ReadWrite.WRITE);
        try {
            // 获取数据集，此时数据集应该为空
            Model model = dataset.getDefaultModel();//获取数据集中的model
            // 为数据集添加数据
            write(model);
            // 提交数据，否则数据集的操作不会被提交
            dataset.commit();
        } finally {
            dataset.end();//完成事物操作，如果写操作和commit（）操作没有调用，会直接终止业务操作并且不会做任何改变
        }
    }

    /**
     *
     * 读事务操作
     *
     * @param dataset
     */

    public static void readTransaction(Dataset dataset) {
        // 读取数据
        dataset.begin(ReadWrite.READ);
        Model model = dataset.getDefaultModel();
        model.write(System.out);
        dataset.end();
    }

    /**
     * 通过目录名称构建TDB数据集
     *
     */
    public static void constructingTDBDatasetByDir() {
        System.out.println("给定目录构建数据集...");
        // 根据目录名称，创建数据集
        /**
         * Dataset的父类是Transactional，要进行查询必须要通过Dataset，它是named graphs的集合和一个 background graph组成
         * 以DIR-"dataset"名称创建数据集
         */
        Dataset dataset = TDBFactory.createDataset(DIR);

        // 进行写事务操作
        writeTransaction(dataset);

        // 进行读操作
        readTransaction(dataset);

        // 保存到磁盘，避免缓存数据丢失
        TDB.sync(dataset);

        // 释放所有TDB占有的系统资源
        TDB.closedown();
    }

    /**
     * 连接已有的TDB数据集，还是通过TDBFactory.createDataset()
     *
     */
    public static void connect() {
        System.out.println("连接数据库...");
        Dataset dataset = TDBFactory.createDataset(DIR);
        // 打印出数据集
        readTransaction(dataset);

        // 释放所有TDB占有的系统资源
        TDB.closedown();
    }


    public static void main(String []args) {
        constructingTDBDatasetByDir();
        //这里是观察是否在根目录下保存了数据集
        connect();
    }

}
