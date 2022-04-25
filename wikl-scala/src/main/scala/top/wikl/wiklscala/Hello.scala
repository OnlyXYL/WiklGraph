package top.wikl.wiklscala

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @title: Hello
 * @description: TODO
 * @author XYL
 * @date 2020/4/21 22:08
 * @return
 * @since V1.0
 */
object Hello {

  /**
   * 主函数
   *
   * @param args
   * @return void
   * @author XYL
   * @date 2022/02/22 22:01
   * */
  def main(args: Array[String]): Unit = {
    //    println("Hello,scala")
    //    var app = (1,2,3)
    //    println(app)

    val conf = new SparkConf();
    conf.setAppName("Course01")
      .setMaster("local[2]")
    val sc = new SparkContext(conf)

    //并行化数据集
    val rdd1 = parallelizeRDD(sc)

    //    val data = Array(1,2,3,4,5);
    //    val rdd1 = sc.parallelize(data)

    rdd1.foreach(s => println(s))

    //关闭环境
    sc.stop();

  }

  /**
   * 并行化集合
   *
   * @param sc
   * @return org.apache.spark.rdd.RDD<java.lang.Object>
   * @author XYL
   * @date 2022/02/22 22:02
   * */
  def parallelizeRDD(sc: SparkContext): RDD[Int] = {

    val data = Array(1, 2, 3, 4, 5);
    sc.parallelize(data)
  }


}
