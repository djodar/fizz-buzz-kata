package com.djodar.katas

object BerlinClock {

  val lampsTopRow: (Int) => Int = (i: Int) => (i - (i % 5)) / 5

  def seconds(seconds: Int): String = seconds % 2 match {
    case 0 if seconds < 60 => "Y"
    case _ => "O"
  }

  private def switch(numberOfLamps: Int, onLamps: Int, sign: String) = {
    sign * onLamps + "O" * (numberOfLamps - onLamps)
  }

  def topHours(hours: Int): String = switch(4, lampsTopRow(hours), "R")

  def bottomHours(hours: Int): String = {
    val topRow = hours - lampsTopRow(hours) * 5
    switch(4, topRow, "R")
  }

  def topMinutes(minutes: Int): String = switch(11, lampsTopRow(minutes), "Y").replaceAll("YYY", "YYR")

  def bottomMinutes(minutes: Int): String = switch(4, minutes % 5, "Y")

  def convertToBerlinTime(str: String): Array[String] = {
    val hourMinSec = str.split(":")
    val hours = hourMinSec(0).toInt
    val minutes = hourMinSec(1).toInt
    val secs = hourMinSec(2).toInt
    Array(seconds(secs), topHours(hours), bottomHours(hours), topMinutes(minutes), bottomMinutes(minutes))
  }
}
