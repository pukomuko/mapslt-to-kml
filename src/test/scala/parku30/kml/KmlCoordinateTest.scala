package parku30.kml

import org.scalatest.FlatSpec

class KmlCoordinateTest extends FlatSpec {

  "simple coordinate" should "convert to xml" in {

    val coord = KmlCoordinate("56.1234567", "24.12345")

    assert(coord.toKmlString() === "24.12345,56.1234567")
  }
}