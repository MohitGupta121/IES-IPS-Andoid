# IES IPS CMS
Android application to manage student, teachers, HOD in college.

![Build](https://github.com/MohitGupta121/DiseaseDetection/workflows/Build/badge.svg?branch=main)

[![GitHub license](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/)
![Twitter Follow](https://img.shields.io/twitter/follow/Mohit_Gupta121?label=Follow&style=social)

**IES IPS CMS** is a campus manager ✅ Android application 📱 to help teachers to mange students in College built to demonstrate use of *Modern Android development* tools. Dedicated to all Android Developers and *Retrofit* with ❤️. 

<!-- ***You can Install and test latest IES IPS CMS app from below 👇***

[![IPS CMS App](https://img.shields.io/badge/IESIPSCMS✅-APK-red.svg?style=for-the-badge&logo=android)](https://github.com/hellosagar/AssigmentHub/releases/download/v1.2/app-release.apk) -->

## ⚙️ Features
* Login: Allow the student, teachers, hod to login in
* Attendance: Show, edit, take student attendance
* Marks: Add and Update studnet marks / Student view there marks.

## 🚀 Technology Used

* IES IPS Academy CMS built using Kotlin
* API fetch using Retrofit

## 📸 Screenshots

||||
|:----------------------------------------:|:-----------------------------------------:|:-----------------------------------------: |
| ![Imgur](https://user-images.githubusercontent.com/76530270/170965228-515111bc-e02c-49cf-878c-eed593ec8a85.png) | ![image](https://user-images.githubusercontent.com/76530270/170965417-b0a9e49a-a7bd-461a-bccd-5c73904f79ae.png) | ![image](https://user-images.githubusercontent.com/76530270/170965814-60209f61-6323-41d4-9978-70c52f788879.png) |
| ![image](https://user-images.githubusercontent.com/76530270/170965993-3942c71b-bd06-4307-acf5-56adbd4723b9.png) | ![image](https://user-images.githubusercontent.com/76530270/170966073-00ea3afa-f094-48a0-875c-264c83742c8c.png) | ![image](https://user-images.githubusercontent.com/76530270/170966221-b91ecc1f-0047-4c14-9b2f-fd92bb934b94.png)

## Built With 🛠
- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Data objects that notify views when the underlying database changes.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes. 
  - [ViewBinding](https://developer.android.com/topic/libraries/view-binding) - Generates a binding class for each XML layout file present in that module and allows you to more easily write code that interacts with views.
- [Dependency Injection](https://developer.android.com/training/dependency-injection) - 
  - [Hilt-Dagger](https://dagger.dev/hilt/) - Standard library to incorporate Dagger dependency injection into an Android application.
  - [Hilt-ViewModel](https://developer.android.com/training/dependency-injection/hilt-jetpack) - DI for injecting `ViewModel`.
- [Material Components for Android](https://github.com/material-components/material-components-android) - Modular and customizable Material Design UI components for Android.
- [Retrofit](https://square.github.io/retrofit/) - Retrofit is a type-safe REST client for Android, Java and Kotlin developed by Square. The library provides a powerful framework for authenticating and interacting with APIs and sending network requests with OkHttp.


# Package Structure
    
    cmsr.ipsacademy.net    # Root Package
    .
    ├── data                # For data handling.
    │   └── repository      # Single source of data.   
    |
    ├── di                  # Dependency Injection             
    |
    ├── ui                  # Activity/View layer
    │   ├── viewmodel       # ViewModels
    │   └── adapter         # Adpaters
    │   └── fragment        # Fragnents
    |
    └── utils               # Utility Classes / Kotlin extensions

       
## Architecture
This app uses [***MVVM (Model View View-Model)***](https://developer.android.com/jetpack/docs/guide#recommended-app-arch) architecture.

![architecture](https://developer.android.com/topic/libraries/architecture/images/final-architecture.png)

## Discuss 💬

Have any questions, doubts or want to present your opinions, views? You're always welcome. You can [start discussion](https://github.com/MohitGupta121/IES-IPS-Andoid/discussions).

## Contact
If you need any help, you can connect with me.

Mail: [mohitgupta](mailto:mohitgupta7780@gmail.com)

## License
```
MIT License

Copyright (c) 2022 Mohit Gupta

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
## To Contribute to this project follow the below steps: 

* ***Fork this Project.***
* **Clone this Project in your local:-** 
        
        git clone <Foked Repo URL>
        
* **Make your Separate Branch according to your task:** 

        git checkout -b "<Your Branch Name>"
        
* ***Great Now you are Ready to Contribute.***  


## How to Make Pull Request

* To add all changed files:-

        git add .

* To commit your Changes:-

        git commit -m "tag: commit message"
        
* **Note Before Push, Please Note that you Update your Local Branch with Upstream Branch**  
* Update your GitHub Repo using Fetch and Merge button on your repo with Development Branch.

        git pull

* To PUSH your Changes:-

        git push  
        
        
         
<h2>Contributors:</h2>

## Thanks to all the contributors ❤️

<table>
   <tr>
      <td>
         <a href = "https://github.com/MohitGupta121/IES-IPS-Andoid/graphs/contributors">
         <img src = "https://contrib.rocks/image?repo=MohitGupta121/IES-IPS-Andoid"/>
         </a>
      </td>
   </tr>
</table>
