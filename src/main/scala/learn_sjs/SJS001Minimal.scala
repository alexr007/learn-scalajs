package learn_sjs

import org.scalajs.dom
import org.scalajs.dom.raw.Element
import org.scalajs.dom.{Node, console, document}

object SJS001Minimal {

  def main(args: Array[String]): Unit = {
    document.addEventListener("DOMContentLoaded", { (e: dom.Event) =>
      setupUI()
    })
    /**
     * because of
     * build.sbt: scalaJSUseMainModuleInitializer := true
     * this will be printed in the Browser JS Console
     */
    console.info("INFO: 8.Hello World!")
    console.debug("DEBUG: 8.Hello World!")
    println("println: 8.Hello World!")
  }

  // https://www.scala-js.org/doc/interoperability/export-to-javascript.html
//  @JSExportTopLevel("addClickedMessage")
  def addClickedMessage(): Unit = {
    append_p(document.body, "You clicked the button!")
  }

  // DOM manipulation
  def append_p(target: Node, what: String): Unit = {
    val el: Element = document.createElement("p")
    el.textContent = what
    target.appendChild(el)
  }

  def setupUI(): Unit = {
    append_p(document.body, "8. Hello World")

    val button = document.createElement("button")
    button.textContent = "Click me!"
    button.addEventListener("click", { (e: dom.MouseEvent) =>
      addClickedMessage()
    })
    // append button
    document.body.appendChild(button)
  }
}
