package com.djodar.katas

/**
  * Created by daniel.jodar on 03/10/2017.
  */
object FizzBuzz extends App {

  def getResult(i: Int): String = (i % 3, i % 5) match {
    case (0, 0) => "fizzbuzz" // divisible by 3 and 5 is also by 15
    case (0, _) => "fizz"
    case (_, 0) => "buzz"
    case (_, _) => i.toString
  }
}
