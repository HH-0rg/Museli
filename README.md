### Museli

A stateless simple music application that supports android, windows, linux and web out of a single source tree.

#### The problem

There are many people(like me!) who like to own their music, not relying on modern streaming services. Apart from the recommendation engines, a big selling point is the syncing capibilities they offer. This is __relatively__ easy to achieve in a closed ecosystem. Museli is an attempt to provide a similar syncing experience with music libraries but taking advantage of widely available and mature mechanisms, while giving the user full ownership of their media.

#### What makes Museli different?

Museli is an(almost) completely stateless music player, ie it doesn't store any information in custom format files. You need to specify a directory(the only state Museli maintains) where your music files lie. Lets see my Museli library directory:
```
.
├── #BrooklynBloodPop!.mp3
├── 01 - forever we can make it!.mp3
├── 03.  Fly Away.mp3
├── Ado
│   ├── 【Ado】 唱.mp3
│   ├── 【Ado】unravel 歌いました.mp3
│   ├── 【Ado】うっせぇわ.mp3
│   ├── 【Ado】ウタカタララバイ（ウタ from ONE PIECE FILM RED）.mp3
    ├──  ..............
├── 【Ado】 唱.mp3
├── 【Ado】 神っぽいな 歌いました.mp3
├── 【Ado】unravel 歌いました.mp3
├── 【Ado】うっせぇわ.mp3
├── 【Ado】ウタカタララバイ（ウタ from ONE PIECE FILM RED）.mp3
├── 【Ado】ダーリンダンス 歌いました.mp3
├── 【GUMI】KING【Kanaria】.mp3
├──  ............
```
All music files are present directly inside the directory. A playlist is represented by a directory and contains symlinks back to the root library directory. So here we have a playlist `Ado` represented by the `./Ado` dir and all files inside it are symlinks.

Museli is writt0nd in the Kotlin Multiplatform Programming framework and Jetpack Compose for KMP, which allows us to share code(both UI and business logic) between all the platforms Museli supports. Though hard to set-up initially, it is very easy to maintain. It allows us to do things like splitting the desktop application code into webapp+frontend with the help of minimal wrappers around the existing desktop code. This otherwise require considerable effort to architect and code.

#### So what??? I don't see why we need yet another music player...

Syncing is a very hard problem.


There exists the Subsonic API which, on paper, allows you to sync music. Navidrome implements the subsonic server API however the client implementation leave much to be desired. A few apps didn't support unicode file, another only shoed my first 50 songs and OOM's when I tried to make all of them offline and the story goes on. Tt also doesn't prvide 2 way sync, ie, I can't add or delete songs from my Android client. You also need to own a public machine and maintain a server to be able.

I believe syncing through a custom API is the wrong way to go about it, the existing syncing implementations like NFS or collaborative editors tooks years of intensive development to get to the point they are today.

So, why doen't we make use of existing syncing mechanisms? Since all our data is represented in the filesystem, we can use existing filesystem syncing tools, which are very mature and well maintained. Lets see my current setup for example:
- All my songs are in a directory `/Music` in my google drive.
- I use the [google drive windows client](https://www.google.com/intl/en_in/drive/download/) to mount it to my Windowss PC's Music library dir and make them available even when I'm offline. I use [harmonoid](https://github.com/harmonoid/harmonoid) to play songs
- I use [FolderSync](https://foldersync.io/) on my android phone to keep a directory in 2-way sync with that drive folder. Then I use [Auxio](https://github.com/OxygenCobalt/Auxio) to play songs.
- I use [google-drive-ocamlfuse](https://github.com/astrada/google-drive-ocamlfuse) to mount it to my linux webserver and serve the navidrome music player from there. Now even if I don't have my devices, I can listen to my music if I can get access to any device!
- I have a small flask script running on my webserver that allows me to upload music files to that drive mounted dir, and by the virtue of my setup it reaches all my devices automatically and is even available offline.


This gives me perfect syncing on all my devices with offline access where I want it but still cannot allow me to sync playlists. The music players keep using my sync'd dir as free real estate, creating directories and files there as they wish. Museli is spiritually more of a specification and maybe a standard


With Museli the possibilities are endless:
- Use an NFS mounted partition to keep multiple machines's music libraries in sync.
- Use CacheFS to make them available offline
- Run rclone in a cron job(why???)
- Use a git/mercurial/svn server to sync all your files(what???)
- Write you own FUSE driver to store music files and directories in image data and use a pixel4a as a backend so you can leverage the [inifite Google photos storage](https://www.youtube.com/watch?v=oNlnfp3zOeU) it gets. Voila infinite music library.


#### Current state

The code is super vaporwave, well, it is a POC hackathon product. So it will be tough to get it running, however since, I'm annoyed with the lack of playlists sync in my beautiful semi-self-hosted music setup, you can surely expect it to get better.

#### Build & Run instructions

You can open the project in the Fleet editor and the Run button will allow you to build and run all versions of the project. Alternate options(Since fleet kept crashing on me...):

- Android: Use android studio
- Desktop: `./gradlew desktopRun -DmainClass=MainKt`
- Web backend: `./gradlew :server:run --args="C:\\Users\\manas\\Music localhost:8081"`. Where first arg is your music dir and ssecond arg is host of frontend(to allow CORS)
- Web frontend(WASM): `./gradlew wasmJsBrowserRun`
