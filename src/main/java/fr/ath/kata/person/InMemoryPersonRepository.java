package fr.ath.kata.person;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InMemoryPersonRepository implements PersonRepository{

    Set<Person> collection = new HashSet<>();

    @Override
    public void save(Person person) {
        collection.add(person);
    }

    @Override
    public List<Person> findAll() {
        return new ArrayList<>(collection);
    }
}
