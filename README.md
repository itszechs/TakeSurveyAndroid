# TakeSurveyAndroid
An simple survey app that allows you to create and share surveys publicly


## Preview
Create|Attempt|Review
:-----:|:-------------------------------:|:-----------:|
![Screenshot_1675603397](https://user-images.githubusercontent.com/52543663/216821768-153887bc-6901-4c6b-9ada-1d4ee1c45495.png)|![Screenshot_1675603497](https://user-images.githubusercontent.com/52543663/216821780-9dbfbd9a-0484-468c-9723-0af7e81d6589.png)|![Screenshot_1675603501](https://user-images.githubusercontent.com/52543663/216821783-27f0cebd-8269-4f9d-9e33-b7a06a8f8e92.png)


## Download

Go to the [Releases](https://github.com/itszechs/TakeSurveyAndroid/releases) to download the latest APK.

## Tech stack

- Minimum SDK level 24
- Kotlin
- Coroutines
- JetPack
    - Lifecycle - Dispose of observing data when lifecycle state changes.
    - ViewModel - UI related data holder, lifecycle aware.
    - ViewBinding - Generates a binding class for each XML layout file.
    - LiveData - Lifecycle-aware data holder class.
    - Navigation Component 
- Architecture
    - MVVM Architecture
    - Repository pattern
- Dependency Injection: Dagger Hilt
- Retrofit2 & OkHttp3 - Construct the REST APIs and paging network data.
- Moshi - A modern JSON library for Kotlin and Java.
- Material-Components - Material design components like ripple animation, cardView.

###### Also check out the [website](https://github.com/itszechs/TakeSurveyAndroid)
