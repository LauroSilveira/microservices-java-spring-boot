package br.com.alurafood.mspayments.model;

import lombok.Getter;

@Getter
public enum Status {
  CREATED,
  CONFIRMED,
  ORDER_NOT_CONFIRMED,
  UPDATED,
  CANCELED;
}
