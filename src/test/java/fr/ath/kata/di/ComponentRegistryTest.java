package fr.ath.kata.di;

import fr.ath.kata.person.PersonService;
import org.junit.jupiter.api.Test;

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
}
