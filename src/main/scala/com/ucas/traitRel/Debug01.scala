package com.ucas.traitRel


object Debug {
  def test01(): Unit = {
    val t = new Test53
    t.info
  }

  def test02(): Unit = {
    val b = new MyBall
    println(b.describe())
    b.info
    println("-" * 72)
    val b2 = new MyBall2
    println(b2.describe())
    b2.info
  }

  def test03(): Unit = {
    val c = new TestC54 with Test54
    println(c.a)
    println(c.info)
    println(c.say)

    println("-" * 72)
    val d = new TestC55
    println(d.a)
    println(d.info)
    println(d.say)

    println("-" * 72)
    val e = new TestC56
    println(e.a)
    println(e.info)
    println(e.info2)

    println("-" * 72)
    val f = new TestC57
    println(f.a)
    println(f.info)
    println(f.say)
  }

  def main(args: Array[String]): Unit = {
    test02()
  }

}

trait Test51 {
  def info: String = {
    "Test51"
  }
}

trait Test52 {
  def info: String = {
    "Test52"
  }
}

class Test53 extends Test51 with Test52 {
  override def info: String = {
    "Test53"
  }
}

trait Ball {
  def describe(): String = {
    "ball"
  }

  def info(): Unit = {
    println("Ball")
  }
}

trait Color extends Ball {
  override def describe(): String = {
    "blue-" + super.describe()
  }

  override def info: Unit = {
    println("Color")
  }
}

trait Category extends Ball {
  override def describe(): String = {
    "foot-" + super.describe()
  }

  override def info: Unit = {
    println("Category")
  }
}

class MyBall extends Category with Color {
  override def describe(): String = {
    "my ball is a " + super.describe()
  }

  //  override def info: Unit = {
  //    println("MyBall")
  //  }
}

class MyBall2 extends Color with Category {
  override def describe(): String = {
    "my ball2 is a " + super.describe()
  }

  //  override def info: Unit = {
  //    println("MyBall2")
  //  }
}

trait Test54 {
  val a = 5

  def info = {
    "Test54"
  }
}

class TestC54 {
  this: Test54 => //this:X => 要求C在实例化时或定义C的子类时，必须混入指定的X类型，这个X类型也可以指定为当前类型
  def say = {
    "TestC54; a=" + a
  }
}

class TestC55 extends TestC54 with Test54

trait Test55 {
  this: Test54 =>
  def info2 = {
    "Test55"
  }
}

class TestC56 extends Test55 with Test54

trait Test56 extends Test54 {
  def say = {
    "Test56"
  }
}

class TestC57 extends Test56