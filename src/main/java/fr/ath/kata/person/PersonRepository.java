package fr.ath.kata.person;

import java.util.List;

public interface PersonRepository {
    void save(Person person);

    List<Person> findAll();
}
