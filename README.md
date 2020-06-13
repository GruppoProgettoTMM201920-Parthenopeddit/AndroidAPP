# Parthenopeddit

Parthenopeddit is a platform where students of Università degli Studi di Napoli Parthenope can meet.
This app uses Esse3 API (Esse3 is a hub from Cineca) so students can login with their credentials 
without requiring a registration.

## Getting Started

Just download this repository and the "RESTplusAPI" in order to run the server-side.

### Prerequisites

Installing the APK and executing the server will not be enough to login in Parthenopeddit.
First: you must be a Parthenope student.
Plus, you might be able to run a Docker container from the RESTPlusAPI Phyton project. Download is below.
So you need:

```
Android Studio (Gradle 4.x, Min API 26)
PyCharm
Docker
```

### Installing

If you need a step-by-step guide, here you go:

```
-Copy this repo in your Android Studio workspace
-Download the API repo from here
https://github.com/GruppoProgettoTMM201920-Parthenopeddit/RESTPlusAPI

```
After you downloaded Docker, open Windows PowerShell in the directory of RESTPLusAPI repo and give those commands:

```
docker-compose up -d --build
//WAIT 1 MIN
docker-compose exec web flask populatedb
```
Now you must see "restplusapi" container in docker. Be sure it's running.
Then open Parthenopeddit on your Android emulator and login with your Esse3 credentials.

## Built With

* [Material SearchBar](https://github.com/mancj/MaterialSearchBar) - The searchbar used
* [Maven](https://maven.apache.org/) - Dependency Management
* [ExpandableSwipeRecyclerView](https://github.com/hyunstyle/ExpandableSwipeRecyclerView) - Used to get a properly expandable listview with swipe feature
* [Glide](https://github.com/bumptech/glide/) - Image loading framework
* [Picsum Photos](https://github.com/DMarby/picsum-photos/) - USed to get random mock user images

## Authors

* **Francesco Bottino**  - [GitHub](https://github.com/FrancescoBottino)
* **Marco Sautto**  - [GitHub](https://github.com/MarcoSautto)

See also the list of [contributors](https://github.com/orgs/GruppoProgettoTMM201920-Parthenopeddit/people) who participated in this project.

## License

This project is licensed under the Licensed under the Apache License, Version 2.0.

## Acknowledgments

* Thanks to Prof. R. Montella for his lessons, his advices and his passion for computer science.

## Google Slides

[Meet Parthenopeddit](https://docs.google.com/presentation/d/1dtrFvwjBepCGeagHdioocrLYIhkDcRJfmuVpJrPYFAU/edit#slide=id.p)
