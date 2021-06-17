package com.ucas.traitRel

import com.typesafe.config.Config

import scala.collection.mutable

trait BatchFlow {
  @transient protected var _conf: Config = _

  protected val rddContext: scala.collection.mutable.Map[String, Any] = scala.collection.mutable.Map[String, Any]()
  protected val batchContext: scala.collection.mutable.Map[String, Any] = scala.collection.mutable.Map[String, Any]()
  protected val cacheRDDs: scala.collection.mutable.Queue[(String, Any)] = mutable.Queue()

  def preStart(): Unit = {
    println("preStart function is finish in BatchFlow")
  }

  def workflow(): Unit = {
    println("workflow function is finish in BatchFlow")
  }

  def postStart(): Unit = {
    println("postStart function is finish in BatchFlow")
  }
}

trait BatchComponent extends BatchFlow {
  override def preStart(): Unit = {
    super.preStart()
    println("preStart function is finish in BatchComponent")
  }
}

trait TrovoRawComponent extends BatchComponent {
  override def preStart(): Unit = {
    super.preStart()
    println("preStart function is finish in TrovoRawComponent")
  }
}

trait TrovoBaseComponent extends BatchComponent {
  override def preStart(): Unit = {
    super.preStart()
    println("preStart function is finish in TrovoBaseComponent")
  }
}

trait TrovoDataComponent extends TrovoRawComponent with TrovoBaseComponent {
}

trait TrovoAnalysisComponent extends TrovoDataComponent {
  override def preStart(): Unit = {
    //super，不是表示其父特质对象，而是表示上述叠加顺序中的下一个特质
    //叠加顺序: TrovoAnalysisComponent->TrovoDataComponent->TrovoBaseComponent->TrovoRawComponent->BatchComponent
    super.preStart()
    println("preStart function is finish in TrovoAnalysisComponent")
  }
}

trait TrovoExpAnalysis extends TrovoAnalysisComponent {
  override def preStart(): Unit = {
    super.preStart()
    println("preStart function is finish in TrovoExpAnalysis")
  }
}

trait TrovoExpAnalysisSmart extends TrovoExpAnalysis {
  override def preStart(): Unit = {
    super.preStart()
    println("preStart function is finish in TrovoExpAnalysisSmart")
  }
}

trait TrovoExpReportSmartFlow extends TrovoExpAnalysisSmart {
  override def workflow(): Unit = {
    println("workflow function is finish in TrovoExpReportSmartFlow")
  }
}

trait BatchApp {
  this: BatchComponent =>

  def start(): Unit = {
    preStart()
    workflow()
    postStart()
  }

  def stop(): Unit = {
    println("All process is finish !")
  }


  def main(args: Array[String]) {
    start()
    stop()
  }

}

object Debug05 extends BatchApp with TrovoExpReportSmartFlow

trait Debug05A {
  def preStart(): Unit = {
    println("Debug05A ... preStart")
  }
}

trait Debug05App {
  this: Debug05A =>
  def start(): Unit = {
    preStart()
  }

  def main(args: Array[String]): Unit = {
    start()
  }
}

trait Debug05B extends Debug05A {
  override def preStart(): Unit = {
    super.preStart()
    println("Debug05B ... preStart")
  }
}

object Debug0501 extends Debug05App with Debug05B

object Debug0502 extends Debug05B with Debug05App