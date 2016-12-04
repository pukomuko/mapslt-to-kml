package parku30.kml

import scala.xml.{Elem, PrettyPrinter}

case class KmlCoordinate(lat: String, lon: String) {
  def toKmlString():String = { s"$lon,$lat"}
}

case class KmlPoint(coordinate: KmlCoordinate) {
  def toXml: Elem = <Point><coordinates>{coordinate.toKmlString()},0.0</coordinates></Point>
}

case class KmlPlacemark(name: String, description: String, point: KmlPoint) {
  def toXml: Elem = <Placemark><name>{name}</name><description>{scala.xml.PCData(description)}</description>{point.toXml}</Placemark>
}

case class KmlFolder(name: String, placemarks: Seq[KmlPlacemark]) {
  def toXml: Elem = <Folder>
    <name>{name}</name>
    {placemarks.map(_.toXml)}
  </Folder>
}

case class KmlDoc(name: String, description: String, folders: Seq[KmlFolder]) {

  def toXml: Elem =
    <kml xmlns='http://www.opengis.net/kml/2.2'>
      <Document>
        <name>{name}</name>
        <description>{scala.xml.PCData(description)}</description>
        {folders.map(_.toXml)}
      </Document>
    </kml>

  def toXmlString: String = {
    val pretty = new PrettyPrinter(120, 2)
    "<?xml version='1.0' encoding='UTF-8'?>\n" + pretty.format(toXml)
  }
}