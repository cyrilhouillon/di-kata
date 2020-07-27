package fr.ath.kata.person;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PersonServiceTest {

    @Test
    // the test will fail
    void personService_should_return_exact_count_of_saved_persons() {


        PersonServiceWithImplementationDependencie personService = new PersonServiceWithImplementationDependencie();
        personService.setPersonRepository(new InMemoryPersonRepository());

        testPersonService(personService);

    }

    public static void testPersonService(PersonService service) {
        List<Person> persons = Arrays.asList(
                new Person("Pierre", "Martinet"),
                new Person("Odile", "Croq"));

        persons.forEach(service::save);

        assertThat(service.getMobSize()).isEqualTo(persons.size());
    }



}
