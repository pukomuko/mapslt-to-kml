package parku30.mapslt

import parku30.kml._

object MapsltToKmlConverter {

  def mapsltSearchResponseToKmlDoc(searchResponse: MapsltSearchResponse, territory: MapsltTerritory): KmlDoc = {
    mapsltSearchResponseToKmlDoc(searchResponse, territory.PAVADINIM, territory.PAVADINIM, "maps.lt conversion")
  }

  def mapsltSearchResponseToKmlDoc(searchResponse: MapsltSearchResponse,
                                   folderName: String = "SearchResults",
                                   docName: String = "SearchResults",
                                   description: String = ""): KmlDoc = {
    KmlDoc(docName, folderName, Seq(mapsltSearchResponseToKmlFolder(searchResponse, folderName)))
  }

  def mapsltSearchResponseToKmlFolder(searchResponse: MapsltSearchResponse,
                                      folderName: String = "SearchResults"): KmlFolder = {
    val placemarks = searchResponse.features.map(mapsltFeatureToKmlPlacemark)
    KmlFolder(folderName, placemarks)
  }

  def mapsltFeatureToKmlPlacemark(feature: MapsltFeature): KmlPlacemark =
    KmlPlacemark(name = feature.attributes.Pavadinimas,
      description = mapsltAttributesToDescription(feature.attributes),
      point = KmlPoint(MapsltGeometryServer.convertMapsltGeometryToCoordinate(feature.geometry))
    )

  def mapsltAttributesToDescription(attributes: MapsltAttributes): String = {
    MapsltClassifiers.KategorijosMap(attributes.Kategorija) + "<br>\n" +
      s"<p>${attributes.TrumpasAprasymas} </p><br>\n" +
      ( if (!attributes.Adresas.trim.isEmpty) s"<p>${attributes.Adresas}, ${attributes.Miestas}</p><br>\n" else "") +
      ( if (!attributes.Aprasymas.trim.isEmpty) s"<p><b>Aprašymas:</b> ${attributes.Aprasymas} </p><br>\n" else "") +
      ( if (!attributes.KaipAtvykti.trim.isEmpty) s"<p><b>Kaip atvykti:</b> ${attributes.KaipAtvykti}</p><br>\n" else "")
  }
}
