Android App for Smsparatodos.com
=================================

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