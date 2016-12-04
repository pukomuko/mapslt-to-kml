package parku30.mapslt

import spray.json.DefaultJsonProtocol

case class MapsltGeometry(x: BigDecimal, y: BigDecimal)

case class MapsltAttributes(Pavadinimas: String,
                            Kategorija: Int,
                            TrumpasAprasymas: String,
                            Aprasymas: String,
                            Nuoroda: String,
                            Adresas: String,
                            Miestas: String,
                            KaipAtvykti: String,
                            Savivaldybe: Int,
                            SaugomaTeritorija: Option[String],
                            GlobalID: String)

case class MapsltFeature(attributes: MapsltAttributes, geometry: MapsltGeometry)

case class MapsltSearchResponse(features: Seq[MapsltFeature])

case class MapsltGeometriesResponse(geometries: Seq[MapsltGeometry])

case class MapsltTerritory(ID: String, ST_ID: String, PAVADINIM: String, GKODAS: String, VIETA: String)

case class MapsltTerritoryFeature(attributes: MapsltTerritory)

case class MapsltTerritoryResponse(features: Seq[MapsltTerritoryFeature])

object MapsltJsonProtocol extends DefaultJsonProtocol {
  implicit val mapsltAttributes = jsonFormat11(MapsltAttributes)
  implicit val mapsltGeometry = jsonFormat2(MapsltGeometry)
  implicit val mapsltFeatureFormat = jsonFormat2(MapsltFeature)
  implicit val searchResponseFormat = jsonFormat1(MapsltSearchResponse)
  implicit val geometriesResponseFormat = jsonFormat1(MapsltGeometriesResponse)
  implicit val mapsltTerritoryFormat = jsonFormat5(MapsltTerritory)
  implicit val mapsltTerritoryFeatureFormat = jsonFormat1(MapsltTerritoryFeature)
  implicit val mapsltTerritoryResponseFormat = jsonFormat1(MapsltTerritoryResponse)
}
