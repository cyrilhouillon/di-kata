package fr.ath.kata.di;

import fr.ath.kata.person.PersonService;
import fr.ath.kata.person.PersonServiceWithImplementationDependencie;
import org.junit.jupiter.api.Test;

import static fr.ath.kata.person.PersonServiceTest.testPersonService;
import static org.junit.jupiter.api.Assertions.*;

class BeansScannerTest {

    @Test
    void should_register_annotated_components_in_package() {
        BeansScanner scanner = new BeansScanner();

        scanner.scan("fr.ath.kata.person");

        PersonService result = scanner.getRegistry().fetch(PersonService.class);

        testPersonService(result);
    }

    @Test
    void should_register_annotated_components() {
        BeansScanner scanner = new BeansScanner();

        scanner.scan();

        PersonService result = scanner.getRegistry().fetch(PersonService.class);

        testPersonService(result);
    }
}
