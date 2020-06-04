package io.github.kprasad99.proto.orm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.kprasad99.proto.orm.model.Person;

@Repository
public interface PersonDao extends JpaRepository<Person, Integer> {

}
