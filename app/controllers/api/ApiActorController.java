package controllers.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.ebean.Ebean;
import io.ebean.Expr;
import io.ebean.ExpressionList;
import io.ebean.Query;
import io.swagger.annotations.*;
import models.ActorEntity;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import utils.Helpers;

import javax.inject.Inject;
import java.util.List;
import play.filters.csrf.AddCSRFToken;
import play.filters.csrf.CSRF;

/**
 * Rest Api Actor.
 * Created by odenktools@gmail.com on 01/12/2017.
 */
@Api(value = "/api/actors", description = "Actor API")
public class ApiActorController extends Controller {

    private final FormFactory formFactory;

    private static final Logger.ALogger logger = Logger.of(ApiActorController.class);

    @Inject
    public ApiActorController(final FormFactory formFactory) {
        this.formFactory = formFactory;
        logger.debug("ActorController", "formFactory");
    }

    /**
     * Get list actors (search available)
     *
     * @return play.mvc.Result
     */
    @ApiOperation(value = "dataList", notes = "Get Actors List", response = ActorEntity.class, httpMethod = "GET")
    public Result dataList(String search, String sortBy, String orderBy, int offset, int limit) {

        // ============ ./START QUERY BUILDER
        Query<ActorEntity> queryBuilder = Ebean.find(ActorEntity.class);
        ExpressionList<ActorEntity> filterData = queryBuilder.where().conjunction();
        if (Helpers.isNotBlank(search)) {
            filterData.or(
                    Expr.ilike("first_name", "%" + search + "%"),
                    Expr.ilike("last_name", "%" + search + "%")
            );
        }
        filterData.endJunction();
        queryBuilder = filterData.query();
        List<ActorEntity> accountList = queryBuilder
                .setMaxRows(limit)
                .setFirstRow(offset)
                .orderBy(sortBy + " " + orderBy)
                .findList();

        //-- Counting Row
        io.ebean.PagedList queryCount = queryBuilder
                .setMaxRows(limit)
                .setFirstRow(offset)
                .findPagedList();

        int rowCount = queryCount.getTotalCount();
        // ============ ./END QUERY BUILDER

        ObjectNode nodeResult = Json.newObject();
        ArrayNode arrayNode = nodeResult.putArray("rows");

        nodeResult.put("total", rowCount);

        for (ActorEntity accountListFilter : accountList) {
            ObjectNode row = Json.newObject();
            row.put("actor_id", accountListFilter.getActorId());
            row.put("first_name", accountListFilter.getFirstName());
            row.put("last_name", accountListFilter.getLastName());
            row.put("created_at", utils.DateUtils.date2Str(accountListFilter.getCreatedAt(), "dd-MM-yyyy HH:mm:ss"));
            row.put("updated_at", utils.DateUtils.date2Str(accountListFilter.getUpdatedAt(), "dd-MM-yyyy HH:mm:ss"));
            arrayNode.add(row);
        }

        return ok(nodeResult);
    }

    /**
     * Add an actor
     *
     * @return play.mvc.Result
     */
    @ApiResponse(code = 200, message = "Success", response = ActorEntity.class, reference = "http://localhost:9000/assets/json/actor-entity.json")
    @ApiOperation(value = "submit", notes = "Add an Actor", response = ActorEntity.class, httpMethod = "POST")
    @BodyParser.Of(value = BodyParser.Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "body", required = true, paramType = "body", value = "ActorEntity")
    })
    public Result submit() {

        JsonNode node = request().body().asJson();

		/*String filePath = Play.application().path() + File.separator + "public" + File.separator + "json" + File.separator + "actor-entity.json";
        File folder = new File(filePath);*/

        if (node != null) {
            Form<ActorEntity> objFormRaw = formFactory.form(ActorEntity.class);
            Form<ActorEntity> objForm = objFormRaw.bind(node);
            if (objForm.hasErrors()) {
                return badRequest(objForm.errorsAsJson());
            }
            ActorEntity actorEntity = objForm.get();
            actorEntity.save();
        }

        //-- Put Result
        ObjectNode nodeResult = Json.newObject();
        nodeResult.put("result", "ok");
        return ok(Json.toJson(nodeResult));
    }
}
