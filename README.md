
# Aldi Assessment

This is an assessment for the Mobile App Developer position at Aldi Süd. The repository is public for now but will make it private should the Aldi team request me to do so.

A brief summary of the app:
- Load a list of the top 10 ranked coins from the [CoinCap REST API](https://docs.coincap.io/)
- Upon clicking on an list item, display more details about the Coin
- The data are to be refresh automatically every 60 seconds
- The app must follow the screen designs provided.

## Notes
- The coins are already sorted in ascending order according to [https://docs.coincap.io](https://docs.coincap.io/) and there was an option to send a parameter to the API to limit the results to 10. No sorting or truncation was needed from the app side.

- I added an "Empty view" for the list and details. Basically if no data is returned for the list/details, there will be a text and a "try again" button to fetch the data again.

- If calling the API fails (because of bad internet or API is down) and the list is already loaded (for example, the automatic refresh failed), the list won't disappear and the cached results will be displayed. The automatic refresh will run again in 1 minute.

- I added a swipe to refresh mechanism in the list view. Upon using it to refresh, the "loading" view is shown (to comply with the screen designs).

- I'm saving the scroll position in the list view after the automatic refresh is triggered.

- The SVGs from figma weren't working, as if the extracted svg was malformed. Since I refused to import them as pngs, I downloaded the biggest PNG size, imported them in Adobe Illustrator and used its image trace to generate an SVG. I admit it's not an elegant solution. In a real scenario, I would have asked the designer for help.

- If the icon is not present in figma, the "Blur On" icon from [Material Symbols & Icons](https://fonts.google.com/icons) is used as default.


## Tech stack

The task was done on Windows 11 version 23H2.

**IDE**: Android Studio Koala | 2024.1.1 Patch 2

**Libraries:**
- Kotlin
- Jetpack Compose
- Retrofit
- Coroutines
- JUnit5
- Mockito
- Hilt
- Gson
- Jetpack Navigation
- Compile SDK version 35
- Min SDK version 26


---

## Instructions to run

The following instructions were tested in a new environment (created a new user on my local machine and set up Android Studio as well as git).

Pre-requisites:
- **Android Studio**: Install Android Studio from [here](https://developer.android.com/studio)
- **Git:** Install Android Studio from [here](https://developer.android.com/studio)

  Confirm the installation was a success by running `git --version` in a command line or terminal

**Steps:**
- In an empty folder or desktop, open a command line/terminal. (On Windows, you can press `SHIFT` and `right click on the mouse` then select `Open in Terminal`.)
- Write `git clone https://github.com/abedit/AldiAssessment.git` in a command line. It will clone the repository in your directory. You should see a folder named "AldiAssessment".
- Launch Android Studio and select **Open**. Navigate to the "AldiAssessment" folder and select it.
- The Android Studio will make sure to download the SDK version 35 but in case it isn't, make sure to use the SDK helper tool in Android Studio to download the proper components. More info [here](https://developer.android.com/tools/releases/platforms)

  You can go to Tools > SDK Manager then click on "SDK Tools".
  
  If "35.0.0" is already checked then all is good.
  
  Otherwise, select "35.0.0" and hit "Ok". Follow the instructions to download the SDK version 35.

- Plug an Android device (mobile or tablet) or create an emulator using Android Studio's AVD Manager.
  If you're using a real device, make sure `USB Debugging` is enabled in the device's settings under "Developer Options".

- Use the "Run" button in Android Studio to run the project on the device.

**Unit Tests**

In Android Studio, you can navigate to the directory on the left under `app > kotlin` and right click on `com.abedit.aldiassessment (test)` then `Run 'Tests in com.abedit...'`

**Instrumented Tests**

In Android Studio, you can navigate to the directory on the left under `app > kotlin` and right click on `com.abedit.aldiassessment (androidTest)` then `Run 'Tests in com.abedit...'`
