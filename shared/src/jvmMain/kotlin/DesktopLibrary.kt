class DesktopLibrary(rootDir: String): Library() {
    override var playlists: Map<String, List<String>> = getPlaylists(rootDir)
    override var songsList: List<String> = getSongs(rootDir)
}
