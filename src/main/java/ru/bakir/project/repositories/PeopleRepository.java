package ru.bakir.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bakir.project.models.Person;


@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {

}
