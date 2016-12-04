package parku30.mapslt

import org.scalatest.FlatSpec
import StringUtil._

class StringUtilTest extends FlatSpec {

  "StringUtil" should "sanitize lithuanian letters" in {

    assert("AnyksciuRP" === "Anykščių regioninis parkas".sanitize)
    assert("DieveniskiuIstorinisRP" === "Dieveniškių istorinis regioninis parkas".sanitize)
    assert("VandensUkiolaivuojamaAkvatorijosDalis" === "Vandens ūkio/laivuojama akvatorijos dalis".sanitize)
    assert("PlokstinesRZV" === "Plokštinės gamtinis rezervatas".sanitize)
    assert("SvenciuliskiuMiskoParkas" === "Švenčiuliškių miško parkas".sanitize)
    assert("KursiuNerijosNP" === "Kuršių nerijos nacionalinis parkas".sanitize)
  }

}
