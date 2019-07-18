package br.com.alura.forum.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.alura.forum.modelo.Resposta;
import br.com.alura.forum.repository.RespostaRepository;

public class AtualizacaoRespostaForm {

	@NotNull @NotEmpty @Length(min = 5)
	private String mensagem;
	
	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	public Resposta atualizar(Long id, RespostaRepository repostaRepository) {
		Resposta resposta = repostaRepository.getOne(id);
		resposta.setMensagem(this.mensagem);
		
		return resposta;
	}

}
