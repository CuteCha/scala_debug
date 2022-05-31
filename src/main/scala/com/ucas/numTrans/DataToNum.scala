package com.ucas.numTrans

import com.typesafe.config.{Config, ConfigFactory}
import com.ucas.numTrans.FeaProc.genNumFea

import java.io.{BufferedWriter, File, FileWriter}
import scala.util.Try
import scala.collection.JavaConversions._
import scala.io.Source

object DataToNum {
  protected def loadConf(path: String): Config = {
    println("load config = " + path)
    val configFile = new File(path)
    if (configFile.exists() && configFile.isFile) {
      ConfigFactory.parseFile(configFile)
    } else {
      throw new IllegalArgumentException("The config file: " + path + " is not existed.")
    }
  }

  protected def getStringList(c: Config, path: String, default: List[String]): List[String] = Try(c.getStringList(path).toList).getOrElse(default)

  protected def getInt(c: Config, path: String, default: Int): Int = Try(c.getInt(path)).getOrElse(default)

  def main(args: Array[String]): Unit = {
    val confPath = "./data/data_v8_to_numerous.conf"
    val conf = loadConf(confPath)
    val modelConf = conf.getConfig("app.model")
    val infos = getStringList(modelConf, "infos", Nil)
    val users = getStringList(modelConf, "users", Nil)
    val items = getStringList(modelConf, "items", Nil)
    val taskSlot = getStringList(modelConf, "task_slot", Nil)
    val singleSlot = getStringList(modelConf, "single_slot", Nil)
    val crossSlot = getStringList(modelConf, "cross_slot", Nil)
    val weightSlot = getStringList(modelConf, "weight_slot", Nil)
    val kvSlot = getStringList(modelConf, "kv_slot", Nil)
    val size = getInt(modelConf, "size", 25)

    println(size)
    infos.foreach(println)

    val taskFea = SlotParse.sinSlotParse(taskSlot)
    val singleFea = SlotParse.sinSlotParse(singleSlot)
    val kvFea = SlotParse.sinSlotParse(kvSlot)
    val crossFea = SlotParse.mulSlotParse(crossSlot)
    val weightFea = SlotParse.mulSlotParse(weightSlot)

    val fields = infos ::: users ::: items

    val inFilePath = "./data/v8.txt"
    val outFilePath = "./data/num.txt"
    val writer = new BufferedWriter(new FileWriter(new File(outFilePath)))

    Source.fromFile(inFilePath).getLines()
      .map(x => {
        val feature = fields.zip(x.split("\u0001").toList).toMap
        genNumFea(feature, taskFea, singleFea, kvFea, crossFea, weightFea, size)
      })
      .foreach(x => writer.write(s"$x\n"))

    writer.close()



    //println(conf.getString("app.spark.name"))
    //println(conf.getList("app.input.file.data.part"))
    //println(conf.getList("app.input.file.data.part").toArray.toList)
    //println(conf.getStringList("app.input.file.data.part").toArray.toList)
    //
    //val inputConf = conf.getConfig("app.input")
    //println(inputConf.getInt("part"))
    //println(inputConf.getBoolean("file.cmdArgs"))

  }

}
