package parku30.kml

import org.scalatest._

class KmlWriterTest extends FlatSpec {

  val defaultCoordinate = KmlCoordinate("11.22", "33.44")
  val defaultPoint = KmlPoint(defaultCoordinate)
  val defaultDesc = "<b>description<b>"

  def placemarkString(name: String) = s"<Placemark><name>$name</name><description><![CDATA[<b>description<b>]]></description><Point><coordinates>33.44,11.22,0.0</coordinates></Point></Placemark>"

  "simple xml generator" should "produce xml" in {
    val portfolio = <product>xml</product>
    assert(portfolio.toString === "<product>xml</product>")
  }

  "coordinate" should "convert to point" in {
    val coord = KmlCoordinate("11.22", "33.44")
    val point = KmlPoint(coord)
    assert(point.toXml.toString() === "<Point><coordinates>33.44,11.22,0.0</coordinates></Point>")
  }

  "placemark" should "convert to valid xml with CDATA" in {
    val placemark = KmlPlacemark("name", defaultDesc, defaultPoint)
    assert(placemark.toXml.toString() === placemarkString("name"))
  }

  "folder" should "convert to valid xml" in {
    val kmlFolder = KmlFolder("folderis", Seq(KmlPlacemark("name", defaultDesc, defaultPoint), KmlPlacemark("name2", defaultDesc, defaultPoint)))
    val folderString = kmlFolder.toXml.toString()
    assert(folderString.contains(placemarkString("name")))
    assert(folderString.contains(placemarkString("name2")))
  }

  "doc" should "convert to valid xml" in {
    val kmlFolder1 = KmlFolder("folderis1", Seq(KmlPlacemark("name", defaultDesc, defaultPoint), KmlPlacemark("name2", defaultDesc, defaultPoint)))
    val kmlFolder2 = KmlFolder("folderis2", Seq(KmlPlacemark("name", defaultDesc, defaultPoint), KmlPlacemark("name2", defaultDesc, defaultPoint)))
    val kmlDoc = KmlDoc("tytuvenai", "what is in this park", Seq(kmlFolder1, kmlFolder2))
    val kmlDocString = xml.Utility.trim(kmlDoc.toXml).toString()
    assert(kmlDocString.contains(placemarkString("name2")))
  }
}