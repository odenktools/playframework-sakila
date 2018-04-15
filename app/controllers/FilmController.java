package controllers;

import akka.stream.Materializer;
import akka.stream.impl.fusing.Log;
import akka.stream.javadsl.Source;
import akka.util.ByteString;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.ebean.*;
import models.FilmEntity;
import models.LanguageEntity;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import play.Logger;
import play.api.Configuration;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.db.ebean.Transactional;
import play.filters.csrf.AddCSRFToken;
import play.libs.Json;
import play.libs.ws.DefaultBodyReadables;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.shaded.ahc.io.netty.util.concurrent.Promise;
import utils.Helpers;
import utils.NakedSSLCerts;

import javax.inject.Inject;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.*;
import java.util.*;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static org.apache.http.HttpHeaders.TIMEOUT;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class FilmController extends Controller implements DefaultBodyReadables {

    private final FormFactory formFactory;

    private WSClient wsClient;
    //private Materializer materializer;
    Configuration configuration;

    private static final Logger.ALogger logger = Logger.of(FilmController.class);

    @Inject
    public FilmController(final FormFactory formFactory, final WSClient ws, final Configuration configuration) {
        this.formFactory = formFactory;
        this.wsClient = ws;
        this.configuration = configuration;
        logger.debug("FilmController", "formFactory");
    }

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        List<FilmEntity> listFilter = FilmEntity.finder
                .query()
                .where()
                .findList();
        return ok(views.html.film.list.render(listFilter));
    }

    /**
     * Get Language
     *
     * @return Map<Long,String> Map
     */
    private Map<Long, String> getListLanguage() {
        Map<Long, String> result = new LinkedHashMap<>();
        LanguageEntity.finder.query().where()
                .findList().forEach(
                dt -> result.put(dt.getLanguageId(), dt.getName()));
        return result;
    }

    private List<String> getListRating() {
        List<String> result = new ArrayList<>();
        result.add("G");
        result.add("PG");
        result.add("PG-13");
        result.add("R");
        result.add("NC-17");
        return result;
    }

    /**
     * Get list actors from API (search available)
     *
     * @return CompletionStage<Result>
     */
    public CompletionStage<Result> dataListApi() {
        // Get params from datatables
        Map<String, String[]> params = request().queryString();

        String limit = params.get("limit")[0];
        String order = params.get("order")[0];
        String sortBy = params.get("sort")[0];
        String orderBy = params.get("order")[0];

        String search = "";

        if (params.get("search") != null) {
            search = params.get("search")[0];
        }

        Integer offset = Integer.valueOf(params.get("offset")[0]);

        final CompletionStage<WSResponse> responseThreePromise = wsClient
                .url("http://localhost:9000/api/v1/films")
                .addHeader("Content-Type", "application/json")
                .setContentType("application/json")
                .addQueryParameter("search", search)
                .addQueryParameter("sortBy", sortBy)
                .addQueryParameter("orderBy", orderBy)
                .addQueryParameter("limit", limit)
                .addQueryParameter("offset", "" + offset)
                .get();

        CompletionStage<Result> result;
        result = responseThreePromise.thenApply(response -> {
            Source<ByteString, ?> body = response.getBodyAsSource();
            if (response.getStatus() == 200) {
                logger.debug("DATA FROM {} {}", "API", response.getBody());
                String contentType =
                        Optional.ofNullable(response.getHeaders().get("Content-Type"))
                                .map(contentTypes -> contentTypes.get(0))
                                .orElse("application/json");
                return ok().chunked(body).as(contentType);
            } else {
                return new Result(Http.Status.BAD_GATEWAY);
            }
        });

        return result;
    }

    /*public CompletionStage<Result> index() {
        Form<CheckoutForm> checkoutForm = formFactory.form(CheckoutForm.class);
        CompletionStage<Cart> cartFuture = CompletableFuture.supplyAsync(() -> cartService.getCartForUser(), ec.current());
        return cartFuture.thenApply(cart -> ok(index.render(cart, checkoutForm)));
    }*/

    /**
     * Implement REST API
     *
     * @param requestBody
     * @return String
     */
    public CompletionStage<Result> basePost(String requestBody) {
        final CompletionStage<WSResponse> responseThreePromise = wsClient
                .url("https://api.stagingmasskredit.com/master/education")
                .addHeader("api_key", "Ys9yN3fzSOB71UKf353ad839zuYMqLi4")
                .setContentType("application/json")
                .get();
        return responseThreePromise.thenApply(response -> {
            Source<ByteString, ?> body = response.getBodyAsSource();
            if (response.getStatus() == 200) {
                logger.debug("DATA FROM {} {}", "API", response.getBody());
                Logger.debug("dddddd {}", response.getHeaders().get("Content-Type"));
                String contentType =
                        Optional.ofNullable(response.getHeaders().get("Content-Type"))
                                .map(contentTypes -> contentTypes.get(0))
                                .orElse("application/json");
                return ok().chunked(body).as(contentType);
            } else {
                return new Result(response.getStatus());
            }
        });
    }

    /**
     * Get list actors (search available).
     *
     * @return Result
     */
    public Result dataList() {

        MDC.put("method", "dataList");

        /**
         * Get params from datatables
         */
        Map<String, String[]> params = request().queryString();

        String limit = params.get("limit")[0];
        String order = params.get("order")[0];
        String sortBy = params.get("sort")[0];
        String orderBy = params.get("order")[0];

        String search = "";

        if (params.get("search") != null) {
            search = params.get("search")[0];
        }
        CompletionStage<Result> basePost = this.basePost("");

        CompletionStage<Result> promiseOfResult = basePost.thenApplyAsync(pi ->
                ok("PI value computed: " + pi)
        );

        this.basePost("").thenApplyAsync(answer -> {

            Source<ByteString, ?> body = answer.body().dataStream();

            // uses Http.Context
            Logger.debug("SSSSSSS {}", body);
            ctx().flash().put("info", "Response updated!");
            return ok("answer was " + answer);
        });

        /*basePost.whenComplete(new BiConsumer<Result, Throwable>() {
            @Override
            public void accept(Result result, Throwable throwable) {
                //Source<ByteString, ?> body = result.body().as("application/json");
                Logger.debug("SSSSSSS {}", result.body().as("application/json").toString());
            }
        });*/


        Integer offset = Integer.valueOf(params.get("offset")[0]);

        // ============ ./START QUERY BUILDER
        Query<FilmEntity> queryBuilder = Ebean.find(FilmEntity.class);
        ExpressionList<FilmEntity> filterData = queryBuilder.where().conjunction();
        if (StringUtils.isNotBlank(search)) {
            filterData.add(
                    Expr.ilike("title", "%" + search + "%")
            );
        }
        filterData.endJunction();
        queryBuilder = filterData.query();
        List<FilmEntity> accountList = queryBuilder
                .setMaxRows(Integer.parseInt(limit))
                .setFirstRow(offset)
                .orderBy(sortBy + " " + orderBy)
                .findList();

        //-- Counting Row
        int rowCount = queryBuilder.findCount();
        // ============ ./END QUERY BUILDER

        ObjectNode nodeResult = Json.newObject();
        ArrayNode arrayNode = nodeResult.putArray("rows");

        nodeResult.put("total", rowCount);

        for (FilmEntity listFilter : accountList) {
            ObjectNode row = Json.newObject();
            row.put("film_id", listFilter.getFilmId());
            row.put("title", listFilter.getTitle());
            row.put("rating", listFilter.getRating());
            row.put("release_year", listFilter.getReleaseYear());
            row.put("created_at", utils.DateUtils.date2Str(listFilter.getCreatedAt(), "dd-MM-yyyy HH:mm:ss"));
            row.put("updated_at", utils.DateUtils.date2Str(listFilter.getUpdatedAt(), "dd-MM-yyyy HH:mm:ss"));

            String action = "" +
                    "<a data-toggle='tooltip' title='Detail an film' href=\"" + routes.FilmController.detail(listFilter.getFilmId()) + "\" onclick=\"\"><i class=\"fa fa-pencil\" title=\"Detail\"></i></a>&nbsp;&nbsp;&nbsp;" +
                    "<a data-toggle='tooltip' title='Delete an film' href=\"javascript:dialogRemove('" + routes.FilmController.remove(listFilter.getFilmId()) + "');\"><i class=\"fa fa-trash\" title=\"Delete\"></i></a>&nbsp;";

            row.put("action", action);
            arrayNode.add(row);
        }

        return ok(nodeResult);
    }

    /**
     * Get detail page.
     *
     * @return Result
     */
    public Result detail(long id) {
        Map<Long, String> listLanguage = getListLanguage();
        FilmEntity entity = FilmEntity.finder.ref(id);

        List<Long> selectedOriginLang = new ArrayList<>();

        List<String> listRating = getListRating();

        List<String> seletedRating = new ArrayList<>();

        seletedRating.addAll(listRating);

        LanguageEntity.finder.query().where()
                .eq("language_id", entity.getOriginalLanguageId())
                .findList().forEach(dt -> {
            selectedOriginLang.add(dt.getLanguageId());
        });

        logger.debug("get ID: {} NAME : {} CREATE_AT: {}", entity.getFilmId(), entity.getTitle(), entity.getCreatedAt());
        Form<FilmEntity> formData = formFactory.form(FilmEntity.class).fill(entity);

        return ok(views.html.film.detail.render("Edit an Film", entity, listLanguage, listRating,
                selectedOriginLang, seletedRating));
    }

    /**
     * Remove An Actor.
     *
     * @return Result
     */
    @AddCSRFToken
    @Transactional
    public Result remove(final long id) {
        TxScope scope = TxScope.requiresNew().setIsolation(TxIsolation.DEFAULT);
        Boolean deleted = Ebean.execute(scope, () -> {
            FilmEntity entity = FilmEntity.finder.ref(id);
            return entity.delete();
        });
        if (deleted) {
            flash("success", "Data success deleted");
        } else {
            flash("error", "Data failed deleted");
        }
        logger.debug("remove", "Delete row " + id);
        return GO_HOME;
    }

    /**
     * Save Data
     *
     * @return Result
     */
    @Transactional
    public Result save(final long id) {

        FilmEntity entity;

        Boolean onError = false;

        if (id != 0) {
            entity = FilmEntity.finder.byId(id);
        } else {
            entity = new FilmEntity();
        }

        DynamicForm form = formFactory.form().bindFromRequest();
        String title = form.get("title");
        String rating = form.get("rating");
        String rental_duration = form.get("rental_duration");
        String rental_rate = form.get("rental_rate");
        String replacement_cost = form.get("replacement_cost");
        Long language = Long.parseLong(form.get("language_id"));
        Long originLanguage = Long.parseLong(form.get("original_language_id"));

        java.sql.Date releaseYear = utils.DateUtils.convertSqlToDate(form.get("release_year"), "yyyy");

        if (form.hasErrors()) {
            onError = true;
        }
        if (Helpers.isEmpty(title)) {
            flash("error", "Please fill ``title``");
            onError = true;
        } else if (Helpers.isEmpty(rating)) {
            flash("error", "Please fill ``rating``");
            onError = true;
        } else if (Helpers.isEmpty(rental_duration)) {
            flash("error", "Please fill ``rental_duration``");
            onError = true;
        } else if (Helpers.isEmpty(rental_rate)) {
            flash("error", "Please fill ``rental_rate``");
            onError = true;
        } else if (Helpers.isEmpty(replacement_cost)) {
            flash("error", "Please fill ``replacement_cost``");
            onError = true;
        }
        //valid data only
        if (!onError) {
            assert entity != null;
            entity.setTitle(title);
            entity.setRating(rating);

            LanguageEntity lang = LanguageEntity.finder.query().where()
                    .eq("language_id", language)
                    .findUnique();

            entity.setLanguageId(lang);
            entity.setOriginalLanguageId(originLanguage);
            entity.setRentalDuration(Byte.parseByte(rental_duration));
            entity.setRentalRate(Double.parseDouble(rental_rate));

            Locale in_ID = new Locale("in", "ID");
            DecimalFormat nf = (DecimalFormat) NumberFormat.getInstance(in_ID);
            nf.setParseBigDecimal(true);
            BigDecimal bd = (BigDecimal) nf.parse(replacement_cost, new ParsePosition(0));
            entity.setReplacementCost(bd);

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                assert releaseYear != null;
                Date date = format.parse(releaseYear.toString());
                SimpleDateFormat df = new SimpleDateFormat("yyyy");
                String year = df.format(date);
                entity.setReleaseYear(Long.parseLong(year));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //-- SAVE RECORDS
            entity.save();
            flash("success", "Data success saved");
        } else {
            return redirect(routes.FilmController.add());
        }
        return GO_HOME;
    }

    /**
     * Actor Add Actor
     *
     * @return Result
     */
    public Result add() {
        FilmEntity entity = new FilmEntity();

        //-- Create Empty List
        List<Long> selectedOriginLang = new ArrayList<>();
        List<String> seletedRating = new ArrayList<>();

        Map<Long, String> listLanguage = getListLanguage();
        List<String> listRating = getListRating();
        return ok(views.html.film.detail.render("Add an Film", entity, listLanguage, listRating,
                selectedOriginLang, seletedRating));
    }

    /**
     * Actor HOMEPAGE
     */
    private static Result GO_HOME = redirect(
            routes.FilmController.index()
    );
}
