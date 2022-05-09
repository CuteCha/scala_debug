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
    }).foreach(x => println(s"${x.c}/${x.s}/${x.n.split("&").mkString(",")}"))

  }

  def test02(): Unit = {
    //val data = "scala"
    //println(hash(data, 97))
    //println(murHash(data, 97)) //1403984909
    //val str = "xxx123"
    //println(MurHash32.stringHash(str))

    val lst = List("python", "golang", "scala", "java", "c++", "c", "123", "-", "", "123456789")
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

  def test03(): Unit = {
    var a = 60
    /* 60 = 0011 1100 */
    var b = 13
    /* 13 = 0000 1101 */
    var c = 0

    c = a & b /* 12 = 0000 1100 */
    println("a & b = " + c);

    c = a | b /* 61 = 0011 1101 */
    println("a | b = " + c)

    c = a ^ b /* 49 = 0011 0001 */
    println("a ^ b = " + c)

    c = ~a /* -61 = 1100 0011 */
    println("~a = " + c)

    c = a << 2 /* 240 = 1111 0000 */
    println("a << 2 = " + c);

    c = a >> 2 /* 15 = 1111 */
    println("a >> 2  = " + c);

    c = a >>> 2 /* 15 = 0000 1111 */
    println("a >>> 2 = " + c)
    println(s"[${(-6).toBinaryString}] -6 >>> 2 = [${((-6) >>> 2).toBinaryString}] ${(-6) >>> 2}")
    println(s"[${6.toBinaryString}] 6 >>> 2 = [${(6 >>> 2).toBinaryString}] ${6 >>> 2}")

    println(s"-60=${(-60).toBinaryString}")
    println(s"60=${60.toBinaryString}")

    println(s"${Integer.parseInt("11", 2)}=11")
    println(s"${Integer.parseInt("111100", 2)}=111100")

    bitCal(-6)
    bitCal(6)


  }

  def bitCal(i: Int): Unit = {
    val bitStr = i.toBinaryString
    val l = bitStr.length
    val r = i >> 2
    val rBitStr = r.toBinaryString
    val rL = rBitStr.length
    val ru = i >>> 2
    val ruBitStr = ru.toBinaryString
    val ruL = ruBitStr.length

    println(s"[$bitStr](len=$l) $i >>> 2 = [$ruBitStr](len=$ruL) $ru")
    println(s"[$bitStr](len=$l) $i >> 2 = [$rBitStr](len=$rL) $r")
  }

  def test04(): Unit = {
    import scala.io.Source
    import java.io.{FileWriter, BufferedWriter, File}

    val inFilePath = "./data/example.txt"
    val outFilePath = "./data/hash_str.txt"
    val writer = new BufferedWriter(new FileWriter(new File(outFilePath)))

    val strList = Source.fromFile(inFilePath).getLines()
      .flatMap(x => x.split("\t")).toSet.toList
    //.foreach(println)
    //.foreach(x => writer.write(s"${NumHash.numHash(x)}:$x"))

    for (x <- strList) {
      writer.write(s"${NumHash.numHash(x)};$x\n")
    }

    writer.close()
  }


  def main(args: Array[String]): Unit = {
    test04()
  }

}
