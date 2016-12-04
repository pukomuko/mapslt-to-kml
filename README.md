# mapslt-to-kml
Download data from Maps.lt DB and convert it to KML.

## Kam šito reikia?

[Maps.lt](http://maps.lt) turi skiltį [Keliaujantiems lėtai](http://services.maps.lt/stmarsrutai/), kurioje yra sukaupta daug informacijos apie visokias lankytinas vietas Lietuvoje. Nors ta informacija įdomi ir naudinga, bet ja sunku naudotis:

  - per telefoną reikia atsidarinėti maps.lt desktop versiją, rezultatai netelpa ekrane.
  - vienu metu žemėlapyje matosi tik vienas taškas.
  
Supratau, kad prieš keliones daug laiko praleidžiu darydamas beždžionėlės darbą - kopijuoju taškus iš maps.lt paieškos į maps.me programėlę. Dabar galėsiu žiūrėti į juos Google Maps programėlėje.

<img src="http://i.imgur.com/ZO3f3lj.png" width="500" />

## Kaip naudotis?
Žinau, kad nevisi suinteresuoti turi savo kompiuteriuose Scala ir SBT, todėl sudėjau visus sugeneruotus failus į [GitHub](https://github.com/pukomuko/mapslt-to-kml/tree/master/kmls) - išsirenkat norimą KML failą, spaudžiat ant jo, o tada vidiniame lange renkatės opciją "Raw".

Ant savo kompiuterio KML failus galima žiūrėti su Google Earth. Bet daugiausia naudos gausite susimportavę juos į [Google My Maps](https://www.google.com/maps/d/), tada jie tampa prieinami per telefoną. 

Galite išsibandyti ant telefono šitą nuorodą - [Aukštadvario regioninio parko taškai](https://drive.google.com/open?id=1qGcvOFPYxNE8bwbxSxWSetLi_OE&usp=sharing).

| Bendras žemėlapis | Taško informacija |
|-------------------| ------------------|
| <img src="http://i.imgur.com/0GTgy8D.jpg" width="300" /> | <img src="http://i.imgur.com/YzdpbSq.jpg" width="300" /> |

## Kaip pasileisti?

```sbt "run --all-territories --combine"```

You will get "allCombined.kml" in kmls folder. If you want separate files for each territory use only "```--all-territories```" option. You can use these KML in Google Maps, or you can convert it to GPX and use it in all kinds of apps. 

## Kas toliau?

  - Visi taškai turi kategorijas, norėčiau, kad skirtingos kategorijos turėtų skirtingas ikonėles. 
  - Maps.lt aprašymuose yra nuotraukos. Manau, kad įmanoma po vieną nuotrauką ir į KML įtraukti.
  - Galbūt nereiktų generuoti tuščių failų :)

## I Am Not A Lawyer
Pati programa neturėtų kelti problemų, šiek tiek pilkesnėje zonoje yra sugeneruoti failai. Aš jų nenorėjau kelti į GitHub'ą, bet kelis supušinau netyčia, tai tada nutariau supušinti ir likusius. Teoriškai šitie duomenys yra parengti pagal [BEF Baltijos Aplinkos Forumas Lietuvoje](http://www.bef.lt/) programą už Lietuvos ir Europos pinigus. Jie šiuo metu pasiekiami nemokamai, aš tik padariau juos labiau prieinamus. Bet jei mane uždarys į cypę, prašau atneškit džiūvėsių.

## Turit klausimų/patarimų?
Visas bibliotekas panaudojau pirmąkart, tai kodas nėra gražus/optimalus.

Juozas Šalna [juozas@salna.org](mailto:juozas@salna.org) 