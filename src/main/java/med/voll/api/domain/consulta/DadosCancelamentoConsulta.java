package med.voll.api.domain.consulta;

import java.time.LocalDateTime;

public record DadosCancelamentoConsulta(Long id, Long idMedico, Long idPaciente, LocalDateTime data) {
    public DadosCancelamentoConsulta(Consulta consulta) {
        this(consulta.getId(), consulta.getMedico().getId(), consulta.getPaciente().getId(), consulta.getData());
    }
}
