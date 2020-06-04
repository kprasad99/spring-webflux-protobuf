package io.github.kprasad99.proto.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.kprasad99.proto.orm.dao.PersonDao;
import io.github.kprasad99.proto.orm.model.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping("/api/person")
public class PersonRestService {

	@Autowired
	private PersonDao personDao;
	
	@GetMapping
	public Flux<Person> list(){
		return Flux.fromIterable(personDao.findAll()).subscribeOn(Schedulers.boundedElastic());
	}
	
	@GetMapping("/{id}")
	public Mono<Person> findById(@PathVariable("id")int id){
		return Mono.justOrEmpty(personDao.findById(id)).subscribeOn(Schedulers.boundedElastic());
	}
	
}
