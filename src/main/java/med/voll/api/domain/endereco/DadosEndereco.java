package med.voll.api.domain.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosEndereco(
        @NotBlank(message = "{endereco.logradouro.obrigatorio}")
        String logradouro,
        @NotBlank(message = "{endereco.bairro.obrigatorio}")
        String bairro,
        @NotBlank(message = "{endereco.cep.obrigatorio}")
        @Pattern(regexp="\\d{8}", message = "{endereco.cep.invalido}")
        String cep,
        @NotBlank(message = "{endereco.cidade.obrigatorio}")
        String cidade,
        @NotBlank(message = "{endereco.uf.obrigatorio}")
        String uf,
        String complemento,
        String numero) {
}
