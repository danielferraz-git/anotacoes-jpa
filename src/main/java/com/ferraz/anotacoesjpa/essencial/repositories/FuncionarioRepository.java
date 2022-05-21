package com.ferraz.anotacoesjpa.essencial.repositories;

import com.ferraz.anotacoesjpa.essencial.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {
}
