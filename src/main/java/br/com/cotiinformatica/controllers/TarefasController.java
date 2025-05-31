package br.com.cotiinformatica.controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.dtos.TarefaRequestDto;
import br.com.cotiinformatica.dtos.TarefaResponseDto;
import br.com.cotiinformatica.entities.Tarefa;
import br.com.cotiinformatica.repositories.CategoriaRepository;
import br.com.cotiinformatica.repositories.TarefaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/tarefas")
@Tag(name = "Tarefas", description = "Serviço para operações relacionadas a tarefas.")
public class TarefasController {

	// injeção de dependência (autoinicializacao de um objeto)
	@Autowired
	TarefaRepository tarefaRepository;

	@Autowired
	CategoriaRepository categoriaRepository;

	@Autowired
	ModelMapper mapper;

	@Operation(summary = "Cadastro de tarefas", description = "Cria uma nova tarefa no banco de dados")
	@PostMapping
	public TarefaResponseDto post(@RequestBody @Valid TarefaRequestDto request) {

		// buscar a categoria no banco de dados pelo ID informado
		var categoria = categoriaRepository.findById(request.getCategoriaId())
				.orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada. Verifique o ID informado"));

		// Copiar os dados do DTO para a entidade Tarefa
		var tarefa = mapper.map(request, Tarefa.class);
		tarefa.setId(UUID.randomUUID()); // gerando um ID único para a tarefa);
		tarefa.setCategoria(categoria); // dados da categoria associado à tarefa

		// Gravar a tarefa no banco de dados
		tarefaRepository.save(tarefa);

		// Copiar os dados da entidade Tarefa para o TarefaResponseDto
		return mapper.map(tarefa, TarefaResponseDto.class);
	}

	@Operation(summary = "Edição de tarefas", description = "Atualiza os dadoe de uma tarefa no banco de dados")
	@PutMapping("{id}")
	public TarefaResponseDto put(@PathVariable UUID id, @RequestBody @Valid TarefaRequestDto request) {

		// Verificar se a tarefa NÃO existe no banco de dados
		if (!tarefaRepository.existsById(id))
			throw new IllegalArgumentException("Tarefa não encontrada. Verifique o ID informado");

		// buscar a categoria no banco de dados pelo ID informado
		var categoria = categoriaRepository.findById(request.getCategoriaId())
				.orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada. Verifique o ID informado"));

		// Copiar os dados do request para a entidade
		var tarefa = mapper.map(request, Tarefa.class);
		tarefa.setId(id); // capturando o ID enviado da tarefa que será editada
		tarefa.setCategoria(categoria); // dados da categoria associado à tarefa

		// Atualizar a tarefa no banco de dados
		tarefaRepository.save(tarefa);

		// Copiar os dados da Tarefa cadastrada para o TarefaResponseDto
		return mapper.map(tarefa, TarefaResponseDto.class);
	}

	@Operation(summary = "Exclusão de tarefas", description = "Exclui uma tarefa no banco de dados")
	@DeleteMapping("{id}")
	public TarefaResponseDto delete(@PathVariable UUID id) {

		// Buscar a tarefa no banco de dados pelo ID informado
		var tarefa = tarefaRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Tarefa não encontrada. Verifique o ID informado"));

		// Excluir a tarefa do banco de dados
		tarefaRepository.delete(tarefa);

		// Retornando os dados da Tarefa que foram excluídos
		return mapper.map(tarefa, TarefaResponseDto.class);
	}

	@Operation(summary = "Consulta de tarefas", description = "Retorna todas as tarefa cadastradas no banco de dados")
	@GetMapping("{dataMin}/{dataMax}")
	public List<TarefaResponseDto> get(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataMin,
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataMax) {

		// A primeira data deve ficar com a hora 00:00:00
		var inicio = dataMin.atStartOfDay();

		// A ultima data deve ficar com a hora 23:59:59
		var fim = dataMax.atTime(LocalTime.MAX);

		// Convertendo as datas para o padrao Date (java.util)
		var dataInicio = Date.from(inicio.atZone(ZoneId.systemDefault()).toInstant());
		var dataFim = Date.from(fim.atZone(ZoneId.systemDefault()).toInstant());

		// Consultar as tarefas no banco de dados dentro do período de datas
		var tarefas = tarefaRepository.findByDatas(dataInicio, dataFim);

		// usando o ModelMapper para copiar os dados da lista de tarefas para a lista de
		// TarefaResponseDto

		return tarefas.stream().map(tarefa -> mapper.map(tarefa, TarefaResponseDto.class)).collect(Collectors.toList());
	}
	
	@Operation(summary = "Consulta de tarefa por Id", description = "Retorna os dados de uma tarefa do banco de dados")
	@GetMapping("{id}")
	public TarefaResponseDto get(@PathVariable UUID id) {

		// Buscar a tarefa no banco de dados pelo ID informado
		var tarefa = tarefaRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Tarefa não encontrada. Verifique o ID informado"));
		
		//copiar os dados da tarefa para o TarefaResponseDto
		return mapper.map(tarefa, TarefaResponseDto.class);
	}

}
