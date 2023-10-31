package br.com.keyrus.beneficiarios.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.keyrus.beneficiarios.domain.Especialidade;

public interface EspecialidadeRepository extends JpaRepository<Especialidade, Integer>{

	Optional<Especialidade> findEspecialidadeByNomeEspecialidade(String nomeEspecialidade);
	
}
