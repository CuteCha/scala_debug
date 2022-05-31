package com.ucas.numTrans

object SlotParse {

  case class SinSlot(slot: Int, field: String, cate: Boolean)

  case class MulSlot(slot: Int, fields: List[String], cate: Boolean)

  def sinSlotParse(lst: List[String]): List[SinSlot] = {
    lst.map(x => {
      val v = x.split("\\|").map(_.trim)
      SinSlot(v(0).toInt, v(1), v(2).equals("s"))
    })
  }

  def mulSlotParse(lst: List[String]): List[MulSlot] = {
    lst.map(x => {
      val v = x.split("\\|").map(_.trim)
      MulSlot(v(0).toInt, v(1).trim.split("&").toList, v(2).equals("s"))
    })
  }

}
