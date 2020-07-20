package fr.ath.kata.person;

public class PersonService {

    private PersonRepository personRepository;

    public void setPersonRepository(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }


    public void save(Person person) {
        personRepository.save(person);
    }

    public int getMobSize() {
        return personRepository.findAll().size();
    }
}
