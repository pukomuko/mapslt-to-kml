package parku30.mapslt

import org.scalatest.FlatSpec

class MapsltGeometryServerTest extends FlatSpec {

  "GeometryServer" should "construct correct request url" in {
    val geometry = MapsltGeometry(534555.6799999997, 6049202.1631000005)
    val requestUrl = MapsltGeometryServer.createRequestUrl(geometry)
    assert(requestUrl === "http://services.maps.lt/stmarsrutai/gisproxy.ashx/Geometry/GeometryServer/project?f=json&outSR=4326&inSR=2600&geometries=%7B%22geometryType%22%3A%22esriGeometryPoint%22%2C%22geometries%22%3A%5B%7B%22x%22%3A534555.6799999997%2C%22y%22%3A6049202.1631000005%2C%22spatialReference%22%3A%7B%22wkid%22%3A2600%7D%7D%5D%7D")
  }

  it should "connect and get valid WGS coordinate" in {
    val geometry = MapsltGeometry(534555.6799999997, 6049202.1631000005)
    val coord = MapsltGeometryServer.convertMapsltGeometryToCoordinate(geometry)

    assert(coord.lat === "54.578246538234772")
    assert(coord.lon === "24.53450075963422")
  }
}
