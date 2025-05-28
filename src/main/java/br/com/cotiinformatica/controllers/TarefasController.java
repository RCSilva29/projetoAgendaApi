package br.com.cotiinformatica.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
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
import br.com.cotiinformatica.repositories.TarefaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/tarefas")
@Tag(name = "Tarefas", description = "Serviço para operações relacionadas a tarefas.")
public class TarefasController {

	@Autowired
	TarefaRepository tarefaRepository;

	@Operation(summary = "Cadastro de tarefas", description = "Cria uma nova tarefa no banco de dados")
	@PostMapping
	public TarefaResponseDto post(@RequestBody @Valid TarefaRequestDto request) {
		// TODO implementar um serviço para cadastro de tarefa

		return null;
	}

	@Operation(summary = "Edição de tarefas", description = "Atualiza os dadoe de uma tarefa no banco de dados")
	@PutMapping("{id}")
	public TarefaResponseDto put(@PathVariable @RequestBody @Valid TarefaRequestDto request) {
		// TODO Implementar o serviço para edição de tarefa

		return null;
	}

	@Operation(summary = "Exclusão de tarefas", description = "Exclui uma tarefa no banco de dados")
	@DeleteMapping("{id}")
	public TarefaResponseDto delete(@PathVariable UUID id) {
		// TODO implementar o serviço para exclusão de tarefa

		return null;
	}

	@Operation(summary = "Consulta de tarefas", description = "Retorna todas as tarefa cadastradas no banco de dados")
	@GetMapping
	public List<TarefaResponseDto> get() {
		// TODO implementar o serviço para consulta de tarefa

		return null;
	}

}
