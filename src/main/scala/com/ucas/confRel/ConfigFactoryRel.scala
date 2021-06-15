package com.ucas.confRel

import com.typesafe.config.{Config, ConfigFactory}

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

  def test01(): Unit = {
    val confPath = "./data/configFactory.conf"
    val conf=loadConf(confPath)
    println(conf.getString("app.spark.name"))
    println(conf.getList("app.input.file.data.part"))
    println(conf.getList("app.input.file.data.part").toArray.toList)
    println(conf.getStringList("app.input.file.data.part").toArray.toList)

    val inputConf=conf.getConfig("app.input")
    println(inputConf.getInt("part"))
    println(inputConf.getBoolean("file.cmdArgs"))

  }

  def main(args: Array[String]): Unit = {
    test01()
  }

}
