package parku30


import java.nio.charset.Charset
import java.nio.file.{Files, Paths}

import parku30.kml.KmlDoc
import parku30.mapslt.StringUtil._
import parku30.mapslt.{MapsltClassifiers, MapsltMapSearchServer, MapsltTerritoryServer, MapsltToKmlConverter}

case class Config(teritorija: Option[String] = None,
                  savivaldybe: Option[String] = None,
                  kategorija: Option[String] = None,
                  allTerritories: Boolean = false,
                  combine: Boolean = false,
                  allSavivaldybes: Boolean = false) {

  def valid: Boolean = singleSearch || allTerritories || allSavivaldybes

  def singleSearch: Boolean = teritorija.isDefined || savivaldybe.isDefined || kategorija.isDefined
}

object MapsltToKml {

  def main(args: Array[String]): Unit = {

    val parser = new scopt.OptionParser[Config]("MapsltToKml") {

      override def showUsageOnError = true

      head("MapsltToKml", "0.1")
      opt[String]('t', "teritorija").action((x, c) =>
        c.copy(teritorija = Some(x))).text("SaugomaTeritorija")

      opt[String]('s', "savivaldybe").action((x, c) =>
        c.copy(savivaldybe = Some(x))).text("Savivaldybe")

      opt[String]('k', "kategorija").action((x, c) =>
        c.copy(kategorija = Some(x))).text("Kategorija")

      opt[Unit]('a', "all-territories").action((_, c) =>
        c.copy(allTerritories = true)).text("save all territories to kml files")

      opt[Unit]('c', "combine").action((_, c) =>
        c.copy(combine = true)).text("combine all territories into one kml file")

      opt[Unit]("all-savivaldybes").action((x, c) =>
        c.copy(allSavivaldybes = true)).text("save all savivaldybes to kml files")

      checkConfig(c =>
        if (!c.valid)
          failure("at least one search param must be set")
        else success)
    }

    parser.parse(args, Config()) match {
      case Some(config) =>
        if (config.singleSearch) {
          val response = MapsltMapSearchServer.query(saugomaTeritorija = config.teritorija,
            savivaldybe = config.savivaldybe,
            kategorija = config.kategorija)
          val kmlDoc = MapsltToKmlConverter.mapsltSearchResponseToKmlDoc(searchResponse = response)
          println(kmlDoc.toXmlString)
        } else if (config.allTerritories) {
          val territories = MapsltTerritoryServer.query()
          if (!config.combine) {
            territories.features.foreach { territory =>
              val response = MapsltMapSearchServer.query(saugomaTeritorija = Some(territory.attributes.ID))
              if (response.features.size > 99)
                Console.err.println(s"territory '${territory.attributes.PAVADINIM}' has more than 100 results: " + response.features.size)
              val kmlDoc = MapsltToKmlConverter.mapsltSearchResponseToKmlDoc(response, territory.attributes)
              println(territory.attributes.GKODAS + " " + territory.attributes.PAVADINIM + " - " + territory.attributes.PAVADINIM.sanitize)
              saveFile("kmls/" + territory.attributes.PAVADINIM.sanitize + ".kml", kmlDoc.toXmlString)
            }
          } else {
            val folders = territories.features.map { territory =>
              val response = MapsltMapSearchServer.query(saugomaTeritorija = Some(territory.attributes.ID))
              if (response.features.size > 99)
                Console.err.println(s"territory '${territory.attributes.PAVADINIM}' has more than 100 results: " + response.features.size)
              println(territory.attributes.GKODAS + " " + territory.attributes.PAVADINIM + " - " + territory.attributes.PAVADINIM.sanitize)
              MapsltToKmlConverter.mapsltSearchResponseToKmlFolder(response, territory.attributes.PAVADINIM.sanitize)
            }
            saveFile("kmls/allCombined.kml", KmlDoc("Saugomos Teritorijos", "", folders).toXmlString)
          }
        } else if (config.allSavivaldybes) {
          MapsltClassifiers.SavivaldybesMap.foreach { case (key: Int, value: String) =>
            println(key + " " + value + " - " + value.sanitize)
            val response = MapsltMapSearchServer.query(savivaldybe = Some(key.toString))
            if (response.features.size > 99)
              Console.err.println(s"savivaldybe '${value}' has more than 100 results: " + response.features.size)
            val kmlDoc = MapsltToKmlConverter.mapsltSearchResponseToKmlDoc(response, folderName = value, docName = value)
            saveFile("kmls/" + key + "-" + value.sanitize + ".kml", kmlDoc.toXmlString)

          }
        }

      case None =>

    }
  }

  def saveFile(name: String, content: String): Unit = {
    Files.write(Paths.get(name), content.getBytes(Charset.forName("UTF-8")))
  }

}

