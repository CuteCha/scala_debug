package com.ucas.numTrans

object NumHash {
  val seed = 0xe17a1465
  val m = 0xc6a4a7935bd1e995L
  val r = 47
  val n = 32

  def stringHash(str: String): Long = {
    val data = str.getBytes
    val length = data.length
    var h = (seed & 0xffffffffL) ^ (length * m)
    val length8 = length / 8
    for (i <- 0 until length8) {
      val i8 = i * 8
      var k = 0.to(7).map(j => (data(i8 + j) & 0xff).toLong << (j * 8)).sum
      k *= m
      k ^= k >>> r
      k *= m
      h ^= k
      h *= m
    }

    if (length % 8 >= 7)
      h ^= (data((length & ~7) + 6) & 0xff).toLong << 48
    if (length % 8 >= 6)
      h ^= (data((length & ~7) + 5) & 0xff).toLong << 40
    if (length % 8 >= 5)
      h ^= (data((length & ~7) + 4) & 0xff).toLong << 32
    if (length % 8 >= 4)
      h ^= (data((length & ~7) + 3) & 0xff).toLong << 24
    if (length % 8 >= 3)
      h ^= (data((length & ~7) + 2) & 0xff).toLong << 16
    if (length % 8 >= 2)
      h ^= (data((length & ~7) + 1) & 0xff).toLong << 8
    if (length % 8 >= 1) {
      h ^= (data(length & ~7) & 0xff).toLong
      h *= m
    }

    h ^= h >>> r
    h *= m
    h ^= h >>> r

    h
  }

  def numHash(str: String): String = {
    var v = stringHash(str)
    if (v < 0) {
      v = -v
    }

    (v >> n).toString
  }

}
