package fr.ath.kata.person;

public class PersonService {

    private InMemoryPersonRepository personRepository;

    public void setPersonRepository(InMemoryPersonRepository personRepository) {
        this.personRepository = personRepository;
    }


    public void save(Person person) {
        personRepository.save(person);
    }

    public int getMobSize() {
        return personRepository.findAll().size();
    }
}
