package fr.ath.kata.person;

import fr.ath.kata.di.annotations.Component;

@Component
public class PersonServiceWithInterfaceDependencie implements PersonService {

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
