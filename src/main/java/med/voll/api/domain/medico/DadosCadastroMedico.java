package med.voll.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.endereco.DadosEndereco;

public record DadosCadastroMedico(

        @NotBlank(message = "{commom.nome.obrigatorio}")
        String nome,
        @NotBlank(message = "{commom.email.obrigatorio}")
        @Email(message = "{commom.email.invalido}")
        String email,
        @NotBlank(message = "{commom.telefone.obrigatorio}")
        @Pattern(regexp="\\d{11}", message = "{commom.telefone.invalido}")
        String telefone,
        @NotBlank(message = "{medico.crm.obrigatorio}")
        @Pattern(regexp="\\d{4,6}", message = "{medico.crm.invalido}")
        String crm,
        @NotNull(message = "{medico.especialidade.obrigatorio}")
        Especialidade especialidade,
        @NotNull(message = "{commom.endereco.obrigatorio}")
        @Valid //Informa ao bean validation para realizar as validaçõe também no DTO DadosEndereco
        DadosEndereco endereco) {
}
