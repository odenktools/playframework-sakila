package controllers;

import models.FilmEntity;
import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.test.WithApplication;

import static org.junit.Assert.assertEquals;
import static play.test.Helpers.*;

public class ModelFilmTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }

    @Test
    public void findFilmCountZero() {
        running(fakeApplication(inMemoryDatabase("test")), () -> {
            int count = models.FilmEntity.finder.query().findCount();
            assertEquals(0, count);
        });
    }
}
