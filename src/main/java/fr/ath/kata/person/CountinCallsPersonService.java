package fr.ath.kata.person;

public class CountinCallsPersonService extends PersonServiceWithInterfaceDependencie {

    int setterCalls = 0;

    @Override
    public void setPersonRepository(PersonRepository personRepository) {
        setterCalls++;
        super.setPersonRepository(personRepository);
    }

    public int getSetterCalls() {
        return setterCalls;
    }
}
