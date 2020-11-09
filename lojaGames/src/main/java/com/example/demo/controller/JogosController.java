package com.example.demo.controller;

import java.util.List;  

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Jogos;
import com.example.demo.repository.JogosRepository;
 

@RestController //informa pro Spring que se trata de controlador para receber requisições
@RequestMapping("/jogos") //definir rota ou URL
@CrossOrigin("*") //* aceita requisicoes de qualquer origem

public class JogosController {

	@Autowired //SERVICO DE INJECAO DE DEPENDENCIA DO SPRING. Como é interface, nao dá pra instanciar. Instanciação fica por conta do spring: aí usa autowired
	private JogosRepository repository;
	
	@GetMapping
	public ResponseEntity<List<Jogos>> GetAll() {
		return ResponseEntity.ok(repository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Jogos> GetById(@PathVariable Long id) {
		return repository.findById(id)
				.map(resp -> ResponseEntity.ok(resp)) 
				.orElse(ResponseEntity.notFound().build()); 
	}
	
	@GetMapping("/titulo/{titulo}") 
	public ResponseEntity<List<Jogos>> GetByTitulo(@PathVariable String titulo) {
		return ResponseEntity.ok(repository.findAllByTituloContainingIgnoreCase(titulo));
	}
	
	@PostMapping
	public ResponseEntity<Jogos>post (@RequestBody Jogos jogos){
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(repository.save(jogos));
	}
	
	@PutMapping
	public ResponseEntity<Jogos> put (@RequestBody Jogos jogos){
		return ResponseEntity.status(HttpStatus.OK).body(repository.save(jogos));
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		repository.deleteById(id);
	}
	
}
