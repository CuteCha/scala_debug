package com.ucas.varArgRel

object Debug01 {
  def main(args: Array[String]) {
    test02()
  }

  def test01(): Unit = {
    printAll("Runoob", "Scala", "Python")
    println("-" * 72)
    printAll(List("Runoob", "Scala", "Python"): _*) //_* 这个标注告诉编译器把 List 的每个元素当作参数
    println("-" * 72)
    printAll(Array("Runoob", "Scala", "Python"): _*)
    println("-" * 72)
    printAll(Seq("Runoob", "Scala", "Python"): _*)
  }

  def printAll(args: String*): Unit = {
    args.foreach(println)
  }

  def test02(): Unit = {
    seqArg("a", Array("b"))
    println("-" * 72)
    seqArg("a", Array("b1","b2"), Array("c"))
  }

  def seqArg(name: scala.Predef.String, args: scala.Seq[scala.Predef.String]): Unit = {
    println(name)
    args.foreach(println)
  }

  def seqArg(name: scala.Predef.String,
             args: scala.Seq[scala.Predef.String],
             subArgs: scala.Seq[scala.Predef.String]): Unit = {
    println(name)
    args.foreach(println)
    println("<>" * 36)
    subArgs.foreach(println)
  }
}
