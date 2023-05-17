package com.ucas.traitRel

object Debug04 extends Debug04App with Debug04Work

trait Debug04Work extends Debug04AppF {
  override def work(): Unit = {
    println("Debug04Work ... work")
  }
}

trait Debug04App {
  this: Debug04AppF =>

  def main(args: Array[String]): Unit = {
    println("Debug04App ... start")
    work()
    println("Debug04App ... end")
  }
}

trait Debug04AppF extends Debug04AppFF {
  def info(): Unit = {
    println("Debug04AppF")
  }
}

trait Debug04AppFF {
  def work(): Unit = {
    println("Debug04AppFF ... work")
  }
}