package fr.ath.kata.di;

import fr.ath.kata.person.InMemoryPersonRepository;
import fr.ath.kata.person.Person;
import fr.ath.kata.person.PersonService;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ComponentRegistryTest {


    @Test
    void could_fetch_instance_of_registred_component() {

        ComponentRegistry componentRegistry = new ComponentRegistry();

        componentRegistry.register(PersonService.class);
        PersonService result = componentRegistry.fetch(PersonService.class);

        assertThat(result).isNotNull();
        assertThat(result).isInstanceOf(PersonService.class);
    }

    @Test
    void should_inject_dependencies_between_components() {

        ComponentRegistry componentRegistry = new ComponentRegistry();

        componentRegistry.register(PersonService.class);
        componentRegistry.register(InMemoryPersonRepository.class);

        PersonService result = componentRegistry.fetch(PersonService.class);

        testPersonService(result);
    }

    private void testPersonService(PersonService result) {
        List<Person> persons = Arrays.asList(
                new Person("Pierre", "Martinet"),
                new Person("Odile", "Croq"));

        persons.forEach(result::save);

        assertThat(result.getMobSize()).isEqualTo(persons.size());
    }

}
