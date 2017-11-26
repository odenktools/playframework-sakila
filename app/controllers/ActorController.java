package controllers;

import play.mvc.*;
import models.ActorEntity;
import java.util.*;
import io.ebean.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;
import com.fasterxml.jackson.databind.node.ArrayNode;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import javax.inject.*;
import play.db.ebean.Transactional;

import utils.Helpers;
import play.filters.csrf.*;
import play.Logger;
import play.Logger.ALogger;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import org.slf4j.MDC;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class ActorController extends Controller {

	private final FormFactory formFactory;

	private static final Logger.ALogger logger = Logger.of(ActorController.class);

    @Inject
    public ActorController(final FormFactory formFactory) {
        this.formFactory = formFactory;
		logger.debug("ActorController", "formFactory");
    }

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
		
        List<ActorEntity> listActor = ActorEntity.find
                .query()
				.where()
                .findList();

		/*//Convert To JSON
		JsonNode json = play.libs.Json.toJson(listActor);
		String stringJson = json.toString();*/

		/*//Using RAW SQL:
		Query<ActorEntity> query = Ebean.find(ActorEntity.class);
		String sqlString = "SELECT * FROM actor;";
        RawSql rawSqlFilter = RawSqlBuilder.parse(sqlString).create();
		query.setRawSql(rawSqlFilter);*/

        return ok(views.html.account.list.render(listActor));
    }

	/**
	 * Get list actors (search available)
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
		
		if(params.get("search") != null){
			search = params.get("search")[0];
		}

		Integer offset = Integer.valueOf(params.get("offset")[0]);

		// ============ ./START QUERY BUILDER
		Query<ActorEntity> queryBuilder = Ebean.find(ActorEntity.class);
		ExpressionList<ActorEntity> filterData = queryBuilder.where().conjunction();
		if (StringUtils.isNotBlank(search)) {
            filterData.or(
                    Expr.ilike("first_name", "%" + search + "%"),
                    Expr.ilike("last_name", "%" + search + "%")
            );
		}
		filterData.endJunction();
        queryBuilder = filterData.query();
		List<ActorEntity> accountList = queryBuilder
			.setMaxRows(Integer.parseInt(limit))
			.setFirstRow(offset)
			.orderBy(sortBy + " " + orderBy)
			.findList();
		//-- Counting Row
		io.ebean.PagedList queryCount = queryBuilder
			.setMaxRows(Integer.parseInt(limit))
			.setFirstRow(offset)
			.orderBy(sortBy + " " + orderBy)
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
			row.put("created_at", utils.DateUtils.date2Str( accountListFilter.getCreatedAt(), "dd-MM-yyyy HH:mm:ss" ));
			row.put("updated_at", utils.DateUtils.date2Str( accountListFilter.getUpdatedAt(), "dd-MM-yyyy HH:mm:ss" ));
			
            String action = "" +
                    "<a data-toggle='tooltip' title='Detail an actor' href=\"" + routes.ActorController.detail(accountListFilter.getActorId()) + "\" onclick=\"\"><i class=\"fa fa-pencil\" title=\"Detail\"></i></a>&nbsp;&nbsp;&nbsp;" +
                    "<a data-toggle='tooltip' title='Delete an actor' href=\"javascript:dialogRemove('" + routes.ActorController.remove(accountListFilter.getActorId()) + "');\"><i class=\"fa fa-trash\" title=\"Delete\"></i></a>&nbsp;";
			
			row.put("action", action);
			arrayNode.add(row);
		}

		return ok(nodeResult);
	}
	
	/**
	 * Get detail page
	 * @return Result
	 */
	public Result detail(long id) {
		ActorEntity actorEntity = ActorEntity.find.ref(id);
		Form<ActorEntity> formData = formFactory.form(ActorEntity.class).fill(actorEntity);
		return ok(views.html.account.detail.render("Edit an Actor", actorEntity));
	}

	/**
	 * Remove An Actor
	 *
	 * @return Result
	 */
	@AddCSRFToken
	@Transactional
	public Result remove(long id) {
		ActorEntity actorEntity = ActorEntity.find.ref(id);
		
        int status = 0;
		boolean deleted = actorEntity.delete();

		if(deleted){
			flash("success", "Data success deleted");
		}else{
			flash("error", "Data failed deleted");
		}

		logger.debug("remove", "Delete row " + id);
		
		return GO_HOME;
	}

	/**
	 * Save Data
	 * @return Result
	 */
	@Transactional
	public Result save(long id) {
		
		ActorEntity actorEntity = null;
		
		Boolean onError = false;

		if(id != 0){
			actorEntity = ActorEntity.find.byId(id);
		}else{
			actorEntity = new ActorEntity();
		}

		DynamicForm form = formFactory.form().bindFromRequest();
		String first_name = form.get("first_name").toString();
		String last_name = form.get("last_name").toString();
		
		if(Helpers.isEmpty(first_name)){
			flash("error", "Please fill ``first_name``");
			onError = true;
		}else if(Helpers.isEmpty(last_name)){
			flash("error", "Please fill ``last_name``");
			onError = true;
		}

		if (!onError) {
			actorEntity.setFirstName(first_name);
			actorEntity.setLastName(last_name);
			actorEntity.save();
			flash("success", "Data success saved");
		}else{
			return redirect(routes.ActorController.add());
		}

		/* //USING FORM MODELS
		Form<ActorEntity> form = this.formFactory.form(ActorEntity.class).bindFromRequest();
		
		if (form.hasErrors()) {
			flash("error", "Please correct errors bellow.");
			return redirect(routes.ActorController.add());
		}

		ActorEntity newComputerData = form.get();

		if(form.get().getActorId() != null){
			actorEntity = ActorEntity.find.byId(form.get().getActorId());
			
			actorEntity.setFirstName(newComputerData.getFirstName());
			actorEntity.setLastName(newComputerData.getLastName());
			actorEntity.update();
			
		}else{
			actorEntity = new ActorEntity();
			actorEntity.setFirstName(newComputerData.getFirstName());
			actorEntity.setLastName(newComputerData.getLastName());
			actorEntity.save();
		}		
		*/
		
		return GO_HOME;
	}

	/**
	 * Actor Add Actor
	 * @return Result
	 */
    public Result add() {
		ActorEntity actorEntity = new ActorEntity();
		return ok(views.html.account.detail.render("Add an Actor", actorEntity));
    }
	
	/**
	 * Actor HOMEPAGE
	 * @return Result
	 */
    public static Result GO_HOME = redirect(
         routes.ActorController.index()
    );
}
