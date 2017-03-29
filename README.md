# Prowadź Patryk

Projekt we wstępnej fazie. Docelowo ma to być aplikacja służąca wspomaganiu w wyborze trasy przy użyciu komunikacji miejskiej (klon "JakDojade")

<p>
<a href="https://play.google.com/store/apps/details?id=pl.cyrzan.prowadzpatryk.internal&rdid=pl.cyrzan.prowadzpatryk.internal"><img src="https://github.com/andyb129/FlipsideCamera/blob/master/screenshots%2Fgoogle_play_badge.png" height="80" width="210" alt="Play Store Badge"/></a>
</p>

W związku z tym, że serwer nie działa jeszcze na zewnątrz odpowiedzi są zamockowane w wariancie "Internal". Defaultowa odpowiedź pojawia się na każde zapytanie w oknach wyszukiwania. Dodatkowa odpowiedź na zapytania "aaa" i "aaaa". Natomiast w wersji "Production" wszystko jest jak należy i można to sprawdzić uruchamiając serwer OpenTripPlanner.

# Przechowwaynie danych w aplikacji


  - Przechowywanie w SharedPreferences preferencji użytkownika dotyczących jakimi środkami transportu chce się poruszać
  - Przehcowywanie w lokalnej bazie ostatnich wyszukiwań i wyświetlanie ich jako podpowiedzi w oknie wyszkuiwania



# Specyfikacja / Biblioteki open-source:

- Minimum **SDK 16**,
- **MVP**-architektura
- **Lambda**
- [**Dagger**](https://github.com/google/dagger) DI
- [**RxJava**](https://github.com/ReactiveX/RxJava) & [**RxAndroid**](https://github.com/ReactiveX/RxAndroid) dla strumienii
- [**Retrofit**](https://github.com/square/retrofit) REST Api
- [**OkHTTP-json-mock**](https://github.com/mirrajabi/okhttp-json-mock) dla zamockowanych odpowiedzi
- [**ButterKnife**](https://github.com/JakeWharton/butterknife)
- [**Calligraphy**](https://github.com/chrisjenx/Calligraphy) dla customowych czcionek
- [**JodaTime**](https://github.com/JodaOrg/joda-time) dla zarządzania czasem
- [**SQLBrite**](https://github.com/square/sqlbrite) lekki wrapper do bazy SQLite
- **CrashLytics** raporty o błędach
- **Android Support Libraries**
