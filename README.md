Android App for smsforall.org
=================================

## How to setup the project locally?
1. Clone the repo
2. Open Android Studio
3. Configure your Backend API according to your needs, in this https://github.com/heridev/sms-mobile-hub/blob/main/gradle.properties#L24 file
4. Rebuild the project without errors
![image](https://github.com/heridev/sms-mobile-hub/assets/1863670/f41327f0-4521-4b5c-8213-f7914cc2549f)
5. Generate the APK so you can install it in your Phone, or download this one that is ready for installation
[link Pending](https://)

## Do you want to install the latest APK app in Android
Please visit the following site for more details
https://smsforall.org/download/

or you can download directly the apk from the following S3 bucket:
https://smsforall-prod.s3.amazonaws.com/smsforall-v1.3.apk

Or you can download the source code and build your own APK version with your
own API(the open-source backend should be available soon - It is a promise)

## Api request
For this we are going to use Retrofit 2

## Creating new version release in Bitbucket with git
1. Updates the version variables:
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
## Big features
- Receive sms - intercept SMS notifications
- Send messages

## Branches management

We use the branch `develop` for developing new stuff and once we want to make a new release
then we merge that into the `main` branch and create a new apk signed package.

## Rails Backend API for https://api.smsforall.org
https://github.com/heridev/smsforall-rails-api

## React app application for https://app.smsforall.org
https://github.com/heridev/smsforall-react-app


