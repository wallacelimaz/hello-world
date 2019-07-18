package br.com.alura.forum.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.alura.forum.modelo.Resposta;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.TopicoRepository;

public class RespostaForm {

	@NotNull @NotEmpty @Length(min = 5)
	private String mensagem;
	@NotNull @NotEmpty @Length(min = 5)
	private String tituloTopico;
	
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public String getTituloTopico() {
		return tituloTopico;
	}
	public void setTituloTopico(String tituloTopico) {
		this.tituloTopico = tituloTopico;
	}
	
	public Resposta converter(TopicoRepository topicoRepository) {
		Topico topico = topicoRepository.findByTitulo(tituloTopico);
		return new Resposta(mensagem, topico);
	}	
}
