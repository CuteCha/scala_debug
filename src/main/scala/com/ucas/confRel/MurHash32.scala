package com.ucas.confRel


object MurHash32 {
  def rotl(i: Int, d: Int): Int = {
    (i << d) | (i >>> -d)
  }

  def mixLast(hash: Int, data: Int): Int = {
    var k = data

    k *= 0xcc9e2d51
    k = rotl(k, 15)
    k *= 0x1b873593

    hash ^ k
  }

  def mix(hash: Int, data: Int): Int = {
    var h = mixLast(hash, data)
    h = rotl(h, 13)
    h * 5 + 0xe6546b64
  }

  def avalanche(hash: Int): Int = {
    var h = hash

    h ^= h >>> 16
    h *= 0x85ebca6b
    h ^= h >>> 13
    h *= 0xc2b2ae35
    h ^= h >>> 16

    h
  }

  def finalizeHash(hash: Int, length: Int): Int = avalanche(hash ^ length)

  def stringHash(str: String, seed: Int): Int = {
    var h = seed
    var i = 0
    while (i + 1 < str.length) {
      val data = (str.charAt(i) << 16) + str.charAt(i + 1)
      h = mix(h, data)
      i += 2
    }
    if (i < str.length) h = mixLast(h, str.charAt(i).toInt)
    finalizeHash(h, str.length)
  }

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
      var k = 0.to(8).map(j => (data(i8 + j) & 0xff).toLong << (j * 8)).sum
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
