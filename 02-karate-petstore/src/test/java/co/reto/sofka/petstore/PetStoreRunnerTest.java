package co.reto.sofka.petstore;

import com.intuit.karate.junit5.Karate;

class PetStoreRunnerTest {
    @Karate.Test
    Karate userCrudFlow() {
        return Karate.run("user-crud").relativeTo(getClass());
    }
}
