package com.ferraz.anotacoesjpa.essencial.repositories;

import com.ferraz.anotacoesjpa.essencial.model.DadosAcesso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DadosAcessoRepository extends JpaRepository<DadosAcesso, Integer> {
}
