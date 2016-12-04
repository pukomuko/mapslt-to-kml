package parku30.mapslt

import parku30.mapslt.MapsltJsonProtocol._
import spray.json._

import scala.io.Source

object MapsltTerritoryServer {

  val MapsltTerritoryServerUrl = "http://services.maps.lt/stmarsrutai/gisproxy.ashx/stmarsrutai/MapServer/2/query?f=json&where=1%3D1&returnGeometry=false&spatialRel=esriSpatialRelIntersects&outFields=*"

  def query(): MapsltTerritoryResponse = {
    val url = MapsltTerritoryServerUrl
    val jsonResponse = Source.fromURL(url).mkString
    jsonResponse.parseJson.convertTo[MapsltTerritoryResponse]
  }
}
