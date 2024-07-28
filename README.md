### Museli

A stateless simple music application that supports android, windows, linux and web out of a single source tree.

##### The problem

There are many people(like me!) who like to own their music, not relying on modern streaming services. Apart from the recommendation engines, a big selling point is the syncing capibilities they offer. This is relatively easy to achieve in a closed ecosystem. Museli is an attempt to provide a similar experience but taking andvantage of available mechanisms.

##### What makes Museli different?

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
All music files are present directly inside the directory. A playlist is represented by a directory and contains symlinks back to the root library directory. So here we have a playlist `Ado` represented dy the `./Ado` dir and all files inside it are symlinks.

Museli is writted in the Kotlin Multiplatform Programming framework and Jetpack Compose for KMP, which allows us to share code(both UI and business logic) between all the platforms Museli supports. Though hard to set-up initially, it is very easy to maintain. It allows us to do things like splitting the desktop application code into webapp+frontend with the help of minimal wrappers around the existing desktop code.This otherwise require considerable effort to architect and code.

##### Why???

Syncing is a very hard problem. So, why doen't we make use of existing syncing mechanisms? Since all our data is represented in the filesystem, we can use existing filesystem syncing tools, which are very mature and well maintained. You can do things like:

What my current setup is:
- All my songs are in a directory `/Music` in my google drive.
- I use the [google drive windows client](https://www.google.com/intl/en_in/drive/download/) to mount it to my Windowss PC's Music library dir and make them available even when I'm offline. I use [harmonoid](https://github.com/harmonoid/harmonoid) to play songs
- I use [FolderSync](https://foldersync.io/) on my android phone to keep a directory in 2-way sync with that drive folder. Then I use [Auxio](https://github.com/OxygenCobalt/Auxio) to play songs.
- I use [google-drive-ocamlfuse](https://github.com/astrada/google-drive-ocamlfuse) to mount it to my linux webserver and serve the navidrome music player from there. Now even if I don't have my devices, I can listen to my music if I can get access to any device!
- I have a small flask script running on my webserver that allows me to upload music files to that drive mounted dir, and by the virtue of my setup it reaches all my devices automatically and is even available offline.


However This still doesn't allow me to sync playlists and the music players keep using my sync'd dir as free real estate.


With Museli the possibilities are endless:
- Use an NFS mounted partition to keep multiple machines in sync.
- 
- Use CacheFS to make them available offline
- Run rclone in a cron job(why???)
- Write you own FUSE driver to roll them out to all your devices, because none of the existing solutions work...


##### Current state

The code is super vaporwave, well it is a hackathon product. So it will be tough to get it running, however since, I'm annoyed with the lack of playlists sync in my beautiful semi-self-hosted music setup, you can surely expect it to get better.
