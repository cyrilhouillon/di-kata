package fr.ath.kata.person;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NoDatabasePersonRepository implements PersonRepository {

    Set<Person> collection = new HashSet<>();

    @Override
    public void save(Person person) {
        throw new DatabaseNotAvailableException();
    }

    @Override
    public List<Person> findAll() {
        throw new DatabaseNotAvailableException();
    }
}
