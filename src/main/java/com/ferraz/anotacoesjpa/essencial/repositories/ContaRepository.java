package com.ferraz.anotacoesjpa.essencial.repositories;

import com.ferraz.anotacoesjpa.essencial.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepository extends JpaRepository<Conta, Integer> {
}
