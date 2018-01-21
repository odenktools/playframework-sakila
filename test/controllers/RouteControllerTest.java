package controllers;

import org.junit.Test;
import play.Application;
import play.Logger;
import play.inject.guice.GuiceApplicationBuilder;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;
import play.test.TestServer;
import play.test.WSTestClient;
import play.test.WithApplication;

import java.util.concurrent.CompletionStage;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

public class RouteControllerTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }

    @Test
    public void testActorList() throws Exception {
        TestServer server = testServer(9000);
        running(server, () -> {
            try (WSClient ws = WSTestClient.newClient(9000)) {
                CompletionStage<WSResponse> completionStage = ws.url("/actors").get();
                WSResponse response = completionStage.toCompletableFuture().get();
                assertEquals(OK, response.getStatus());
            } catch (Exception e) {
                Logger.error(e.getMessage(), e);
            }
        });
    }

    @Test
    public void testFilmList() throws Exception {
        TestServer server = testServer(9000);
        running(server, () -> {
            try (WSClient ws = WSTestClient.newClient(9000)) {
                CompletionStage<WSResponse> completionStage = ws.url("/films").get();
                WSResponse response = completionStage.toCompletableFuture().get();
                assertEquals(OK, response.getStatus());
            } catch (Exception e) {
                Logger.error(e.getMessage(), e);
            }
        });
    }

    @Test
    public void testApiActorList() throws Exception {
        TestServer server = testServer(9000);
        running(server, () -> {
            try (WSClient ws = WSTestClient.newClient(9000)) {
                CompletionStage<WSResponse> completionStage = ws.url("/api/v1/actors").get();
                WSResponse response = completionStage.toCompletableFuture().get();
                assertEquals(OK, response.getStatus());
            } catch (Exception e) {
                Logger.error(e.getMessage(), e);
            }
        });
    }

}
