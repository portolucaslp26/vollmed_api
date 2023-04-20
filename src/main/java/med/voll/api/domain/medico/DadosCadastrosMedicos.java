package med.voll.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.endereco.*;
import med.voll.api.domain.usuario.DadosCadastroUsuario;

public record DadosCadastrosMedicos(
        @NotBlank // apenas para strings
        String nome,
        @NotBlank
        @Email
        String email,
        @NotBlank
        @Pattern(regexp="\\d{10,11}")
        String telefone,
        @NotBlank
        @Pattern(regexp="\\d{4,6}")
        String crm,
        @NotNull
        Especialidade especialidade,
        @NotNull
        @Valid
        DadosEndereco endereco,
        @Valid
        @NotNull
        DadosCadastroUsuario usuario
) {

}
