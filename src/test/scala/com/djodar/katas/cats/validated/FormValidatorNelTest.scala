package com.djodar.katas.cats.validated

import cats.data.Validated.{Invalid, Valid}
import org.scalatest.{FeatureSpec, GivenWhenThen, Matchers}

class FormValidatorNelTest extends FeatureSpec with GivenWhenThen with Matchers {

  scenario("Validate valid name") {

    When("Domain data contains valid name")
    val result: FormValidatorNel.ValidationResult[DomainData] = FormValidatorNel.validate(name = "Daniel")
    Then("ok validation")
    result should be (Valid(DomainData("Daniel")))
  }

  scenario("Invalid valid name") {

    When("Domain data contains invalid name")
    val result: FormValidatorNel.ValidationResult[DomainData] = FormValidatorNel.validate(name = "Dani-el")
    Then("KO validation")
    result match {
      case Valid(_) => fail()
      case Invalid(nel) => nel.head should be (NameContainsInvalidCharacters)
    }
  }

}
