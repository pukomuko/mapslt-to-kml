package parku30.mapslt

import java.net.URLEncoder

import parku30.kml.KmlCoordinate
import parku30.mapslt.MapsltJsonProtocol._
import spray.json._

import scala.io.Source

object MapsltGeometryServer {

  val MapsltGeometryServerUrl = "http://services.maps.lt/stmarsrutai/gisproxy.ashx/Geometry/GeometryServer/project"

  def convertMapsltGeometryToCoordinate(geometry: MapsltGeometry): KmlCoordinate = {
    val jsonResponse = Source.fromURL(createRequestUrl(geometry)).mkString
    val response = jsonResponse.parseJson.convertTo[MapsltGeometriesResponse]
    val geometryResponse = response.geometries.head

    KmlCoordinate(geometryResponse.y.toString(), geometryResponse.x.toString())
  }

  def createRequestUrl(geometry: MapsltGeometry): String = {
    val geometryJson = "{\"geometryType\":\"esriGeometryPoint\",\"geometries\":[{\"x\":" + geometry.x + ",\"y\":" + geometry.y + ",\"spatialReference\":{\"wkid\":2600}}]}"
    MapsltGeometryServerUrl + "?f=json&outSR=4326&inSR=2600&geometries=" + URLEncoder.encode(geometryJson, "UTF-8")
  }

}
