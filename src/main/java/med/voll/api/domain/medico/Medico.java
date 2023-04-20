package med.voll.api.domain.medico;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.endereco.Endereco;
import med.voll.api.domain.usuario.Usuario;

@Table(name = "medicos")
@Entity(name = "Medico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String crm;
    private String telefone;
    private Boolean ativo;

    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    @Embedded
    private Endereco endereco;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;


    public Medico(DadosCadastrosMedicos dados) {
        this.nome = dados.nome();
        this.email = dados.email();
        this.crm = dados.crm();
        this.telefone = dados.telefone();
        this.endereco = new Endereco(dados.endereco());
        this.especialidade = dados.especialidade();
        this.ativo = true;
        this.usuario = new Usuario(dados.usuario());
    }

    public void atualizarInfo(DadosAtualizarMedicos dados) {
        if (dados.telefone() != null) {
            this.telefone = dados.telefone();
        }
        if (dados.nome() != null) {
            this.nome = dados.nome();
        }
        if (dados.endereco() != null) {
            this.endereco.atualizarInfo(dados.endereco());
        }
        if (dados.email() != null) {
            this.email = dados.email();
        }

    }

    public void inativar() {
        this.ativo = false;
    }
}
