# mapslt-to-kml
Download data from Maps.lt DB and convert it to KML.
All data belongs to Maps.lt, this tool is only for personal use.

## How to run

```sbt "run --all --combine"```

You will get "allCombined.kml" in kmls folder. If you want separate files for each territory use only "```--all```" option. You can use these KML in Google Maps, or you can convert it to GPX and use it in all kinds of apps. 


## Kam šito reikia?

[Maps.lt](http://maps.lt) turi skiltį [Keliaujantiems lėtai](http://services.maps.lt/stmarsrutai/), kurioje yra sukaupta daug inormacijos apie visokias lankytinas vietas Lietuvoje. Nors ta informacija labai įdomi, bet sistema turi du trūkumus:

  - labai nepatogu naudotis per telefoną.
  - vienu metu žemėlapyje matosi tik vienas taškas.
  
Supratau, kad prieš keliones daug laiko praleidžiu darydamas beždžionėlės darbą - kopijuoju taškus iš vienos programos į kitą. Dabar galėsiu žiūrėti į juos Google Maps programėlėje.

<img src="http://i.imgur.com/ZO3f3lj.png" width="500" />

## Kas toliau?

  - Visi taškai turi kategorijas, norėčiau, kad skirtingos kategorijos turėtų skirtingas ikonėles. 
  - Maps.lt aprašymuose yra nuotraukos. Manau, kad įmanoma po vieną nuotrauką ir į KML įtraukti.
  
## Turit klausimų/patarimų?
Visas bibliotekas panaudojau pirmąkart, tai kodas nėra gražus.

Juozas Šalna [juozas@salna.org](mailto:juozas@salna.org) 