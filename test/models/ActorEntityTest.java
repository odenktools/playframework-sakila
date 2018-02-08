package test;

import io.ebean.Ebean;
import io.ebean.TxIsolation;
import io.ebean.TxScope;
import models.ActorEntity;
import models.FilmEntity;
import org.junit.Test;
import play.Application;
import play.db.ebean.Transactional;
import play.inject.guice.GuiceApplicationBuilder;
import play.test.WithApplication;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static play.test.Helpers.*;

public class ActorEntityTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }

    @Test
    public void testActorInsert() {
        running(fakeApplication(inMemoryDatabase("test")), () -> {
            models.ActorEntity entity = new models.ActorEntity();
            entity.setActorId(1L);
            entity.setFirstName("John");
            entity.setLastName("Doe");
            entity.save();
            assertEquals("John", entity.getFirstName());
        });
    }

    @Test
    public void findActorById() {
        running(fakeApplication(inMemoryDatabase("test")), () -> {
            models.ActorEntity entity = models.ActorEntity.finder.byId(1L);
            assertEquals("John", entity.getFirstName());
        });
    }

    private boolean deleteId() {
        TxScope scope = TxScope.requiresNew().setIsolation(TxIsolation.DEFAULT);
        Boolean deleted = Ebean.execute(scope, () -> {
            ActorEntity entity = ActorEntity.finder.ref(1L);
            return entity.delete();
        });
        return deleted;
    }

    @Test
    public void findActorByName() {
        running(fakeApplication(inMemoryDatabase("test")), () -> {
            int count =
                    io.ebean.Ebean.find(models.ActorEntity.class)
                            .where().eq("first_name", "John")
                            .findCount();
            assertEquals(0, count);
        });
    }

    @Test
    public void deleteActorByName() {
        running(fakeApplication(inMemoryDatabase("test")), () -> {
            boolean del = deleteId();
            assertEquals(true, del);
        });
    }
}
