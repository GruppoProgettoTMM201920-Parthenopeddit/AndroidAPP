# Parthenopeddit

![alt text](https://github.com/GruppoProgettoTMM201920-Parthenopeddit/AndroidAPP/blob/master/app/src/main/res/drawable/parthenopeddit_logo_transparent.png?raw=true | width=48)

Parthenopeddit is a platform where students of Università degli Studi di Napoli Parthenope can meet.
This app uses Esse3 API (Esse3 is a hub from Cineca) so students can login with their credentials 
without requiring a registration.

## Getting Started

* Have a running instance of [Parthenopeddit API](https://github.com/GruppoProgettoTMM201920-Parthenopeddit/RESTPlusAPI)
* Clone or download [this repository](https://github.com/GruppoProgettoTMM201920-Parthenopeddit/AndroidAPP.git)
* On Android Studio, build apk and install on emulator / device of choice

If you're using the app on a physical device, or running the API on a different machine, you need to change the IP used by the app to send requests to that of the machine you are running the server on.  
This can be done in file ApiRoute.kt in package:
```
it.uniparthenope.parthenopeddit.apireturn "http:///10.0.2.2:8000"
```
on line 11: 
```
from 
      return "http:///10.0.2.2:8000"
to
      return "http:///YOUR_SERVER_IP:8000"
Example:
      return "http:///192.168.0.10:8000"
```

### Prerequisites
```
A server running the Parthenopeddit API
Android Studio (Gradle 4.x, Min API 26)
```
## Built With

* [Material SearchBar](https://github.com/mancj/MaterialSearchBar) - The searchbar used
* [Maven](https://maven.apache.org/) - Dependency Management
* [ExpandableSwipeRecyclerView](https://github.com/hyunstyle/ExpandableSwipeRecyclerView) - Used to get a properly expandable listview with swipe feature
* [Glide](https://github.com/bumptech/glide/) - Image loading framework
* [Picsum Photos](https://github.com/DMarby/picsum-photos/) - Used to get random mock user images

## Authors

* **Francesco Bottino**  - [GitHub](https://github.com/FrancescoBottino)
* **Marco Sautto**  - [GitHub](https://github.com/MarcoSautto)

See also the list of [contributors](https://github.com/orgs/GruppoProgettoTMM201920-Parthenopeddit/people) who participated in this project.

## License

This project is licensed under the Apache License, Version 2.0.

## Acknowledgments

* Thanks to Prof. R. Montella for his lessons, his advices and his passion for computer science.

## Google Slides

[Meet Parthenopeddit](https://docs.google.com/presentation/d/1dtrFvwjBepCGeagHdioocrLYIhkDcRJfmuVpJrPYFAU/edit#slide=id.p)
