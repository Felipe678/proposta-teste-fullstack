package br.com.keyrus.beneficiarios.beneficiario.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.dao.DataIntegrityViolationException;

import br.com.keyrus.beneficiarios.domain.Beneficiario;
import br.com.keyrus.beneficiarios.dto.BeneficiarioDTO;
import br.com.keyrus.beneficiarios.exceptions.BeneficiarioCadastradoException;
import br.com.keyrus.beneficiarios.exceptions.BeneficiarioNaoCadastradoException;
import br.com.keyrus.beneficiarios.mapper.BeneficiarioMapper;
import br.com.keyrus.beneficiarios.repository.BeneficiarioRepository;
import br.com.keyrus.beneficiarios.service.BeneficiarioService;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BeneficiarioServiceTest {

	@Mock
	private BeneficiarioRepository repository;

	@Mock
	private BeneficiarioMapper mapper;

	@InjectMocks
	private BeneficiarioService service;

	@Test
	void testValidarBeneficiarioNaoCadastrado() throws BeneficiarioCadastradoException {
		String cpf = "12345678901";

		// Preparando o cenário do teste
		when(repository.getBeneficiarioByCpf(cpf)).thenReturn(Optional.empty());

		// Executando o método que queremos testar
		assertDoesNotThrow(() -> service.validarBeneficiario(cpf));

		// Verificando as interações com o mock
		verify(repository, times(1)).getBeneficiarioByCpf(cpf);
	}

	@Test
	void testValidarBeneficiarioCadastrado() {
		String cpf = "12345678901";

		// Preparando o cenário do teste
		when(repository.getBeneficiarioByCpf(cpf)).thenReturn(Optional.of(new Beneficiario()));

		// Executando o método que queremos testar e verificando a exceção
		BeneficiarioCadastradoException excecao = assertThrows(BeneficiarioCadastradoException.class,
				() -> service.validarBeneficiario(cpf));

		// Verificando a mensagem da exceção
		assertEquals("Beneficiario já cadastrado", excecao.getMessage());

		// Verificando as interações com o mock
		verify(repository, times(1)).getBeneficiarioByCpf(cpf);
	}

	@Test
	void testCadastrarBeneficiario() throws BeneficiarioCadastradoException {
		// Preparando o cenário do teste
		BeneficiarioDTO beneficiarioDTO = new BeneficiarioDTO("12345678901", "João", "1990-01-15");
		Beneficiario beneficiarioMock = new Beneficiario("12345678901", "João", LocalDate.of(1990, 1, 15));

		when(mapper.toEntity(beneficiarioDTO)).thenReturn(beneficiarioMock);
		when(repository.save(beneficiarioMock)).thenReturn(beneficiarioMock);
		when(mapper.toDTO(beneficiarioMock)).thenReturn(beneficiarioDTO);

		// Executando o método que queremos testar
		BeneficiarioDTO resultado = service.cadastrarBeneficiario(beneficiarioDTO);

		// Verificando o resultado
		assertEquals(beneficiarioDTO, resultado);

		// Verificando as interações com o mock
		verify(mapper, times(1)).toEntity(beneficiarioDTO);
		verify(repository, times(1)).save(beneficiarioMock);
		verify(mapper, times(1)).toDTO(beneficiarioMock);
	}

	@Test
	void testCadastrarBeneficiarioExistente() {
		// Preparando o cenário do teste
		BeneficiarioDTO beneficiarioDTO = new BeneficiarioDTO("12345678901", "João", "1990-01-15");
		Beneficiario beneficiarioMock = new Beneficiario("12345678901", "João", LocalDate.of(1990, 1, 15));

		when(mapper.toEntity(beneficiarioDTO)).thenReturn(beneficiarioMock);
		when(repository.save(beneficiarioMock))
				.thenThrow(new DataIntegrityViolationException("Beneficiario já cadastrado"));

		// Executando o método que queremos testar e verificando a exceção
		BeneficiarioCadastradoException excecao = assertThrows(BeneficiarioCadastradoException.class,
				() -> service.cadastrarBeneficiario(beneficiarioDTO));

		// Verificando a mensagem da exceção
		assertEquals("Beneficiario já cadastrado", excecao.getMessage());

		// Verificando as interações com o mock
		verify(mapper, times(1)).toEntity(beneficiarioDTO);
		verify(repository, times(1)).save(beneficiarioMock);
	}

	@Test
	void testCadastrarBeneficiarioComExcecao() {
		// Preparando o cenário do teste
		BeneficiarioDTO beneficiarioDTO = new BeneficiarioDTO("12345678901", "João", "1990-01-15");
		Beneficiario beneficiarioMock = new Beneficiario("12345678901", "João", LocalDate.of(1990, 1, 15));

		when(mapper.toEntity(beneficiarioDTO)).thenReturn(beneficiarioMock);
		when(repository.save(beneficiarioMock)).thenThrow(new RuntimeException("Erro no repositório"));

		// Executando o método que queremos testar e verificando a exceção
		BeneficiarioCadastradoException excecao = assertThrows(BeneficiarioCadastradoException.class,
				() -> service.cadastrarBeneficiario(beneficiarioDTO));

		// Verificando a mensagem da exceção
		assertEquals("Beneficiario já cadastrado", excecao.getMessage());

		// Verificando as interações com o mock
		verify(mapper, times(1)).toEntity(beneficiarioDTO);
		verify(repository, times(1)).save(beneficiarioMock);
	}

	@Test
	void testLimparBaseBeneficiario() throws BeneficiarioNaoCadastradoException {
		service.limparBase();

		Mockito.verify(repository, Mockito.times(1)).deleteAll();
	}

	@Test
	void testExcluirBeneficiarioException() throws BeneficiarioNaoCadastradoException {
		// Preparando o cenário do teste
		doThrow(new RuntimeException("Erro no repositório")).when(repository).deleteAll();

		// Executando o método que queremos testar e verificando a exceção
		BeneficiarioNaoCadastradoException excecao = assertThrows(BeneficiarioNaoCadastradoException.class,
				() -> service.limparBase());

		// Verificando a mensagem da exceção
		assertEquals("Beneficiario não cadastrado", excecao.getMessage());

		// Verificando as interações com o mock
		verify(repository, times(1)).deleteAll();
	}

	@Test
	void testExcluirBeneficiario() throws BeneficiarioNaoCadastradoException {
		String cpf = "12345678901";

		// Executando o método que queremos testar
		assertDoesNotThrow(() -> service.excluirBeneficiario(cpf));

		// Verificando as interações com o mock
		verify(repository, times(1)).deleteById(cpf);
	}

	@Test
	void testExcluirBeneficiarioComExcecao() {
		// Preparando o cenário do teste
		String cpf = "12345678901";
		doThrow(new RuntimeException("Erro no repositório")).when(repository).deleteById(cpf);

		// Executando o método que queremos testar e verificando a exceção
		BeneficiarioNaoCadastradoException excecao = assertThrows(BeneficiarioNaoCadastradoException.class,
				() -> service.excluirBeneficiario(cpf));

		// Verificando a mensagem da exceção
		assertEquals("Beneficiario não cadastrado", excecao.getMessage());

		// Verificando as interações com o mock
		verify(repository, times(1)).deleteById(cpf);
	}

	@Test
	void testConsultarBeneficiario() throws BeneficiarioNaoCadastradoException {
		String cpf = "12345678901";

		// Preparando o cenário do teste
		Beneficiario beneficiarioMock = new Beneficiario(cpf, "João", LocalDate.of(1990, 1, 15));
		BeneficiarioDTO beneficiarioDTOMock = new BeneficiarioDTO(cpf, "João", "1990-01-15");

		when(repository.getBeneficiarioByCpf(cpf)).thenReturn(Optional.of(beneficiarioMock));
		when(mapper.toDTO(beneficiarioMock)).thenReturn(beneficiarioDTOMock);

		// Executando o método que queremos testar
		BeneficiarioDTO resultado = service.consultarBeneficiario(cpf);

		// Verificando o resultado
		assertEquals(beneficiarioDTOMock, resultado);

		// Verificando as interações com o mock
		verify(repository, times(1)).getBeneficiarioByCpf(cpf);
		verify(mapper, times(1)).toDTO(beneficiarioMock);
	}

	@Test
	void testConsultarBeneficiarioBeneficiarioNaoCadastrado() {
		// Preparando o cenário do teste
		String cpf = "12345678901";
		when(repository.getBeneficiarioByCpf(cpf)).thenReturn(Optional.empty());

		// Executando o método que queremos testar e verificando a exceção
		BeneficiarioNaoCadastradoException excecao = assertThrows(BeneficiarioNaoCadastradoException.class,
				() -> service.consultarBeneficiario(cpf));

		// Verificando a mensagem da exceção
		assertEquals("Beneficiario não cadastrado", excecao.getMessage());

		// Verificando as interações com o mock
		verify(repository, times(1)).getBeneficiarioByCpf(cpf);
	}

	@Test
	void testConsultarBeneficiarioComExcecao() {
		// Preparando o cenário do teste
		String cpf = "12345678901";
		when(repository.getBeneficiarioByCpf(cpf)).thenThrow(new RuntimeException("Erro no repositório"));

		// Executando o método que queremos testar e verificando a exceção
		BeneficiarioNaoCadastradoException excecao = assertThrows(BeneficiarioNaoCadastradoException.class,
				() -> service.consultarBeneficiario(cpf));

		// Verificando a mensagem da exceção
		assertEquals("Beneficiario não cadastrado", excecao.getMessage());

		// Verificando as interações com o mock
		verify(repository, times(1)).getBeneficiarioByCpf(cpf);
	}
}
