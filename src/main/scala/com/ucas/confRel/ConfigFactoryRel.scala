package com.ucas.confRel

import com.google.common.hash.Hashing
import com.typesafe.config.{Config, ConfigFactory}

import scala.util.Try
import scala.collection.JavaConversions._
import java.io.File
import java.nio.charset.Charset


object ConfigFactoryRel {
  protected def loadConf(path: String): Config = {
    println("load config = " + path)
    val configFile = new File(path)
    if (configFile.exists() && configFile.isFile) {
      ConfigFactory.parseFile(configFile)
    } else {
      throw new IllegalArgumentException("The config file: " + path + " is not existed.")
    }
  }

  case class NumerousFea(s: Int, n: String, c: String)

  def test01(): Unit = {
    val confPath = "./data/configFactory.conf"
    val conf = loadConf(confPath)
    println(conf.getString("app.spark.name"))
    println(conf.getList("app.input.file.data.part"))
    println(conf.getList("app.input.file.data.part").toArray.toList)
    println(conf.getStringList("app.input.file.data.part").toArray.toList)

    val inputConf = conf.getConfig("app.input")
    println(inputConf.getInt("part"))
    println(inputConf.getBoolean("file.cmdArgs"))

    val slots = Try(conf.getStringList("app.model.slots").toList).getOrElse(Nil)
    println(slots)
    slots.map(x => {
      val t = x.split("\\|")
      NumerousFea(t(0).toInt, t(1), t(2))
    }).foreach(x => println(s"${x.c}/${x.s}/${x.n}"))

  }

  def test02(): Unit = {
    //val data = "scala"
    //println(hash(data, 97))
    //println(murHash(data, 97)) //1403984909
    //val str = "xxx123"
    //println(MurHash32.stringHash(str))

    val lst = List("python", "golang", "scala", "java", "c++", "c", "123")
    lst.foreach(x => println(s"${x}: ${MurHash32.stringHash(x)}"))
    println("-----------------------------")
    lst.foreach(x => println(s"${x}: ${MurHash32.numHash(x)}"))
  }

  def hash(s: String, seed: Int): String = {
    Hashing.murmur3_32(seed).hashString(s, Charset.forName("UTF-8")).asInt().abs.toString
  }

  def murHash(s: String, seed: Int): String = {
    MurHash32.stringHash(s, seed).abs.toString
  }


  def main(args: Array[String]): Unit = {
    test02()
  }

}
