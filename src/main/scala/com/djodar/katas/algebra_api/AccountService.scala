package com.djodar.katas.algebra_api

import java.time.LocalDate

import scala.util.{Failure, Success, Try}

trait AccountService[Account, Amount, Balance] {
  def open(no: String, name: String, openDate: Option[LocalDate]): Try[Account]

  def close(account: Account, closeDate: Option[LocalDate]): Try[Account]

  def debit(account: Account, amount: Amount): Try[Account]

  def credit(account: Account, amount: Amount): Try[Account]

  def balance(account: Account): Try[Balance]

  def transfer(from: Account, to: Account, amount: Amount): Try[(Account, Account, Amount)] = for {
    a <- debit(from, amount)
    b <- credit(to, amount)
  } yield (a, b, amount)
}

case class Account(number: String, name: String, dateOfOpen: LocalDate, dateOfClose: Option[LocalDate] = None, balance: BigDecimal = 0)

// interpreter
object AccountService extends AccountService[Account, BigDecimal, BigDecimal] {

  def today: LocalDate = LocalDate.now()

  override def open(no: String, name: String, openDate: Option[LocalDate]): Try[Account] = {
    if (no.isEmpty || name.isEmpty)
      Failure(new Exception("Account number or name cannot be blank"))
    else if (openDate.getOrElse(today) isBefore today) {
      Failure(new Exception("Provided opening date is in the past."))
    } else {
      Success(Account(no, name, openDate.getOrElse(today)))
    }
  }

  override def close(account: Account, closeDate: Option[LocalDate]): Try[Account] = ???

  override def debit(account: Account, amount: BigDecimal): Try[Account] =
    Try(Account(account.number, account.number, account.dateOfOpen, balance = account.balance - amount))

  override def credit(account: Account, amount: BigDecimal): Try[Account] =
    Try(Account(account.number, account.number, account.dateOfOpen, balance = account.balance + amount))

  override def balance(account: Account): Try[BigDecimal] = Try(account.balance)
}
