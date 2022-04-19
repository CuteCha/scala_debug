package com.ucas.sparkRel

import org.apache.spark.sql.SparkSession


object TestApp {
  def test01(spark: SparkSession): Unit = {
    val data = Seq(("James","Sales","NY",90000,34,10000),("Michael","Sales","NV",86000,56,20000),("Robert","Sales","CA",81000,30,23000),("Maria","Finance","CA",90000,24,23000),("Raman","Finance","DE",99000,40,24000),("Scott","Finance","NY",83000,36,19000),("Jen","Finance","NY",79000,53,15000),("Jeff","Marketing","NV",80000,25,18000),("Kumar","Marketing","NJ",91000,50,21000))
    val schema = List("employee_name", "department", "state", "salary", "age", "bonus")

    val rdd = spark.sparkContext.parallelize(data)
    //val df = rdd.toDF("employee_name","department","state","salary","age","bonus")


  }

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local[*]").appName("TestApp").getOrCreate()
    test01(spark)
    spark.close()


  }
}
