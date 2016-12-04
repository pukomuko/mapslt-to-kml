package parku30.mapslt

import java.io.InputStream

import org.scalatest.FlatSpec

import scala.io.Source
import spray.json._
import MapsltJsonProtocol._

class MapsltJsonParserTest extends FlatSpec {


  def readFile(name: String): String = {
    val stream: InputStream = getClass.getResourceAsStream(name)
    Source.fromInputStream(stream).getLines().mkString
  }


  "json parser" should "read mapslt search response" in {
    val jsonString = readFile("/mapslt-search-response.json")
    assert(jsonString.contains("SaugomaTeritorija"))

    val response = jsonString.parseJson.convertTo[MapsltSearchResponse]

    assert(response.features.nonEmpty)

    val firstPlace = response.features.head
    assert(firstPlace.attributes.Pavadinimas === "Velnio duobė")

    assert(firstPlace.geometry.x.toString() === "533234.28969999962")
    assert(firstPlace.geometry.y.toString() === "6052924.5033")

  }

}


