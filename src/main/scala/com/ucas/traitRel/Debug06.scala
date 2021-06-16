package com.ucas.traitRel

trait A {
  def hello(): String = "A"
}

trait B extends A {
  override def hello(): String = "B-" + super.hello()
}

trait C extends A {
  override def hello(): String = "C-" + super.hello()
}

trait D1 extends B with C {

}

trait D2 extends C with B {

}

trait D3 extends B with C {
  override def hello(): String = super[B].hello() + "\n" + super[C].hello()
}

object Debug0601 extends App with D1 {
  println(hello()) //A-B-C
}

object Debug0602 extends App with D2 {
  println(hello()) //A-C-B
}

object Debug0603 extends App with D3 {
  println(hello())
  /*
  * A-B-
  * A-B-C-
  * */
}

class D4 extends B with C {
  override def hello(): String = "D4-" + super.hello()
}

class D5 extends C with B {
  override def hello(): String = "D5-" + super.hello()
}

object Debug0604 extends App {
  println(new D4().hello())
  println(new D5().hello())
}

class D6 extends B with C {
  override def hello(): String = "D6-" + super.hello()

  def main(args: Array[String]): Unit = {
    println(hello())
  }
}

object Debug0605 extends D6