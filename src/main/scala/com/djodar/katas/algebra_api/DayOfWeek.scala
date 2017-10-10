package com.djodar.katas.algebra_api

/**
  * smart constructor idiom. Most of the domain logic for creation and validation is moved away from the core abstraction, which is the trait, to the companion object, which is the module.
  */
sealed trait DayOfWeek {
  val value: Int

  override def toString: String = value match {
    case 1 => "Monday"
    case 2 => "Tuesday"
    case 3 => "Wednesday"
    case 4 => "Thursday"
    case 5 => "Friday"
    case 6 => "Saturday"
    case 7 => "Sunday"
  }
}

object DayOfWeek {
  private def unsafeDayOfWeek(d: Int) = new DayOfWeek {
    val value = d
  }

  private val isValid: Int => Boolean = { i => i >= 1 && i <= 7 }

  def dayOfWeek(d: Int): Option[DayOfWeek] = if (isValid(d))
    Some(unsafeDayOfWeek(d)) else None
}
