package br.com.cotiinformatica.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class TarefaRequestDto {

	@Size(min = 8, max = 150, message = "Por favor informe de 8 a 150 caracteres.")
	@NotEmpty(message = "Por favor informe o título da tarefa.")
	private String titulo;

	@NotEmpty(message = "Por favor informe a data da tarefa.")
	@Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Por favor informe a data no formato yyyy-MM-dd.")
	private String data;

	@NotEmpty(message = "Por favor informe a hora da tarefa.")
	@Pattern(regexp = "^\\d{2}:\\d{2}$", message = "Por favor informe a hora no formato hh:mm.")
	private String hora;

	@NotNull(message = "Por favor informe se a tarefa foi finalizada.")
	private Boolean finalizado;

	@NotNull(message = "Por favor informe a categoria da tarefa.")
	private UUID categoriaId;

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public Boolean getFinalizado() {
		return finalizado;
	}

	public void setFinalizado(Boolean finalizado) {
		this.finalizado = finalizado;
	}

	public UUID getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(UUID categoriaId) {
		this.categoriaId = categoriaId;
	}

}
