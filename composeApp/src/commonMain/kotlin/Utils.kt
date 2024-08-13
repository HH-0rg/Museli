fun formatDuration(milliseconds: Long?): String {
    if(milliseconds==null || milliseconds < 0) {
        return "00:00"
    }
    // Calculate total seconds from milliseconds
    val totalSeconds = milliseconds / 1000

    // Calculate hours, minutes, and seconds
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60

    val pad = { number: Int -> number.toString().padStart(2, '0') }

    return if (hours > 0) {
        "${pad(hours.toInt())}:${pad(minutes.toInt())}:${pad(seconds.toInt())}"
    } else {
        "${pad(minutes.toInt())}:${pad(seconds.toInt())}"
    }
}

fun nullError() = "got null directory from filepicker"
