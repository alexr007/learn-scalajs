package learn_sjs

import utest._

import scala.scalajs.js
import org.scalajs.dom
import org.scalajs.dom.document
import org.scalajs.dom.ext._
import org.scalajs.dom.html.Button

object SJS001MinimalTest$ extends TestSuite {
  SJS001Minimal.setupUI()

    def tests: Tests = Tests {

    test {
      def messageCount: Int =
        document.querySelectorAll("p").count(_.textContent == "You clicked the button!")

      val button: Button = document.querySelector("button").asInstanceOf[dom.html.Button]

      assert(button != null && button.textContent == "Click me!")
      assert(messageCount == 0)

      for (c <- 1 to 5) {
        button.click()
        assert(messageCount == c)
      }
    }

    test {
      assert(1 == 1)
    }

  }
}
