package fr.ath.kata.di;

import fr.ath.kata.person.CountinCallsPersonService;
import fr.ath.kata.person.InMemoryPersonRepository;
import fr.ath.kata.person.InMemoryRepositorySubSubclass;
import fr.ath.kata.person.InMemoryRepositorySubclass;
import fr.ath.kata.person.Person;
import fr.ath.kata.person.PersonService;
import fr.ath.kata.person.PersonServiceWithImplementationDependencie;
import fr.ath.kata.person.PersonServiceWithInterfaceDependencie;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ComponentRegistryTest {


    @Test
    void could_fetch_instance_of_registred_component() {

        ComponentRegistry componentRegistry = new ComponentRegistry();

        componentRegistry.register(InMemoryPersonRepository.class);
        componentRegistry.register(PersonServiceWithImplementationDependencie.class);
        PersonServiceWithImplementationDependencie result = componentRegistry.fetch(PersonServiceWithImplementationDependencie.class);

        assertThat(result).isNotNull();
        assertThat(result).isInstanceOf(PersonServiceWithImplementationDependencie.class);
    }

    @Test
    void should_inject_dependencies_between_components() {

        ComponentRegistry componentRegistry = new ComponentRegistry();

        componentRegistry.register(PersonServiceWithImplementationDependencie.class);
        componentRegistry.register(InMemoryPersonRepository.class);

        PersonServiceWithImplementationDependencie result = componentRegistry.fetch(PersonServiceWithImplementationDependencie.class);

        testPersonService(result);
    }

    @Test
    void should_throw_exception_when_dependencie_is_not_satisfied() {

        ComponentRegistry componentRegistry = new ComponentRegistry();

        componentRegistry.register(PersonServiceWithImplementationDependencie.class);

        assertThatThrownBy(() -> componentRegistry.fetch(PersonService.class))
                .hasMessage("Missing depencie : class fr.ath.kata.person.InMemoryPersonRepository needed for bean class fr.ath.kata.person.PersonServiceWithImplementationDependencie");
    }

    @Test
    void should_allow_subclasses_as_dependency_candidate() {

        ComponentRegistry componentRegistry = new ComponentRegistry();

        componentRegistry.register(PersonServiceWithImplementationDependencie.class);
        componentRegistry.register(InMemoryRepositorySubclass.class);

        PersonServiceWithImplementationDependencie result = componentRegistry.fetch(PersonServiceWithImplementationDependencie.class);

        testPersonService(result);
    }

    @Test
    void should_allow_any_subclasses_level_as_dependency_candidate() {

        ComponentRegistry componentRegistry = new ComponentRegistry();

        componentRegistry.register(PersonServiceWithImplementationDependencie.class);
        componentRegistry.register(InMemoryRepositorySubSubclass.class);

        PersonServiceWithImplementationDependencie result = componentRegistry.fetch(PersonServiceWithImplementationDependencie.class);

        testPersonService(result);
    }

    @Test
    void should_fetch_component_by_interface() {

        ComponentRegistry componentRegistry = new ComponentRegistry();

        componentRegistry.register(PersonServiceWithImplementationDependencie.class);
        componentRegistry.register(InMemoryPersonRepository.class);

        PersonService result = componentRegistry.fetch(PersonService.class);

        testPersonService(result);
    }

    @Test
    void could_declare_dependencies_as_interfaces() {

        ComponentRegistry componentRegistry = new ComponentRegistry();

        componentRegistry.register(PersonServiceWithInterfaceDependencie.class);
        componentRegistry.register(InMemoryPersonRepository.class);

        PersonService result = componentRegistry.fetch(PersonService.class);

        testPersonService(result);
    }

    @Test
    void should_inject_dependencies_only_once() {

        ComponentRegistry componentRegistry = new ComponentRegistry();

        componentRegistry.register(CountinCallsPersonService.class);
        componentRegistry.register(InMemoryPersonRepository.class);

        CountinCallsPersonService result = (CountinCallsPersonService) componentRegistry.fetch(PersonService.class);

        assertThat(result.getSetterCalls()).isEqualTo(1);

    }

    private void testPersonService(PersonService result) {
        List<Person> persons = Arrays.asList(
                new Person("Pierre", "Martinet"),
                new Person("Odile", "Croq"));

        persons.forEach(result::save);

        assertThat(result.getMobSize()).isEqualTo(persons.size());
    }

}
