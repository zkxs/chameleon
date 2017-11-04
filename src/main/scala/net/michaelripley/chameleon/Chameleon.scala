package net.michaelripley.chameleon

import java.util.Random

import org.scalajs.dom
import org.scalajs.dom.{document, html, window}

import scala.scalajs.js.annotation._

object Chameleon {

  val NUMBER_OF_TILES = 16
  val STRING_SALT = "谹樮泹띇᠋ᅱ饬\u05F5岧낕⌦㍈⧪ꣷ㇔ᨁ\u0AF7\u18AB힀箎"
  val INT_SALT = 1577031886
  val CHEATER_PREFIX = "You are a dirty cheater and "

  var lastChameleonPlayerIndex: Option[Int] = None

  def main(args: Array[String]): Unit = {

    // get elements once
    lazy val form = document.getElementById("form").asInstanceOf[html.Form]
    lazy val outputElement = document.getElementById("out").asInstanceOf[html.Paragraph]
    lazy val seedElement = document.getElementById("seed").asInstanceOf[html.Input]
    lazy val playerIndexElement = document.getElementById("player").asInstanceOf[html.Input]
    lazy val numberOfPlayersElement = document.getElementById("players").asInstanceOf[html.Input]

    // getters for form fields
    def playerIndex: Int = playerIndexElement.valueAsNumber - 1
    def numberOfPlayers: Int = numberOfPlayersElement.valueAsNumber
    def seed = seedElement.value

    // setters
    val setPlayerIndex = (playerIdx: Int) => playerIndexElement.value = (playerIdx + 1).toString
    val setOutput = (output: String) => outputElement.innerHTML = output

    // called when the number of players changes
    def numberOfPlayersChanged(): Unit = {
      if (playerIndex >= numberOfPlayers) {
        setPlayerIndex(numberOfPlayers - 1)
      }
      playerIndexElement.max = numberOfPlayers.toString
    }

    // wait for page load
    window.addEventListener("load", (_: dom.Event) => {
      numberOfPlayersChanged()

      // when number of players changes
      numberOfPlayersElement.addEventListener("change", (_: dom.Event) => numberOfPlayersChanged(), useCapture = false)

      // when form is submitted
      form.addEventListener("submit", (event: dom.Event) => {
        event.preventDefault()

        computeNumber(stringToSeed(seed), playerIndex, numberOfPlayers) match {
          case Some(tileIndex) => setOutput(s"Hello regular human. The tile number is ${tileIndex + 1}.")
          case None => setOutput("You are the chameleon… good luck…")
        }
      }, useCapture = false)

    }, useCapture = false)
  }

  def stringToSeed(string: String): Long = {
    (STRING_SALT + string).hashCode
  }

  def computeNumber(seed: Long, playerIndex: Int, numberOfPlayers: Int): Option[Int] = {

    /* Notes:
     *
     * Chameleon selection happens with the first random number from a seeded PRNG. The seed is a user-supplied string,
     * salted to remove any weird edge cases like "".
     *
     * For non-chameleons, a second number is generated and used as the tile index. Note that the tile index for a
     * particular seed is independent from the number of players.
     *
     * In the event that a chameleon fails to step forward, a "secret" cheat() function is exposed that can be called
     * to reveal their player number without the need to manually brute-force the seed.
     */

    val random = new Random(seed)

    // first, determine if we are the chameleon
    val chameleonNumber =(random.nextInt() + INT_SALT + playerIndex) % numberOfPlayers
    val isChameleon = chameleonNumber == 0

    // compute the index of the actual chameleon player and save it for later
    lastChameleonPlayerIndex = Some(Math.floorMod(playerIndex - chameleonNumber, numberOfPlayers))

    if (isChameleon) {
      // we are the chameleon, so no number is needed
      None
    } else {
      // we are a normal player, so we need to know what tile to use
      Some(Math.floorMod(random.nextInt() + INT_SALT, NUMBER_OF_TILES))
    }
  }

  @JSExportTopLevel("cheat")
  def showLastChameleon(): Unit = {
    lastChameleonPlayerIndex match {
      case Some(idx) => println(CHEATER_PREFIX + s"the chameleon was player ${idx + 1}")
      case None => println(CHEATER_PREFIX + "you haven't even played a game yet")
    }
  }
}
