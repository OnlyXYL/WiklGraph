package top.wikl.wiklscala

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * spark学习第一天
 *
 * @author XYL
 * @date 2023/07/05 14:25
 * @since 305.2.2.0
 */
object WordCount {

  def main(args: Array[String]): Unit = {

    //TODO 和spark建立联系
    val sparkConf: SparkConf = new SparkConf().setMaster("local").setAppName("app")
    val sc = new SparkContext(config = sparkConf)

    //TODO 业务逻辑
    val lines: RDD[String] = sc.textFile("data")

    val words: RDD[String] = lines.flatMap(line => line.split(" "))

    val counts =  words.map(word => (word,1)).reduceByKey{
      case (x,y) => x + y
    }

    counts.saveAsTextFile("data/out.txt")

//    val tuples: Array[(String, Int)] = counts.collect()
//
//    tuples.foreach(println)

    //TODO 关闭连接
    sc.stop()

  }
}
