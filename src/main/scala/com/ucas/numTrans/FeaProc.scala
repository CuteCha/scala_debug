package com.ucas.numTrans

import com.ucas.numTrans.NumHash.numHash

object FeaProc {

  def getDense(v: String): String = {
    if (v.equals("-")) "0.0" else v
  }

  def genSinSlot(feature: Map[String, String], fea: List[SlotParse.SinSlot], size: Int): String = {
    fea.map(x => {
      if (x.cate) {
        feature.getOrElse(x.field, "-").split(",").slice(0, size)
          .map(t => s"${numHash(t)}:${x.slot}:1.0").mkString(";")
      } else {
        feature.getOrElse(x.field, "0.0").split(",").slice(0, size)
          .zipWithIndex.map(t => s"${t._2}:${x.slot}:${getDense(t._1)}").mkString(";")
      }
    }).mkString(";")
  }


  def genWeightSlot(feature: Map[String, String], fea: List[SlotParse.MulSlot], size: Int): String = {
    fea.map(x => {
      val k = feature.getOrElse(x.fields.head, "-").split(",").slice(0, size)
      val v = feature.getOrElse(x.fields.last, "0.0").split(",").slice(0, size)

      k.zip(v).map(t => s"${numHash(t._1)}:${x.slot}:${getDense(t._2)}").mkString(";")
    }).mkString(";")
  }


  def cross(x: List[String], y: List[String]): List[String] = {
    x.flatMap(r => y.map(s => s"${r}_${s}"))
  }


  def genCrossSlot(feature: Map[String, String], fea: List[SlotParse.MulSlot], size: Int): String = {
    fea.map(x => {
      x.fields.map(r => feature.getOrElse(r, "-").split(",").slice(0, size).toList)
        .reduce((u, v) => cross(u, v)).map(t => s"${numHash(t)}:${x.slot}:1.0").mkString(";")
    }).mkString(";")
  }


  def genNumFea(feature: Map[String, String],
                taskFea: List[SlotParse.SinSlot],
                singleFea: List[SlotParse.SinSlot],
                kvFea: List[SlotParse.SinSlot],
                crossFea: List[SlotParse.MulSlot],
                weightFea: List[SlotParse.MulSlot], size: Int): String = {

    val rid = feature.getOrElse("rid", "-")
    val label = feature.getOrElse("label", "0.0")

    val taskSlot = genSinSlot(feature, taskFea, size)
    val singleSlot = genSinSlot(feature, singleFea, size)
    val kvSlot = genSinSlot(feature, kvFea, size)
    val crossSlot = genCrossSlot(feature, crossFea, size)
    val weightSlot = genWeightSlot(feature, weightFea, size)

    s"$rid|$label|${List(taskSlot, singleSlot, kvSlot, crossSlot, weightSlot).filter(_.nonEmpty).mkString(";")}"
  }
}
