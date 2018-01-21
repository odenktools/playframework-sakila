package controllers;

import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.test.WithApplication;

import static org.junit.Assert.assertEquals;
import static play.test.Helpers.*;

public class ModelActorTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }

    @Test
    public void findActorById() {
        running(fakeApplication(inMemoryDatabase("test")), () -> {
            models.ActorEntity entity = models.ActorEntity.finder.byId(1L);
            assertEquals("PENELOPE", entity.getFirstName());
            assertEquals("GUINESS", entity.getLastName());
        });
    }
}
