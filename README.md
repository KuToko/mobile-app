# MOBILE-DEVELOPMENT-PATH
This repository is for building the KuToko mobile app for mobile development path. The MD part is responsible to make the system work and can be use for user. The functional androiod application of KuToko will have 4 main navigation in the landing page of the application, which are Home, Search, Favorite, and Profile.
This app have feature to add location for user see the umkm in that specific location, this app for the user use will have a main feature, which is see the nearest UMKM store, see UMKM store based on user preferences, see UMKM related store.


# LOGO

![alt text](https://github.com/71200593dediyanto/AndroidStudio/blob/main/icon.png?raw=true)

# Depedencies

Here are all the depedencies we use in KuToko application

1. image
These are two libraries that we use as image loader
implementation 'de.hdodenhof:circleimageview:3.1.0'
implementation 'com.github.bumptech.glide:glide:4.12.0'

2. maps
this api are use to fetch user location and show the nearest umkm store to that location
implementation 'com.google.android.gms:play-services-location:19.0.1'
implementation 'com.google.android.gms:play-services-places:17.0.0'
implementation 'com.google.android.gms:play-services-maps:18.0.2'

4. ROOM database
the room database are use to store user login data and also store user location and user favorite store.
annotationProcessor 'androidx.room:room-compiler:2.4.2'
implementation 'androidx.room:room-ktx:2.4.2'
implementation 'androidx.room:room-runtime:2.4.2'
kapt 'androidx.room:room-compiler:2.4.2'

5. Retrofit
For retrive data from API server that CC team provided with their endpoints.
implementation 'com.squareup.retrofit2:retrofit:2.9.0'
implementation "com.squareup.retrofit2:converter-gson:2.9.0"
implementation "com.squareup.okhttp3:logging-interceptor:4.9.0"

6. viewmodel
to store live data in live data so we can observe it anytime the data go update
implementation "androidx.paging:paging-runtime-ktx:3.1.1"
implementation 'androidx.room:room-paging:2.5.1'

7. coroutines
to load data in asynchronous progress
implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.0'

8. paging
so we can use paging 3 to use pagination and load the data
implementation "androidx.paging:paging-runtime-ktx:3.1.1"
implementation 'androidx.room:room-paging:2.5.1'











