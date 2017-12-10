# Java Playframework Hackathon

What's inside ?
---------------

* We use playframework 2.6.x
* We use Beautiful UI powered by [AdminLTE](https://adminlte.io/)
* We use Ebean
* We use MySQL 5.x
* [Integrated with Swagger and SwaggerUI](https://swagger.io/)
* [Bootstrap Table](http://bootstrap-table.wenzhixin.net.cn/)
* Rest API Examples
* CRUD Examples
* Server Side Validation
* Server Side Sorting and Filtering
* Cross Site Request Forgery aka CSRF Filter
* Cross-Origin Resource Sharing aka CORS Filter
* OneToMany Relation
* Play Evolution Examples

### How to run

```bash
git clone https://github.com/odenktools/playframework-sakila.git

cd playframework-sakila/

cp conf/application.txt conf/application.conf

mysql -u root -p -e "CREATE database odenktools_play;"

sbt compile
```

### Import Data

```bash
cd playframework-sakila/

mysql -uroot -P -f odenktools_play < conf/data.sql
```

After that run a projects with

```bash
sbt -jvm-debug 9898 "run 9000"
```

OR

```bash
sbt "run 9000"
```

Now open your browser then navigate to ```http://localhost:9000``` then ```apply``` the script

### Swagger UI

[SEE UI](http://localhost:9000/assets/lib/swagger-ui/index.html?/url=http://localhost:9000/api-docs)

### TESTING

List of actors
--------------

```bash
curl -X GET \
  'http://localhost:9000/api/v1/actors?offset=0&limit=10&sortBy=first_name&orderBy=asc' \
  -H 'cache-control: no-cache'
```

Find an actors
--------------

```bash
curl -X GET \
  'http://localhost:9000/api/v1/actors?offset=0&limit=10&sortBy=first_name&orderBy=asc&search=ADAM' \
  -H 'cache-control: no-cache'
```


List of films
--------------

```bash
curl -X GET \
  'http://localhost:9000/api/v1/films?offset=0&limit=10&sortBy=title&orderBy=asc' \
  -H 'cache-control: no-cache'
```

Find an films
--------------

```bash
curl -X GET \
  'http://localhost:9000/api/v1/films?search=ACADEMY%20DINOSAUR' \
  -H 'cache-control: no-cache'
```

### References

[What’s new in Play 2.6](https://www.playframework.com/documentation/2.6.x/Highlights26)

[Template](https://www.playframework.com/documentation/2.6.x/JavaTemplates)

[Ebean](https://www.playframework.com/documentation/2.6.x/JavaEbean)

[Form](https://www.playframework.com/documentation/2.6.x/JavaForms)

[Custom Logging](https://www.playframework.com/documentation/2.6.x/SettingsLogger)

[Bootstrap Table](http://bootstrap-table.wenzhixin.net.cn/)

[Evolution On Production](https://stackoverflow.com/a/20840401)
