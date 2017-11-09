package com.djodar.katas.cats.validated

import cats.data.ValidatedNel
import cats.implicits._

sealed trait FormValidatorNel {

  type ValidationResult[A] = ValidatedNel[DomainValidation, A]

  private def validateName(name: String): ValidationResult[String] =
    if(name.matches("^[a-zA-Z0-9]+$")) name.validNel else NameContainsInvalidCharacters.invalidNel //lifting valid and invalid NEL


  def validate(name: String): ValidationResult[DomainData] = {
    // validatedNel accepts multiple validations and one list with all errors combined
    validateName(name).map(DomainData)
  }
}

object FormValidatorNel extends FormValidatorNel
