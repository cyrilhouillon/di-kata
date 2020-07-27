package fr.ath.kata.person;

public class PersonServiceWithImplementationDependencie implements PersonService {

    private InMemoryPersonRepository personRepository;

    public void setPersonRepository(InMemoryPersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public void save(Person person) {
        personRepository.save(person);
    }

    @Override
    public int getMobSize() {
        return personRepository.findAll().size();
    }
}
