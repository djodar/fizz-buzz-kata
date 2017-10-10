package com.djodar.katas.algebra_api

import java.time.LocalDate

import scala.util.{Failure, Success, Try}

/**
  * Smart constructors
  */
sealed trait Account {
  def no: String

  def name: String

  def dateOfOpen: LocalDate

  def dateOfClose: Option[LocalDate]

  def balance: BigDecimal

  def copy(no: String = no, name: String = name, dateOfOpen: LocalDate = dateOfOpen, dateOfClose: Option[LocalDate] = dateOfClose, balance: BigDecimal = balance): Account

}

final case class CheckingAccount private(no: String, name: String, dateOfOpen: LocalDate, dateOfClose: Option[LocalDate], balance: BigDecimal) extends Account {
  override def copy(no: String, name: String, dateOfOpen: LocalDate, dateOfClose: Option[LocalDate], balance: BigDecimal): Account =
    CheckingAccount(no, name, dateOfOpen, dateOfClose, balance)
}

final case class SavingsAccount private(no: String, name: String, dateOfOpen: LocalDate, rateOfInterest: BigDecimal, dateOfClose: Option[LocalDate], balance: BigDecimal = 0) extends Account {
  override def copy(no: String, name: String, dateOfOpen: LocalDate, dateOfClose: Option[LocalDate], balance: BigDecimal): Account =
    SavingsAccount(no, name, dateOfOpen, 0, dateOfClose, balance)
}

object Account {
  def checkingAccount(no: String, name: String, dateOfOpen: LocalDate, dateOfClose: Option[LocalDate] = None, balance: BigDecimal = 0): Try[Account] = {
    closeDateCheck(dateOfOpen, dateOfClose).map { d =>
      CheckingAccount(no, name, d._1, d._2, balance)
    }
  }

  def savingsAccount(no: String, name: String, dateOfOpen: LocalDate, rateOfInterest: BigDecimal, dateOfClose: Option[LocalDate], balance: BigDecimal): Try[Account] = {
    closeDateCheck(dateOfOpen, dateOfClose).map { d =>
      if (rateOfInterest <= BigDecimal(0))
        throw new Exception(s"Interest rate $rateOfInterest must be > 0")
      else
        SavingsAccount(no, name, d._1, rateOfInterest, d._2, balance)
    }
  }

  private def closeDateCheck(openDate: LocalDate, closeDate: Option[LocalDate]): Try[(LocalDate, Option[LocalDate])] = {
    closeDate.map { cd =>
      if (cd.isBefore(openDate)) Failure(new Exception(s"Close date [$cd] cannot be earlier than open date [$openDate]"))
      else Success(openDate, Some(cd))
    }.getOrElse(Success(openDate, closeDate))
  }
}