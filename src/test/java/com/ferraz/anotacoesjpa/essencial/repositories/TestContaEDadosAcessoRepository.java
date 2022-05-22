package com.ferraz.anotacoesjpa.essencial.repositories;

import com.ferraz.anotacoesjpa.essencial.model.Conta;
import com.ferraz.anotacoesjpa.essencial.model.DadosAcesso;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class) //Para executar os métodos na ordem escolhida.
public class TestContaEDadosAcessoRepository {

    @Autowired
    private ContaRepository contaRepo;

    @Autowired
    private DadosAcessoRepository dadosAcessoRepo;

    @Test
    @Order(1)
    void deveSalvarUmaContaComContatoDeAcessoNoBanco(){
        DadosAcesso acesso = new DadosAcesso();
        acesso.setEmail("emaildeacesso@gmail.com");
        acesso.setCpf("123.456.789.11");
        acesso.setTelefone("33988556622");

        Conta conta = new Conta();
        conta.setNomeProprietario("João Silva");
        conta.setDadosAcesso(acesso);
        acesso.setConta(conta);

        contaRepo.save(conta);
    }

    @Test
    @Order(2)
    void deveTrazerAContaComBaseNoRelacionamento(){
        assertDoesNotThrow(()->{
            DadosAcesso acesso = dadosAcessoRepo.findById(1).orElseThrow(NullPointerException::new);
            assertNotNull(acesso.getConta());
            System.out.println("Proprietário: " + acesso.getConta().getNomeProprietario());
        });
    }

    @Test
    @Order(3)
    void deveRemoverDadosAcessoDeUmaContaRemovida(){
        assertDoesNotThrow(()->{
            contaRepo.deleteById(1);
            assertTrue(dadosAcessoRepo.findAll().isEmpty());
        });
    }

}
