package controllers.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.ebean.Ebean;
import io.ebean.Expr;
import io.ebean.ExpressionList;
import io.ebean.Query;
import io.swagger.annotations.*;
import models.FilmEntity;
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

/**
 * Rest Api Film.
 * Created by odenktools@gmail.com on 01/12/2017.
 */
@Api(value = "/api/films", description = "Films API")
public class ApiFilmController extends Controller {

    private final FormFactory formFactory;

    private static final Logger.ALogger logger = Logger.of(ApiFilmController.class);

    @Inject
    public ApiFilmController(final FormFactory formFactory) {
        this.formFactory = formFactory;
        logger.debug("FilmController", "formFactory");
    }

    /**
     * Get list actors (search available)
     *
     * @return play.mvc.Result
     */
    @ApiOperation(value = "dataList", notes = "Get Film List", response = FilmEntity.class, httpMethod = "GET")
    public Result dataList(String search, String sortBy, String orderBy, int offset, int limit) {

        // ============ ./START QUERY BUILDER
        Query<FilmEntity> queryBuilder = Ebean.find(FilmEntity.class);
        ExpressionList<FilmEntity> filterData = queryBuilder.where().conjunction();
        if (Helpers.isNotBlank(search)) {
            filterData.add(
                    Expr.ilike("title", "%" + search + "%")
            );
        }
        filterData.endJunction();
        queryBuilder = filterData.query();
        List<FilmEntity> accountList = queryBuilder
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

        for (FilmEntity data : accountList) {
            ObjectNode row = Json.newObject();
            row.put("film_id", data.getFilmId());
            row.put("title", data.getTitle());
            row.put("rating", data.getRating());
            row.put("release_year", data.getReleaseYear());
            row.put("rental_duration", data.getRentalDuration());
            row.put("rental_rate", data.getRentalRate());
            row.put("replacement_cost", data.getReplacementCost());
            row.put("created_at", utils.DateUtils.date2Str(data.getCreatedAt(), "dd-MM-yyyy HH:mm:ss"));
            row.put("updated_at", utils.DateUtils.date2Str(data.getUpdatedAt(), "dd-MM-yyyy HH:mm:ss"));

            arrayNode.add(row);
        }

        return ok(nodeResult);
    }

    /**
     * Add an actor
     *
     * @return play.mvc.Result
     */
    @ApiResponse(code = 200, message = "Success", response = FilmEntity.class, reference = "http://localhost:9000/assets/json/actor-entity.json")
    @ApiOperation(value = "submit", notes = "Add an Actor", response = FilmEntity.class, httpMethod = "POST")
    @BodyParser.Of(value = BodyParser.Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "body", required = true, paramType = "body", value = "ActorEntity")
    })
    public Result submit() {

        JsonNode node = request().body().asJson();

        if (node != null) {
            Form<FilmEntity> objFormRaw = formFactory.form(FilmEntity.class);
            Form<FilmEntity> objForm = objFormRaw.bind(node);
            if (objForm.hasErrors()) {
                return badRequest(objForm.errorsAsJson());
            }
            FilmEntity entity = objForm.get();
            entity.save();
        }
        //-- Put Result
        ObjectNode nodeResult = Json.newObject();
        nodeResult.put("result", "ok");
        return ok(Json.toJson(nodeResult));
    }
}
