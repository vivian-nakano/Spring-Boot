package com.example.demo;

import java.util.List; 
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RestController
public class Controller implements WebMvcConfigurer {

	public void addViewControllers(ViewControllerRegistry index) {
		index.addViewController("/").setViewName("forward:/index.html");
	}

	@Autowired
	private ManutencaoRepository repository;
	
	@Autowired
	private Services service;
	
	 @GetMapping("/teste")
	    public ResponseEntity<List<ManutencaoTable>> listAllItens() {
	        List<ManutencaoTable> itens= service.findAllItens();
	        if(itens.isEmpty()){
	            return new ResponseEntity<List<ManutencaoTable>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
	        }
	        return new ResponseEntity<List<ManutencaoTable>>(itens, HttpStatus.ACCEPTED);
	    }

	//localhost:8080/manutencoes --> vai pegar todos os dados da tabela
	//@GetMapping("/manutencoes")
	//public List<ManutencaoTable> buscarTodos() {
	//	return repository.findAll();
	//}
	
	//@GetMapping("/manutencoes/id/{id}")
	//public Optional<ManutencaoTable> buscarUm(@PathVariable Long id) {
	//	return repository.findById(id);
	//}
	
	//deste jeito nao retornar erro
	 //	@GetMapping("/manutencoes/id/{id}")
	 //	public Optional<ManutencaoTable> buscarUm(@PathVariable Long id) {
	 //	return repository.findById(id);
	 //}
	
@GetMapping("/manutencoes/id/{id}")
	public ResponseEntity<ManutencaoTable> getById(@PathVariable Long id){
		return repository.findById(id).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}

	//post é inserir, neste caso mesma URL acima
	@PostMapping("/manutencoes")
	public ManutencaoTable criar(@RequestBody ManutencaoTable objetinho) {
		repository.save(objetinho);
		return objetinho;
	}
	
	@PutMapping("/manutencoes/{id}")
	public ManutencaoTable atualizar(@PathVariable Long id, @RequestBody ManutencaoTable objetinho) {
	objetinho.setId(id); //força a alteração do body, setar=mudar
	repository.save(objetinho);
	return objetinho;
	}
	
	@GetMapping("/manutencoes/teste/{nome}")
	public List<ManutencaoTable> buscarPorNome(@PathVariable String nome) {
		return repository.findByNome(nome);
	}
	
	@GetMapping("/manutencoes/{nome}/{categoria}")
	public Optional<ManutencaoTable> findByNomeAndCategoria(@PathVariable String nome, @PathVariable String categoria) {
	return repository.findByNomeAndCategoria(nome, categoria);	
	}

	@DeleteMapping("/manutencoes/deleteId/{id}")
	public void delete(@PathVariable Long id) {
		repository.deleteById(id);
	}
	// INJETAR O REPOSITORIO
	// para acessar a página pelo localhost:8080 -> te leva pra index

	// MÉTODO GET, PUT, POST, DELETE
	// GET BY ID, GET BY NOME,

	// find All: comando nativo ; findById ; save ---> NAO PRECISA COLOCAR NO
	// REPOSITORY
	
	//path variable serve para pegar o parâmetro - tipo captura
	//putmapping: serve para atualizar, primeiro pega com path variable depois insere com request body. 

	// diminui alguns codigos, mas nao entendi bem o porquê ainda
	// vai pegar apenas id
	// option é pra evitar
	// manutencao table nome da table
	// pathvariable: pega todos os dados com id
}
