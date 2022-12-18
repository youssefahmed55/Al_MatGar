# Al_MatGar

**This application is an E-commerce App, built to Improve marketing communication between Merchants And Customers.**

![ic_app_icon](https://user-images.githubusercontent.com/99625111/208273067-b25f52a8-9b09-4e27-992b-328ba1c901b4.png)

![AGPL License](https://img.shields.io/badge/AndroidStudio-blue.svg) 
![AGPL License](https://img.shields.io/badge/Kotlin-blue.svg) 
[![GPLv3 License](https://img.shields.io/badge/minSdk-21-green.svg)](https://opensource.org/licenses/)
[![GPLv3 License](https://img.shields.io/badge/targetSdk-32-yellow.svg)](https://opensource.org/licenses/)

## Features

- The Application supports English and Arabic languages
- The Project is written in Kotlin
- Using Mvi And Mvvm Architecture
- Using View Binding And Data Binding
- Using Firebase Services
- Improve marketing communication between Merchants And Customers


## Table of Contents

- Dependency used in project
- Screenshots explain of Al_MatGar
- View a video showing the app
- Download Apk And Contact


## Dependency used in project

- Navigation Component

```css
implementation 'androidx.navigation:navigation-fragment-ktx:2.5.2'
implementation 'androidx.navigation:navigation-ui-ktx:2.5.2'
```

- sdp

```css
implementation 'com.intuit.sdp:sdp-android:1.1.0'
```

- ssp

```css
implementation 'com.intuit.ssp:ssp-android:1.1.0'
```

- Glide

```css
implementation 'com.github.bumptech.glide:glide:4.11.0'
annotationProcessor 'com.github.bumptech.glide:compiler:4.11.0'
```

- Recycler

```css
implementation "androidx.recyclerview:recyclerview:1.1.0"
```

- Shimmer

```css
implementation 'com.facebook.shimmer:shimmer:0.5.0'
```

- swiperefreshlayout

```css
implementation 'androidx.swiperefreshlayout:swiperefreshlayout:1.1.0'
```

- Toasty

```css
implementation 'com.github.GrenderG:Toasty:1.5.2'
```

- slider view

```css
implementation 'com.github.smarteist:autoimageslider:1.4.0'
```

- FireBase

```css
implementation platform('com.google.firebase:firebase-bom:31.0.0')
implementation 'com.google.firebase:firebase-analytics-ktx'
```

- FireBase Auth

```css
implementation 'com.google.firebase:firebase-auth'
implementation 'com.google.firebase:firebase-auth-ktx:21.1.0'
```

- Google Play services Auth

```css
implementation 'com.google.android.gms:play-services-auth:20.3.0'
```

- Cloud Firestore

```css
implementation 'com.google.firebase:firebase-firestore-ktx'
```

- Cloud Storage

```css
implementation 'com.google.firebase:firebase-storage'
```

- FCM

```css
implementation 'com.google.firebase:firebase-messaging:23.1.1'
implementation 'com.google.firebase:firebase-core:21.1.1'
```

- ViewModel

```css
implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.0-rc02")
// LiveData
implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.0-rc02")
// Lifecycles only (without ViewModel or LiveData)
implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.0-rc02")
// Saved state module for ViewModel
implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.5.0-rc02")
```

- Coroutine

```css
implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2"
implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.1'
implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.2.1'
```

- Dagger Hilt

```css
implementation("com.google.dagger:hilt-android:2.38.1")
kapt("com.google.dagger:hilt-android-compiler:2.38.1")
```

- Room

```css
implementation("androidx.room:room-runtime:2.5.0-alpha02")
annotationProcessor("androidx.room:room-compiler:2.5.0-alpha02")
// To use Kotlin annotation processing tool (kapt)
kapt("androidx.room:room-compiler:2.5.0-alpha02")
// optional - Kotlin Extensions and Coroutines support for Room
implementation("androidx.room:room-ktx:2.5.0-alpha02")
```

- Retrofit

```css
implementation 'com.squareup.retrofit2:retrofit:2.9.0'
```

- GSON Converter

```css
implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
```


## Screenshots explain of Al_MatGar

- Splash Screen That User Can Sign In Or Explore App First (Sign In Anonymously)

![Splash](https://user-images.githubusercontent.com/99625111/208275905-ce10e121-0a8d-4c8a-85aa-c0a9b13a8c44.png)

- Sign In 

![Login](https://user-images.githubusercontent.com/99625111/208275927-ecdd1698-a7c2-4022-8037-59468751dc96.png)

- Sign Up 

![Sign Up](https://user-images.githubusercontent.com/99625111/208275934-adb41719-00df-4379-9867-378989a67648.png)

- Forget Password

![Forget Password](https://user-images.githubusercontent.com/99625111/208275941-43c2175f-6b09-46ca-8986-2a8be3dedeca.png)


**It Consists Of 3 Faces :**

- Customer/User 

- Merchant

- Admin

__________________
**Customer/User**

- Home 

![Home](https://user-images.githubusercontent.com/99625111/208276071-b67ad774-5f2a-45ff-825f-631e8a1ebc6d.png)

- Sub Category 

![Sub Category](https://user-images.githubusercontent.com/99625111/208276073-95de1247-ba34-47c7-b80d-0684b757a5ad.png)

- Product Details 

![Product Details](https://user-images.githubusercontent.com/99625111/208276077-72d2f223-fcef-47af-b9d0-f82918b50567.png)

- Explore

![Explore](https://user-images.githubusercontent.com/99625111/208276088-60d8ee90-627e-42c6-97c3-508660633173.png)

- Offer 

![Offer](https://user-images.githubusercontent.com/99625111/208276096-102dbef5-fc10-49ef-8067-68881e164b24.png)

- Account 

![Account](https://user-images.githubusercontent.com/99625111/208276100-eb1dd32d-4abc-4f4b-a75c-2c9ae91b67f6.png)

- Favorites

![Favorites](https://user-images.githubusercontent.com/99625111/208276104-a737c5de-c38d-4b24-98d1-279306280cef.png)

- Cart 

![Cart](https://user-images.githubusercontent.com/99625111/208276105-c926b1af-d862-443d-a21e-56ff3db8b17c.png)

- Ship To

![Ship To](https://user-images.githubusercontent.com/99625111/208276115-ffd8780a-804e-401b-9b27-382e0fb91e4c.png)

- Success Order

![Success Order](https://user-images.githubusercontent.com/99625111/208276125-447f1289-0a67-4d88-a2d9-65dce401dbe4.png)

- Orders

![Orders](https://user-images.githubusercontent.com/99625111/208276136-af7663ab-b6b8-48b8-89fb-d3ff0e63a668.png)

__________________
**Merchant**

- My Products

![My Products](https://user-images.githubusercontent.com/99625111/208276265-66a5654a-7761-4153-838e-71f016feb301.png)

- Add Product

![Add Product](https://user-images.githubusercontent.com/99625111/208276267-5af155d2-4ec7-4630-b306-63f088531b05.png)

- Orders

![Orders](https://user-images.githubusercontent.com/99625111/208276272-426374fa-c273-415d-a4f9-803972de1b4c.png)

- Notification 

![Notification](https://user-images.githubusercontent.com/99625111/208276281-9906246b-32a2-4419-aaa8-37652cf124ee.png)

__________________
**Admin**

- Users

![New User](https://user-images.githubusercontent.com/99625111/208276329-21707845-fe59-4d8c-a31f-3daae7772ae7.png)

- New User

![New User](https://user-images.githubusercontent.com/99625111/208276322-2288baa8-4017-4faf-8f63-27d320991f2f.png)



## View a video showing the app




## Download Apk And Contact

- Apk : 

- Contact : youssefahmed505505@gmail.com
