# Media Hunter

## Basic info

Media Hunter is used for getting data from various media content providers (hereinafter MCP) and providing it (as JSON files) to apps requesting such information (using REST API). There has to be implemented plugin inside the plugins module of this project for every such MCP. Actively running plugins are: *YouTube*, *Vimeo*. Thanks to those plugins it is possible to register new channels or multimedia (media) to DB. Channels can be registered with or without "trusted" flag. While registering new channel, all of its multimedia are registered as well. NOT TRUSTED channels multimedia have to be accepted (in Multimedia queue) in order to be delivered to other apps, TRUSTED channels multimedia are always accepted. Multimedium is accepted automatically while registering independent multimedium in "Find multimedium" tab (in Web administration). When Media Hunter is running, it is checking all channels for a new content every **X** minutes.

Media Hunter has started as a passion project to help me create a rollerblading media news platform and is now part of my bachelor thesis.

## How to run this project

**IMPORTANT** - in order to use this project, you have to add **`secret.properties`** file to **`/plugins/src/main/resources`**. This file has to contain:

```properties
spring.data.mongodb.uri=URI_TO_MONGODB
spring.data.mongodb.database=DATABASE_NAME
youtube.apikey=YOUR_API_KEY
vimeo.apikey=YOUR_VIMEO_ACCESS_TOKEN
```

Then you can run this project from bash using `mvn clean install && cd /core && mvn spring-boot:run`.