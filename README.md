# XBoxApi
The apis for retrieving user profiles, games and achievements information from XBox live.

###System requirement:
- The system requires js libs: [phantomjs](http://phantomjs.org/) and [casperjs](http://casperjs.org/), and please set their bin directory to the sytem path.

###How to build:
- The system is created by Java 1.7 and built by maven. If you don't have any experience about maven, please reference to <http://maven.apache.org/guides/>.
- The system depends on both [javaPlatform](https://github.com/elminsterjimmy/javaPlatform) which provides the parent poms and [javacommons](https://github.com/elminsterjimmy/javacommons) which provides common functions. Please make sure you also got these 2 projects.

###How does it work?
- It uses web browser engine to login into the MS live, and then retrieves the user profiles, game and achievement information via parsing the HTML result from user achievement comparison pages.

###What the API provides?
- The api provides interfaces to retrieve the user profiles, user's game lists and user's game achievements. (Please check the detail in interface com.elminster.retrieve.service.IXboxApi)

###How to use?
- The system requires the login inforamtion for MS live, you may put the information into a properties file named "System.properties" and set it with keys: MS_LIVE_USERNAME and MS_LIVE_PASSWORD. Here's a sample System.properties file.  
MS_LIVE_USERNAME=\<MS live username\>  
MS_LIVE_PASSWORD=\<MS live password\>
