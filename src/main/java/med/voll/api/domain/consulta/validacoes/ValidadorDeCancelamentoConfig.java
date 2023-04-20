package med.voll.api.domain.consulta.validacoes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ValidadorDeCancelamentoConfig {

    @Bean
    public List<ValidadorCancelamentoDeConsulta> validadorCancelamentoDeConsultaList() {
        List<ValidadorCancelamentoDeConsulta> validadores = new ArrayList<>();
        validadores.add(new ValidadorHorarioCancelamentoAntecedencia());

        return validadores;
    }
}
