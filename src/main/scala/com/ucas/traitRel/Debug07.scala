package com.ucas.traitRel


trait Operator {

  def insert(value: Int): Boolean
}

trait DBOperator extends Operator {

  println("build a DBOperator.")

  override def insert(value: Int): Boolean = {
    println("insert value into database.")
    true
  }

}

trait MysqlOperator extends DBOperator {

  println("build a MysqlOperator.")

  override def insert(value: Int): Boolean = {
    println("insert value into Mysql Database.")
    true
  }
}

trait OracleOperator extends DBOperator {

  println("build a OracleOperator.")

  override def insert(value: Int): Boolean = {

    println("insert value into Oracle Database.")
    //留意一下这个super关键字！等会提及它。
    super.insert(value)
  }
}


class Connection(thisURL: String) {

  val URL: String = thisURL

  println("build a Connection.")

}

trait OtherTrait {

}


object Debug07 {

  def test01(): Unit = {
    val connection = new Connection("127.0.0.1") with MysqlOperator
    connection.insert(100)
    //特质的构造顺序和类的构造顺序是一样的，会从父类开始逐步向下进行构建
    /*
    build a Connection.
    build a DBOperator.
    build a MysqlOperator.
    insert value into Mysql Database.*/

    //如果类在声明时就实现了特质，则先构造特质，再构造类实例。
    //如果类在实例化时动态混入了特质，则先构造实例，再构造特质。
    //无论哪种情况，都不会重复地构造任何一个已经被构造过的特质。
  }

  def test02(): Unit = {
    val connection = new Connection("127.0.0.1") with MysqlOperator with OracleOperator
    connection.insert(100)
    //特质的构造顺序和类的构造顺序是一样的，会从父类开始逐步向下进行构建
    //特质中的 super 关键字，未必会先指向它的父特质。
    //在 Scala 的动态混入特质过程中，特质的方法调用顺序是从右到左。
    //如果该特质左边的特质有存在的同名方法，则这个super指代的是动态混入时，位于该特质左边的特质。
    //如果沿着左边一直都无法寻找到可重用的方法，这时 super 关键字才会指代其父特质的被重写方法。
    /*
    build a Connection.
    build a DBOperator.
    build a MysqlOperator.
    build a OracleOperator.
    insert value into Oracle Database.
    insert value into Mysql Database.*/
  }

  def test03(): Unit = {
    val connection = new Connection("127.0.0.1") with OtherTrait with OracleOperator
    connection.insert(100)
    //特质的构造顺序和类的构造顺序是一样的，会从父类开始逐步向下进行构建
    //特质中的 super 关键字，未必会先指向它的父特质。
    //在 Scala 的动态混入特质过程中，特质的方法调用顺序是从右到左。
    //如果该特质左边的特质有存在的同名方法，则这个super指代的是动态混入时，位于该特质左边的特质。
    //如果沿着左边一直都无法寻找到可重用的方法，这时 super 关键字才会指代其父特质的被重写方法。
    /*
    build a Connection.
    build a DBOperator.
    build a OracleOperator.
    insert value into Oracle Database.
    insert value into database.*/
  }

  //trait B
  //trait A { this: B => }
  //A不能混合到一个不扩展B


  def main(args: Array[String]): Unit = {
    test03()
  }

}
