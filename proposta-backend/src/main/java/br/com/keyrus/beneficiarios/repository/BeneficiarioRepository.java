package br.com.keyrus.beneficiarios.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.keyrus.beneficiarios.domain.Beneficiario;

public interface BeneficiarioRepository extends JpaRepository<Beneficiario, String>{

	Optional<Beneficiario> getBeneficiarioByCpf(String cpf);
	
}
