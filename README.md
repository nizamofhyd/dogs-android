# dogs-android

Android app which lists the dog breeds which are retrieved via dogs open API (https://www.thedogapi.com/).
This app uses MVVM with clean architectural pattern separating UI, Domain and Data layers using kotlin coroutines and flows.
Gradle files are also maintained clean by reusing some common settings into common gradle files.
Used Hilt for dependency injection across the layers.

Branch:
Please checkout branch `develop` where all the changes are pushed to.

Unit tests using Mockk.io:
Includes test for view model, domain and data layers.

Functionality:
App home screen lists the dog breeds.
On selecting any specific dog breed from list , user is navigated to detail page showing more details about the dog breed.
List screen also provides option to search for a dog breed to find the new result set.
App consists of one activity and 2 compose screens i.e one for list view and other for detail view.
App supports android API level 28 and above only.
