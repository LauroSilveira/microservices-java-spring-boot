package br.com.alurafood.mspayments.repository;

import br.com.alurafood.mspayments.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {


}
