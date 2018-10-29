# Media Hunter

## Basic info

Media Hunter is used for getting data from various media content providers (hereinafter MCP) and providing it (as JSON files) to apps requesting such information (using REST API). There has to be implemented plugin inside the plugins module of this project for every such MCP. Actively running plugins are: **TBD**. Thanks to those plugins it is possible to register new channels or records (media) to DB. Channels can be registered with or without "trusted" flag. While registering new channel, all of its records are registered as well. NOT TRUSTED channels records have to be accepted (in Records queue) in order to be delivered to other apps, TRUSTED channels records are always accepted. Record is accepted automatically while registering independent record in "Find record" tab. When Media Hunter is running, it is checking all channels for a new content every **TBD** minutes.

Media Hunter has started as a passion project to help me create a rollerblading media news platform and is now part of my bachelor thesis.

## How to run this project

**IMPORTANT** - in order to use this project, you have to add **`secret.properties`** file to **`/core/src/main/resources`**. What to add to this file:

```properties
spring.data.mongodb.uri=URI_TO_MONGODB
spring.data.mongodb.database=DATABASE_NAME
```

Then you can run this project from bash using `mvn clean install && cd /web && mvn spring-boot:run`.

## Project goals

- [ ] Web module
- - [ ] Create Home page
- - - [x] Write static content - project description
- - - [ ] Insert dynamic content to project description
- - [ ] Create pages and controller for Channels
- - - [ ] Find channel (from MCP and DB)
- - - [ ] Add channel (from find channel, if it's not in DB already, add all its records)
- - - [ ] Update channel (Update from MCP to DB)
- - - [ ] Delete channel (with option to delete all videos from this channel)
- - [ ] Create pages and controller for Records
- - - [ ] Find record (from MCP and DB)
- - - [ ] Add record (from find record, if it's not in DB already)
- - - [ ] Update record (update from MCP to DB)
- - - [ ] Delete record
- - - [ ] Record queue
- - [x] Create error handling (at least 404)
- - [ ] Create Rest controller for public access
- - [ ] Create Admin Facade layer providing all necessary admin methods
- - [ ] Create Public Facade layer providing all methods for public REST API
- [ ] Plugins module
- - [ ] Create plugin interface
- - [ ] Implement plugin for YouTube
- - [ ] Implement plugin for Vimeo
- [ ] Core module
- - [ ] Create persistence layer
- - [ ] Create Database service providing all necessary DB operations
- - [ ] Create Plugin service associating all plugins and providing necessary operations
- [ ] Domain module
- - [ ] Create DB entities (Channel, Record)
- - [ ] Create DTOs

List is updated as needed.