package com.djodar.katas

object PrimeFactors {

  def result(i: Int, list: List[Int] = List()): List[Int] = {
    for (n <- 2 to i if i % n == 0) {
      return result(i / n, list :+ n)
    }
    list
  }

}
