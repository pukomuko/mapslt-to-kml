package parku30.mapslt

import java.net.URLEncoder

import parku30.mapslt.MapsltJsonProtocol._
import spray.json._

import scala.io.Source


object MapsltMapSearchServer {

  val MapsltMapSearchServerUrl = "http://services.maps.lt/stmarsrutai/gisproxy.ashx/stmarsrutai/MapServer/0/query?f=json&returnGeometry=true&spatialRel=esriSpatialRelIntersects&outFields=*&where="

  def query(saugomaTeritorija: Option[String] = None,
            savivaldybe: Option[String] = None,
            kategorija: Option[String] = None): MapsltSearchResponse = {
    val searchQuery = constructSearchQuery(saugomaTeritorija, savivaldybe, kategorija)
    val url = MapsltMapSearchServerUrl + URLEncoder.encode(searchQuery, "UTF-8")
    val jsonResponse = Source.fromURL(url).mkString
    jsonResponse.parseJson.convertTo[MapsltSearchResponse]


  }

  def constructSearchQuery(saugomaTeritorija: Option[String] = None,
                           savivaldybe: Option[String] = None,
                           kategorija: Option[String] = None): String = {

    val saugomaTeritorijaPredicate = saugomaTeritorija.map(s => s"SaugomaTeritorija = '$s'")
    val savivaldybePredicate = savivaldybe.map(s => s"Savivaldybe = '$s'")
    val kategorijaPredicate = kategorija.map(s => s"Kategorija = '$s'")

    val optionList = Seq(saugomaTeritorijaPredicate, savivaldybePredicate, kategorijaPredicate)
    optionList.flatten.mkString(" AND ")
  }
}
