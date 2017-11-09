package com.djodar.katas.cats.validated

sealed trait DomainValidation {
  def errorMessage: String
}

case object NameContainsInvalidCharacters extends DomainValidation {
  override def errorMessage: String = "Invalid characters, remove them!"
}
