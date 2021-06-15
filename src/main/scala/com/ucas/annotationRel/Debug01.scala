package com.ucas.annotationRel

import java.io.{File, FileInputStream, FileOutputStream, ObjectInputStream, ObjectOutputStream}

/**
 * 注解
 * Annotation是一种对程序代码进行描述的结构化信息。
 * Annotation可以分布在程序的任何地方，能够注解变量、类、方法、参数等多种元素，
 * 它的主要功能有以下几种：
 * 1、自动生成scala文档
 * 2、检查程序中可能出现的语法问题
 * 3、规定程序行为，例如以下代码：
 * //@BeanProperty，要求程序生成相应getter,setter方法，与java命名规范一致
 * class Student[T,S](name:T,var age:S) extends Person(name){
 * //@BeanProperty var studentNo:String=null
 * }
 * annotation还有其它功能，上面三种只是平时在编写程序时最为常用的功能。
 *
 *
 * 注解常用场景
 * 注解的常用场景包括volatile，transient，native,SerialVersionUID,serializable 5个，
 * 用于对变量或方法进行注解，其中volatile用于标识变量可能会被多个线程同时修改，它不是线程安全的；
 * transient用于标识变量是瞬时的，它不会被持久化；
 * native用于标识算法来自C或C++代码实现
 */


//Person类可序列化，直接继承Serializable
class Person extends Serializable {
  //  var name:String="zzh"
  //@transient注解声明后，成员变量不会被序列化
  //如果给成员变量加@transient注解，则相应的成员变量不会被序列化，
  //此时如果进行反序列化的话，对应成员变量为null
  @transient var name: String = "zzh"
  var age: Int = 0

  override def toString() = "name=" + name + " age=" + age
}

object Debug01 {
  def main(args: Array[String]): Unit = {
    val file = new File("./data/person.out")

    val out = new ObjectOutputStream(new FileOutputStream(file))
    val person = new Person
    out.writeObject(person)
    out.close()
    println(person.toString())

    val in = new ObjectInputStream(new FileInputStream(file))
    val objPerson = in.readObject()
    in.close()
    println(objPerson)

    val newPerson=objPerson.asInstanceOf[Person]
    println(newPerson.toString())
  }
}
