package io.github.kprasad99.proto.endpoint;

import java.util.function.Function;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.kprasad.person.proto.PersonProto.Person;
import io.github.kprasad99.proto.orm.dao.PersonDao;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping("/api/proto/person")
public class PersonProtoService {

	@Autowired
	private PersonDao personDao;

	@Autowired
	private ModelMapper mapper;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Flux<Person> list(){
		return Flux.fromIterable(personDao.findAll()).map(toProto).map(Person.Builder::build).subscribeOn(Schedulers.boundedElastic());
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<Person> findById(@PathVariable("id")int id){
		return Mono.justOrEmpty(personDao.findById(id)).map(toProto).map(Person.Builder::build).subscribeOn(Schedulers.boundedElastic());
	}

	public Function<io.github.kprasad99.proto.orm.model.Person, Person.Builder> toProto = p -> mapper.map(p,
			Person.Builder.class);

}
