package br.com.keyrus.beneficiarios.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.keyrus.beneficiarios.domain.Consulta;

public interface ConsultaRepository extends JpaRepository<Consulta, Integer>{

	Optional<Consulta> getConsultaByBeneficiarioCpfAndDataHora(String cpf, LocalDateTime dataHora);
	
	List<Consulta> getConsultasByBeneficiarioCpf(String cpf);
}
