package com.djodar.katas.algebra_api

import java.time.LocalDate

import org.scalacheck.{Gen, Properties}
import org.scalacheck.Prop.forAll

import scala.util.Success

object AccountServiceTest extends Properties("AccountService") {

  val amounts: Gen[BigDecimal] = Gen.choose(-1000, 1000).map(BigDecimal(_))

  property("Equal credit & debit in sequence retain the same balance") = forAll(amounts) { amount: BigDecimal =>
    val Success((before, after)) = for {
      a <- AccountService.open("anyNo", "anyName", Some(LocalDate.now().plusDays(1)))
      b <- AccountService.balance(a)
      c <- AccountService.credit(a, amount)
      d <- AccountService.debit(c, amount)
    } yield (b, d.balance)

    before == after
  }
}
