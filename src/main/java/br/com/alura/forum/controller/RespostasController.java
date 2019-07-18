package br.com.alura.forum.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.controller.dto.DetalheDaRespostaDto;
import br.com.alura.forum.controller.dto.RespostaDto;
import br.com.alura.forum.controller.form.AtualizacaoRespostaForm;
import br.com.alura.forum.controller.form.RespostaForm;
import br.com.alura.forum.modelo.Resposta;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.RespostaRepository;
import br.com.alura.forum.repository.TopicoRepository;

@RestController
@RequestMapping("/respostas")
public class RespostasController {

	@Autowired
	private RespostaRepository respostaRepository;
	
	@Autowired
	private TopicoRepository topicoRepository;
	
	@GetMapping
	public Page<RespostaDto> lista(String tituloTopico, 
			@PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 10) Pageable paginacao) {
		if (tituloTopico == null) {
			Page<Resposta> respostas = respostaRepository.findAll(paginacao);
			return RespostaDto.converter(respostas);
		}
		else {
			Page<Resposta> respostas = respostaRepository.findByTopicoTitulo(tituloTopico, paginacao);
			return RespostaDto.converter(respostas);
		}		
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<RespostaDto> cadastrar(@RequestBody @Valid RespostaForm form, UriComponentsBuilder uriBuilder){
		Resposta resposta = form.converter(topicoRepository);
		respostaRepository.save(resposta);
		
		URI uri = uriBuilder.path("/respostas/{id}").buildAndExpand(resposta.getId()).toUri();
		return ResponseEntity.created(uri).body(new RespostaDto(resposta));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DetalheDaRespostaDto> detalhar(@PathVariable Long id) {
		Optional<Resposta> resposta = respostaRepository.findById(id);
		if (resposta.isPresent()) {
			return ResponseEntity.ok(new DetalheDaRespostaDto(resposta.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<RespostaDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoRespostaForm form){
		Optional<Topico> optional = topicoRepository.findById(id);
		if (optional.isPresent()) {
			Resposta resposta = form.atualizar(id, respostaRepository);			
			return ResponseEntity.ok(new RespostaDto(resposta));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> remover(@PathVariable Long id){
		Optional<Resposta> resposta = respostaRepository.findById(id);
		if (resposta.isPresent()) {
			respostaRepository.deleteById(id);		
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
}
