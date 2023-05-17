package com.ucas.traitRel

object Debug3 {
  def main(args: Array[String]): Unit = {
    val p = DebugCls01("polo", 37)
    p.showInfo()

    val q=DebugCls02.getInstance()
    q.showInfo()

  }
}

class DebugCls01 private(name: String, age: Int) {
  def showInfo(): Unit = {
    println(s"name=$name; age=$age; company=${DebugCls01.company}")
  }
}

object DebugCls01 {
  val company = "tencent"

  def apply(name: String, age: Int): DebugCls01 = new DebugCls01(name, age)
}


class DebugCls02 private(name: String, age: Int) {
  def showInfo(): Unit = {
    println(s"name=$name; age=$age")
  }
}

object DebugCls02 {
  private val employee=new DebugCls02("polo", 37)
  def getInstance(): DebugCls02 = employee
}