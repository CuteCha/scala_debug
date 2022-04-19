package com.ucas.common

import scala.collection.mutable

/**
 * Created by cxq on 2019/1/4.
 */
object ParamParse {
  def ParseParameters(params: Array[String]): mutable.HashMap[String, String] = {
    val inputParameter = new mutable.HashMap[String, String]

    params.par.foreach(each => {
      val part = each.split("=", 2)
      if (part.length == 2) {
        inputParameter(part(0)) = part(1)
      }
    })

    inputParameter

  }
}