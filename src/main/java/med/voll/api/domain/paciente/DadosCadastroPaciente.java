package med.voll.api.domain.paciente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.endereco.DadosEndereco;

public record DadosCadastroPaciente(
        @NotBlank(message = "{commom.nome.obrigatorio}")
        String nome,
        @NotBlank(message = "{commom.email.obrigatorio}")
        @Email(message = "{commom.email.invalido}")
        String email,
        @NotBlank(message = "{commom.telefone.obrigatorio}")
        @Pattern(regexp="\\d{11}", message = "{commom.telefone.invalido}")
        String telefone,
        @NotBlank(message = "{paciente.cpf.obrigatorio}")
        @Pattern(regexp="\\d{11}", message = "{paciente.cpf.invalido}")
        String cpf,
        @NotNull(message = "{commom.endereco.obrigatorio}")
        @Valid
        DadosEndereco endereco
) {
}
