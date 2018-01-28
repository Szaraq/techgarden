# Zadanie 1
Rozwiązanie zadania 1 znajduje się w paczce trees. Klasa tree jest klasą nadrzędną, wobec ConiferTree (drzewo iglaste)
oraz LeafTree (drzewo liściaste). Implementacje tej klasy znajdują się w paczce trees.tree, składniki drzew zaś (gałęzie i pnie)
w paczce tree.components. Drzewo może mieć wiele gałęzi, ale tylko jeden pień (stąd lista gałęzi i pole pień). Większość
implementacji jest wspólna dla drzewa liściastego i iglastego, ale w celu ich rozróżnienia, zaimplementowano różne metody
rośnięcia (różny jest współczynnik wzrostu pnia). Pomimo toczących się obecnie sporów odnośnie zasadności stosowania dziedziczenia
w Javie i wyższości interfejsów nad klasami abstrakcyjnymi (vide:[link](https://www.javaworld.com/article/2073649/core-java/why-extends-is-evil.html)),
zdecydowano się w tym przypadku zastosować dziedziczenie. W tej konkretnej sytuacji dużo więcej zysku dostajemy z użycia wspólnej
implementacji różnych drzew, niż ponosimy kosztów opisanych w przytoczonym artykule.

# Zadanie 2
Rozwiązanie zadania 2 znajduje się w paczce payu. Mamy tutaj 2 calle do API PayU:
1. Po uzyskanie tokena - OAuthDao
2. Stworzenie zamówienia - OrderDao

Na nasze API "/api/order" oczekujemy JSONa ze szczegółami zamówienia, np.:
 ```json
[
        {
            "name": "Wireless mouse",
            "unitPrice": "15000",
            "quantity": "1"
        },
        {
            "name": "HDMI cable",
            "unitPrice": "6000",
            "quantity": "1"
        }
    ]
```
System najpierw uzyskuje token OAuth a następnie za jego pomocą przesyła zamówienie na API PayU. Wzamian dostaje z PayU URL
 do systemu płatności, który przekazuje do front-endu ze statusem 301 (przekierowanie). Zastosowano tutaj 3-warstwową strukturę:
 1. Controller z API
 2. Service rozdzielający żądanie do uzyskania tokena i złożenia zamówienia
 3. DAO zajmujące się poszczególnymi, pojedynczymi operacjami uzyskania tokena i złożenia zamówienia
 
 W celu poprawnego działania modułu należy podać odpowiednie dane obsługiwanego sklepu oraz URL serwisu płatności w pliku application.properties
 # Zadanie 3
 Rozwiązanie zadania 3 znajduje się w paczce navi. Poprawne działanie zakłada uruchomienie bazy mysql, stworzonej ze skryptu,
 znajdującego się w pliku techgarden.sql również załączonego do projektu. Mamy tu 2 tabele: location (przechowującą lokalizację)
 oraz device (urządzenia wysyłające lokalizację). Klasy, reprezentujące te tabele znajdują się w paczce navi.model.db.
 Mimo istnienia klucza obcego w tabeli location do tabeli device, w klasie Location, pole device jest typu long. Jest to działanie
  celowe, związane z wymaganiami bardzo wysokiej wydajności bazy. Gdyby to pole miało typ Device, przy każdym odczycie byłaby
  wykonywana operacja Join, rzadko wykorzystywana przez systemy odpytujące, spowalniająca za to działanie programu.\
  API zapisujące dane lokalizacyjne oczekuje JSONa takiego jak:
  ```json
{
      "device": 1,
      "longitude": -12.34,
      "latitude": 56.78
}
```
Data zapisywana jest automatycznie aktualna. Długość i szerokośc geograficzna powinna być wyrażona w wartościach z ułamkami,
tak jak ma to miejsce w mapach Google. Kierunek (wschód/zachód lub północ/południe) wyznacza znak tej wartości: +/-.\
W rozwiązaniu zastosowano bazę mysql, gdyż jest dużo łatwiejsza w utrzymaniu (Spring posiada własną implementację JPA dla tej bazy).
Jednak jeżeli w testach wydajnościowych okazałoby się, że potrzebna jest wydajniejsza baza, można zastosować Elasticsearch
(niestety w tym przypadku trzeba by skorzystać z bibliotek Elasticsearcha dla Spring, zamiast czystego JPA).