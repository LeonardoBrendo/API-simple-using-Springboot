package com.dev.simpleapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dev.simpleapi.model.Pessoa;
import com.dev.simpleapi.repository.PessoaRepository;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

	@Autowired
	private PessoaRepository pessoaRepository;

	PessoaController(PessoaRepository pessoaRepository) {
		this.pessoaRepository = pessoaRepository;
	}

	//Esse método vai listar todas pessoas.
	@GetMapping
	public List<Pessoa> findAll() {
		return pessoaRepository.findAll();
	}

	//Esse método retornar apenas uma pessoa, conforme id passado.
	@GetMapping(path = { "/{id}" })
	public ResponseEntity<Pessoa> findById(@PathVariable long id) {
		return pessoaRepository.findById(id).map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.notFound().build());
	}

	//Esse método vai criar uma pessoa.
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Pessoa add(@RequestBody Pessoa pessoa) {
		return pessoaRepository.save(pessoa);
	}

	//Esse método vai atualizar as informações de uma pessoa
	@PutMapping(value = "/{id}")
	public ResponseEntity<Pessoa> update(@PathVariable("id") long id, @RequestBody Pessoa pessoa) {
		return pessoaRepository.findById(id).map(record -> {
			record.setNome(pessoa.getNome());
			Pessoa updatedPerson = pessoaRepository.save(record);
			return ResponseEntity.ok().body(updatedPerson);
		}).orElse(ResponseEntity.notFound().build());
	}

	//Esse método vai excluir uma pessoa
	@DeleteMapping(path = { "/{id}" })
	public ResponseEntity<?> delete(@PathVariable long id) {
		return pessoaRepository.findById(id).map(record -> {
			pessoaRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}).orElse(ResponseEntity.notFound().build());
	}

}
