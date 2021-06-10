package com.ucas.traitRel


object Debug02 extends DebugApp
  with Test1
  with Test2

trait DebugApp {
  this: Test1 =>
  def main(args: Array[String]): Unit = {
    println("ok")
    println(this.a)
    println(a)
    say()
    run()
  }
}

trait TestRoot{
  def run(): Unit={}
}

trait Test1 extends TestRoot {
  val a = 1

  def say(): Unit = {
    println("Test1")
  }
}

trait Test2 extends Test1 {
  val b = 2

  override def run(): Unit = {
    println("go go")
  }
  def call(): Unit={
    say()
  }
}