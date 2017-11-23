# Java Playframework Hackathon

* We use playframework 2.6.x
* We use [AdminLTE](https://adminlte.io/)
* We use Ebean
* We use MySQL 5.x

### How to run

```bash
git clone https://github.com/odenktools/playframework-sakila.git

cd playframework-sakila/

cp conf/application.txt conf/application.conf

mysql -u root -p -e "CREATE database odenktools_play;"

sbt compile
```

After that run a projects with

```bash
sbt -jvm-debug 9898 "run 9000"
```

Now open your browser then navigate to ```http://localhost:9000```

### References

[What’s new in Play 2.6](https://www.playframework.com/documentation/2.6.x/Highlights26)

[Template](https://www.playframework.com/documentation/2.6.x/JavaTemplates)

[Ebean](https://www.playframework.com/documentation/2.6.x/JavaEbean)

[Form](https://www.playframework.com/documentation/2.6.x/JavaForms)

[Evolution On Production](https://stackoverflow.com/a/20840401)

[Bootstrap Table](http://bootstrap-table.wenzhixin.net.cn/)