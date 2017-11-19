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

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class ActorController extends Controller {

	private final FormFactory formFactory;

    @Inject
    public ActorController(final FormFactory formFactory) {
        this.formFactory = formFactory;
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

	public static boolean isEmpty(CharSequence str) {
		if (str == null || str.length() == 0)
			return true;
		else
			return false;
	}

	/**
	 * Get list actors (search available)
	 * @return Result
	 */
	public Result dataList() {
		
        /**
         * Get params from datatables
         */
        Map<String, String[]> params = request().queryString();
		
		String limit = params.get("limit")[0];
		String order = params.get("order")[0];
		String search = "";
		
		if(params.get("search") != null){
			search = params.get("search")[0];
		}

		Integer offset = Integer.valueOf(params.get("offset")[0]);

		String sortBy = "first_name";

		// ============ ./START QUERY BUILDER
		Query<ActorEntity> queryBuilder = Ebean.find(ActorEntity.class);
		ExpressionList<ActorEntity> filterData = queryBuilder.where().conjunction();
		if (!isEmpty(search)) {
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
			.orderBy(sortBy + " " + order)
			.findList();
		// ============ ./END QUERY BUILDER
		
		ObjectNode result = Json.newObject();
		ArrayNode arrayNode = result.putArray("rows");

		SqlRow sqlRowCountAccount = Ebean.createSqlQuery("select count(*) as count from actor").findUnique();
        int iTotalRecords = sqlRowCountAccount.getInteger("count");
		result.put("total", iTotalRecords);

		for (ActorEntity accountListFilter : accountList) {
			ObjectNode row = Json.newObject();
			row.put("actor_id", accountListFilter.getActorId());
			row.put("first_name", accountListFilter.getFirstName());
			row.put("last_name", accountListFilter.getLastName());
			
            String action = "" +
                    "<a href=\"" + routes.ActorController.detail(accountListFilter.getActorId()) + "\" onclick=\"\"><i class=\"fa fa-search\" title=\"Detail\"></i></a>&nbsp;&nbsp;&nbsp;" +
                    "<a href=\"javascript:deleteData(" + accountListFilter.getActorId() + ");\"><i class=\"fa fa-trash\" title=\"Delete\"></i></a>&nbsp;";
			
			row.put("action", action);
			arrayNode.add(row);
		}
		return ok(result);
	}
	
	/**
	 * Get detail page
	 * @return Result
	 */
	public Result detail(long id) {
		ActorEntity actorEntity = ActorEntity.find.ref(id);
		Form<ActorEntity> formData = formFactory.form(ActorEntity.class).fill(actorEntity);
		return ok(views.html.account.detail.render(actorEntity));
	}

	/**
	 * Save Data
	 * @return Result
	 */
	@Transactional
	public Result save(long id) {
		
		ActorEntity actorEntity = null;
		
		if(id != 0){
			actorEntity = ActorEntity.find.byId(id);
		}else{
			actorEntity = new ActorEntity();
		}
		DynamicForm form = formFactory.form().bindFromRequest();
		String first_name = form.get("first_name").toString();
		String last_name = form.get("last_name").toString();
		
		actorEntity.setFirstName(first_name);
		actorEntity.setLastName(last_name);
		
		actorEntity.save();
		
		return GO_HOME;
	}

	/**
	 * Actor Add Actor
	 * @return Result
	 */
    public Result add() {
		ActorEntity actorEntity = new ActorEntity();
		return ok(views.html.account.detail.render(actorEntity));
    }
	
	/**
	 * Actor HOMEPAGE
	 * @return Result
	 */
    public static Result GO_HOME = redirect(
         routes.ActorController.index()
    );
}
