import org.scalajs.linker.interface.ModuleInitializer
//enablePlugins(ScalaJSPlugin, WorkbenchPlugin)
enablePlugins(ScalaJSPlugin)

name := "learn_sjs"
version := "0.1-SNAPSHOT"
scalaVersion := "2.13.1"

libraryDependencies ++= Seq(
  // http://scala-js.github.io/scala-js-dom/
  // https://mvnrepository.com/artifact/org.scala-js/scalajs-dom
  // %%% - for cross builds
  "org.scala-js" %%% "scalajs-dom"              % "1.0.0",

// https://www.lihaoyi.com/scalatags/
//   https://mvnrepository.com/artifact/com.lihaoyi/scalatags
  "com.lihaoyi" %%% "scalatags" % "0.8.6",
  "com.lihaoyi" %%% "utest" % "0.7.4" % "test",
)

testFrameworks += new TestFramework("utest.runner.Framework")

// this line will run main class automatically
//scalaJSUseMainModuleInitializer := true
val cname =
//  "SJS000Print"
//  "SJS001Minimal"
  "SJS002Canvas"
mainClass in Compile := Some(s"learn_sjs.$cname")
// rm -rf node_modules && npm i source-map-support jsdom
jsEnv := new org.scalajs.jsenv.jsdomnodejs.JSDOMNodeJSEnv()
