resolvers ++= Seq(
  Resolver.sonatypeRepo("public"),
  Resolver.mavenLocal,
  Resolver.sonatypeRepo("snapshots"),
  Resolver.sonatypeRepo("releases")
)

// The Play plugin
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.6.7")

addSbtPlugin("com.typesafe.sbt" % "sbt-play-ebean" % "4.0.6")

addSbtPlugin("com.typesafe.sbt" % "sbt-play-enhancer" % "1.1.0")
