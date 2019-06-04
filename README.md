# GreenLend

Online půjčování položek.


Projekt se zabývá webovou aplikací pro systém sdíleného půjčování věcí.

Návod na spuštění aplikace lokálně:

*  Nainstalujte si kontejnerovací aplikaci [Docker](https://www.docker.com/get-started)
*  [Nejspíš není nutné] Spustěte v terminálu příkaz `docker login` (občas to některé balíčky vyžadují a občas ne)
*  Na rootu projektu spusťte v terminálu příkaz `docker-compose up`
*  Počkejte, než docker spustí aplikaci

Nyní máte spuštěný frontend na portu 3000 a backend na 8080 (na localhostu)

Pokud mezi spuštěními proběhly nějaké změny, které chcete, aby se projevily
ve vašem spuštěném prostředí, spustěte příkaz `docker-compose build` a až pak
příkaz `docker-compose up`.

V případě výpadku databáze si můžete připojit vlastní změnou paramtrů v backendu,
v application.properties (např. Heroku poskytuje databáze zdarma o určité kapacitě)
