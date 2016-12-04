package parku30.mapslt

import org.scalatest.FlatSpec

class MapsltMapSearchServerTest extends FlatSpec {

  "MapSearchServer" should "construct empty search query if no params" in {
    assert("" === MapsltMapSearchServer.constructSearchQuery())
  }

  it should "construct SaugomaTeritorija query" in {
    assert("SaugomaTeritorija = '1'" === MapsltMapSearchServer.constructSearchQuery(saugomaTeritorija = Some("1")))
  }

  it should "construct full query for all params" in {
    val query = MapsltMapSearchServer.constructSearchQuery(saugomaTeritorija = Some("1"),
      savivaldybe = Some("2"),
      kategorija = Some("3"))

    assert("SaugomaTeritorija = '1' AND Savivaldybe = '2' AND Kategorija = '3'" == query)
  }

  it should "call Maps.lt and parse response" in {
    val result = MapsltMapSearchServer.query(saugomaTeritorija = Some("0700000000025"))
    val mapsltFeature = result.features.head

    assert("Velnio duobė" === mapsltFeature.attributes.Pavadinimas)
    assert(203 === mapsltFeature.attributes.Kategorija)
  }
}
