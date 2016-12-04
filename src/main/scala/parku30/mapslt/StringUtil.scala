package parku30.mapslt

import java.text.Normalizer
import java.text.Normalizer.Form

object StringUtil {

  implicit class StringImprovements(val s: String) {
    implicit def sanitize:String = {
      def toCamel(s: String): String = {
        val split = s.split(" ")
        val tail = split.tail.map { x => x.head.toUpper + x.tail }
        split.head + tail.mkString
      }

      var sanitized = s.trim
      sanitized = sanitized.replace("regioninis parkas", "RP")
      sanitized = sanitized.replace("nacionalinis parkas", "NP")
      sanitized = sanitized.replace("gamtinis rezervatas", "RZV")
      sanitized = sanitized.replace(" r. sav.", "")
      sanitized = Normalizer.normalize(sanitized, Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
      sanitized = toCamel(sanitized)
      sanitized = sanitized.replaceAll("\\W", "")

      sanitized
    }
  }

}
