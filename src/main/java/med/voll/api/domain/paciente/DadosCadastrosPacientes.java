package med.voll.api.domain.paciente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.domain.usuario.DadosCadastroUsuario;

public record DadosCadastrosPacientes(
        @NotBlank // apenas para strings
        String nome,
        @NotBlank
        @Email
        String email,
        @NotBlank
        @Pattern(regexp="\\d{10,11}")
        String telefone,
        @NotNull
        @Valid
        DadosEndereco endereco,

        @Valid
        @NotNull
        DadosCadastroUsuario usuario
) {
}
