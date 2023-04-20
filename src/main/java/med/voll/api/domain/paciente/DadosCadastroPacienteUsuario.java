package med.voll.api.domain.paciente;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import med.voll.api.domain.usuario.DadosCadastroUsuario;
@Getter
@Setter
public class DadosCadastroPacienteUsuario {

    @Valid
    private DadosCadastrosPacientes dadosPaciente;

    @Valid
    private DadosCadastroUsuario dadosUsuario;
}
