package com.djodar.katas.algebra_api

import java.time.LocalDate

import org.scalacheck.{Gen, Properties}
import org.scalacheck.Prop.forAll

import scala.util.Success

object AccountServiceTest extends Properties("AccountService") {

  val amounts: Gen[BigDecimal] = Gen.choose(-1000, 1000).map(BigDecimal(_))
  val accounts: Gen[Account] = for (n <- amounts) yield Account("anyNo", "anyName", LocalDate.now().plusDays(1), balance = n)

  property("Equal credit & debit in sequence retain the same balance") = forAll(accounts, amounts) {
    (account: Account, amount: BigDecimal) =>
      val Success((before, after)) = for {
        b <- AccountService.balance(account)
        c <- AccountService.credit(account, amount)
        d <- AccountService.debit(c, amount)
      } yield (b, d.balance)

      before == after
  }
}
