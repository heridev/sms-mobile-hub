Android App for smsforall.org
=================================

## How to get started with smsforall.org
There are different ways to get started here:
1. Host your own server(Rails API) and Client Application(React) if you have technical knowledge in Web Development
2. Register an account in https://app.smsforall.org and get familiar with the system

About option 1, I'll be publishing a video soon about how to connect all the pieces in development, stay tuned for that!

## Do you want to build your own version of smsforall.org?
I'm planning to create some videos for that purpose, but in the meanwhile, 

## How to set up the project locally?
1. Clone the repo
2. Open Android Studio
3. Configure your Backend API according to your needs, in this https://github.com/heridev/sms-mobile-hub/blob/main/gradle.properties#L24 file
4. Create a new Firebase project in the console and after that create a new SDK app, by following some steps like this
![image](https://github.com/heridev/sms-mobile-hub/assets/1863670/3eeeaf20-f946-4d77-bf2c-9d5240c5efb1)
![image](https://github.com/heridev/sms-mobile-hub/assets/1863670/327bda05-4df7-430d-a3ec-f3623fd3fe5a)
![image](https://github.com/heridev/sms-mobile-hub/assets/1863670/327cd317-6f0e-4954-96cf-22caef8bff01)
5. Then Download your Firebase credentials(google-services.json) and place them within the `app` folder and the file will look like this `app/google-services.json`
6. Rebuild the project without errors(sync project option)
![image](https://github.com/heridev/sms-mobile-hub/assets/1863670/f41327f0-4521-4b5c-8213-f7914cc2549f)
7. Generate the APK selecting the variant(prodDebug)
![image](https://github.com/heridev/sms-mobile-hub/assets/1863670/dd9240f3-bc4c-4271-aa9f-099aab097321)
8. Upload it into a public hosting provider such as Google Drive, AWS S3, etc
9. Install it on your Android Device
Get access to your React Application in development so you can get a new activation pin.

## Would you like to install the latest APK app on Android?
Please visit the following site for more details
https://smsforall.org/download/

or you can download them directly from the following S3 bucket:
https://smsforall-prod.s3.amazonaws.com/smsforall-v1.3.apk

You can also download the source code and build your own APK version with your
own API and Web Client, here are the other repositories for that matter:
- [Rails Backend API](https://github.com/heridev/smsforall-rails-api]
- [React app application for https://app.smsforall.org](https://github.com/heridev/smsforall-react-app)

## API request
For all the HTTP requests Retrofit 2 it's been utilized

## Would you like to create a new version?
1. Update the version variables:
- versionCode — A positive integer used as an internal version number.
- versionName — A string used as the version number shown to users.

within `app/build.gradle`
2. Create a new tag with it eg:
```
git tag -a v1.2 -m "Generates release app bundle with versionCode 2 and versionName 1.2"
```
3. Push the new tag
```
git push origin v1.2
```
## Main features of the Android Application
- Receiving and intercepting SMS messages
- Full integration with Firebase
- Sending messages


## Rails Backend API for https://api.smsforall.org
https://github.com/heridev/smsforall-rails-api

## React app application for https://app.smsforall.org
https://github.com/heridev/smsforall-react-app


