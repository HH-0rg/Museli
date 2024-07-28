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

    // Format the output string
    return when {
//        hours > 0 -> String.format("%02d:%02d:%02d", hours, minutes, seconds)
//        else -> String.format("%02d:%02d", minutes, seconds)
        hours > 0 -> "$hours:$minutes:$seconds"
        else -> "$minutes:$seconds"
    }
}
