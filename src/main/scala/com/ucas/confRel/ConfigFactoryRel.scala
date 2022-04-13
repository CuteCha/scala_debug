package com.ucas.confRel

import com.typesafe.config.{Config, ConfigFactory}
import scala.util.Try
import scala.collection.JavaConversions._

import java.io.File


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

  def main(args: Array[String]): Unit = {
    test01()
  }

}
