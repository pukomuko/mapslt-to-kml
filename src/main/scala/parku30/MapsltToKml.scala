package parku30


import java.nio.charset.Charset
import java.nio.file.{Files, Paths}

import parku30.kml.KmlDoc
import parku30.mapslt.StringUtil._
import parku30.mapslt.{MapsltMapSearchServer, MapsltTerritoryServer, MapsltToKmlConverter}

case class Config(teritorija: Option[String] = None,
                  savivaldybe: Option[String] = None,
                  kategorija: Option[String] = None,
                  saveAll: Boolean = false,
                  combine: Boolean = false)

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

      opt[Unit]('a', "all").action((_, c) =>
        c.copy(saveAll = true)).text("save all territories to kml files")

      opt[Unit]('c', "combine").action((_, c) =>
        c.copy(combine = true)).text("combine all territories into one kml file")

      checkConfig(c =>
        if ((c.teritorija.isEmpty && c.savivaldybe.isEmpty && c.kategorija.isEmpty) && !c.saveAll)
          failure("at least one search param must be set")
        else success)
    }

    parser.parse(args, Config()) match {
      case Some(config) =>
        if (!config.saveAll) {
          val response = MapsltMapSearchServer.query(saugomaTeritorija = config.teritorija,
            savivaldybe = config.savivaldybe,
            kategorija = config.kategorija)
          val kmlDoc = MapsltToKmlConverter.mapsltSearchResponseToKmlDoc(searchResponse = response)
          println(kmlDoc.toXmlString)
        } else {
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
        }

      case None =>

    }
  }

  def saveFile(name: String, content: String): Unit = {
    Files.write(Paths.get(name), content.getBytes(Charset.forName("UTF-8")))
  }

}

