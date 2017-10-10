package com.djodar.katas.algebra_api

import java.time.LocalDate

import scala.util.{Failure, Success, Try}

/**
  * Algebra-based API design. Module contains API definition implemented with this trait.
  *
  * Algebra laws are modeled as properties */
sealed trait AccountService[Account, Amount, Balance] {
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

// interpreter of the algebra
object AccountService extends AccountService[Account, BigDecimal, BigDecimal] {

  def today: LocalDate = LocalDate.now()

  override def open(no: String, name: String, openDate: Option[LocalDate]): Try[Account] = {
    if (no.isEmpty || name.isEmpty)
      Failure(new Exception("Account number or name cannot be blank"))
    else if (openDate.getOrElse(today) isBefore today) {
      Failure(new Exception("Provided opening date is in the past."))
    } else {
      Account.checkingAccount(no, name, openDate.getOrElse(today))
    }
  }

  override def close(account: Account, closeDate: Option[LocalDate]): Try[Account] = {
    val cd = closeDate.getOrElse(today)
    if (cd.isBefore(account.dateOfOpen))
      Failure(new Exception(s"Close date $cd cannot be before opening date ${account.dateOfOpen}"))
    else Success(account.copy(dateOfClose = Some(cd)))
  }

  override def debit(account: Account, amount: BigDecimal): Try[Account] = {
    require(amount > 0, "Amount to debit must be > 0")
    if (amount > account.balance)
      Failure(new Exception("Insufficient funds"))
    else Success(account.copy(balance = account.balance - amount))
  }

  override def credit(account: Account, amount: BigDecimal): Try[Account] =
    Success(account.copy(balance = account.balance + amount))

  override def balance(account: Account): Try[BigDecimal] = Try(account.balance)
}
