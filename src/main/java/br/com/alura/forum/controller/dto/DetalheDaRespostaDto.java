package br.com.alura.forum.controller.dto;

import java.time.LocalDateTime;

import br.com.alura.forum.modelo.Resposta;

public class DetalheDaRespostaDto {

	private String mensagem;
	private String tituloTopico;
	private LocalDateTime dataCriacao;
	
	public DetalheDaRespostaDto(Resposta resposta) {
		this.mensagem = resposta.getMensagem();
		this.tituloTopico = resposta.getTopico().getTitulo();
		this.dataCriacao = resposta.getDataCriacao();
	}

	public String getMensagem() {
		return mensagem;
	}

	public String getTituloTopico() {
		return tituloTopico;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}
}
