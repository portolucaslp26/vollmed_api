package med.voll.api.domain.medico;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.domain.usuario.DadosCadastroUsuario;

public record DadosAtualizarMedicos(
        @NotNull
        Long id,
        String nome,
        String email,
        String telefone,
        DadosEndereco endereco,
        DadosCadastroUsuario usuario
) {
}
