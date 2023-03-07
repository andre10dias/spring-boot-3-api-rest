package med.voll.api.paciente;

import jakarta.persistence.Enumerated;
import med.voll.api.endereco.Endereco;

public record DadosListagemPaciente(
        Long id, String nome, String email, String telefone, String cpf, Endereco endereco) {

    public DadosListagemPaciente(Paciente paciente) {
        this(
                paciente.getId(),
                paciente.getNome(),
                paciente.getEmail(),
                paciente.getTelefone(),
                paciente.getCpf(),
                paciente.getEndereco()
        );
    }
}
