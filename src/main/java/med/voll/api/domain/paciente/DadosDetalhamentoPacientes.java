package med.voll.api.domain.paciente;

import med.voll.api.domain.endereco.Endereco;
import med.voll.api.domain.medico.Especialidade;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.usuario.Usuario;

public record DadosDetalhamentoPacientes(Long id, String nome, String email, String telefone, Endereco endereco) {
    public DadosDetalhamentoPacientes(Paciente paciente) {
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getTelefone(), paciente.getEndereco());
    }
}
