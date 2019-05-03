package azUtils

import java.io.File
import java.io.FileNotFoundException
import java.io.FileReader
import java.nio.file.Paths
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import java.util.concurrent.CompletableFuture


/**
 * A shortcut value for providing the directory path to the
 * application's root directory.
 *
 * @return the Path to the application's root directory
 */
val pathHome = Paths.get(System.getProperty("user.dir"))

/**
 * A shortcut value for providing a completed CompletableFuture.
 *
 * @return a truely completed CompletableFuture
 */
val trueFuture = CompletableFuture.completedFuture(true)


/**
 * Concatenate an "s" to the end of an existing String, based on
 * the value of an incoming Integer being greater than 1.
 *
 * @param text the text to be modified
 * @param size the value to evaluate
 * @return the (un)modified text as a String
 */
fun plural(text: String, size: Int) = "$text${if (size > 1) "s" else ""}"


/**
 * A shortcut method for printing to a row
 * and breaking at the end of the line
 *
 * @param text the `String`, or collection of `Strings`,
 * that will be print to the System console
 */
fun prLn(vararg text: String) = text.forEach { s -> print("\n$s") }


/**
 * A shortcut method for printing to a row
 * in the System console that does not end with a line break,
 * but does end with a space.
 *
 * @param text the `String`, or collection of `Strings`,
 * that will be printed to the System console
 */
fun prSp(vararg text: String) = text.forEach { s -> print("$s ") }


/**
 * A shortcut method for printing to a row
 * in the System console that does not end with a line break
 *
 * @param text the `String`, or collection of `Strings`,
 * that will be print to the System console
 */
fun prnt(vararg text: String) = text.forEach { s -> print(s) }


/**
 * Used to retrieve a `String` input from the System console.
 *
 * @return input from the console as a String
 */
fun input(): String {
    print("▶")
    return Scanner(System.`in`).next()
}


/**
 * Gets an authentication token as a `String` from a remote directory.
 *
 * @param path  each directory and finally the name of the
 * file that contains the token to be read
 * @return the token as a `String`
 */
fun getTokenFromFile(vararg path: String): String {
    val sb = StringBuilder()
    try {
        val fileScan = Scanner(FileReader(Paths.get("$pathHome", *path).toFile()))
        while (fileScan.hasNext()) sb.append(fileScan.next())
        fileScan.close()
    } catch (e: FileNotFoundException) { e.printStackTrace() }

    return sb.toString()
}


/**
 * Convert millis into a formatted time string.
 *
 * @param epochMilli the time as epoch milli
 * @return the formatted date and time as a `String`
 */
fun time(epochMilli: Long) = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(Locale.US).withZone(ZoneId.systemDefault()).format(Instant.ofEpochMilli(epochMilli))


/**
 * Get a preformatted time string.
 *
 * @return the formatted date and time as a `String`
 */
fun time() = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(Locale.US).withZone(ZoneId.systemDefault()).format(Instant.ofEpochMilli(timeMillis()))


/**
 * Get the current date and time in the form of milliseconds.
 *
 * @return the time in millis
 */
fun timeMillis() = System.currentTimeMillis()


/**
 * Table format a `String`.
 *
 * @param width the width of the table
 * @param text  the text to be formatted
 * @return the formatted table as a `String`
 */
fun table(width: Int, vararg text: String): String {
    val column = StringBuilder()
    text.forEach { s ->
        column.append(s)
        (0 until width - s.length).forEach { column.append(" ") }
    }
    return column.toString()
}

/**
 * Exit the application with a Shutdown.
 *
 * @param code the exit code to display after Shutdown
 * @param text the text to display before shutdown
 */
fun exit(code: Int, text: String) {
    println("\uD83D\uDED1 $text")
    System.exit(code)
}

/**
 * Add a Shutdown Hook to the application.
 *
 * @param text the text to display before shutdown
 */
fun addShutdownHook(text: String) = Runtime.getRuntime().addShutdownHook(Thread { println(text) })


/**
 * Consume a Runnable, and loop it for the interval provided, converted from milliseconds.
 *
 * @param interval the interval
 * @param runnable the runnable
 */
fun loopRun(interval: Long, runnable: Runnable) = Timer().schedule(object : TimerTask() {
    override fun run() { runnable.run() }
}, interval, interval)


/**
 * TODO: WTF DOES THIS DO?
 *
 * @param name soon.
 * @param length soon.
 */
fun truncate(name: String, length: Int): String {
    val re = Regex("[^A-Za-z0-9 ]")
    if (name.length > length) return re.replace(name, "X").substring(0, length)
    else return re.replace(name, "X")
}


/**
 * TODO: WTF DOES THIS DO?
 *
 * @param inStr soon.
 */
fun addCommas(inStr: String):String {
    var commas = if (inStr.length % 3 == 0) (inStr.length/3)-1 else inStr.length/3
    var outStr = ""
    for (i in 0..commas-1) outStr = if (inStr.length > 3) ",${inStr.substring(inStr.length-(3*(i+1)), inStr.length-(3*i))}${outStr}" else "${inStr.substring(inStr.length-(3*(i+1)), inStr.length-(3*i))}${outStr}"
    return inStr.substring(0, inStr.length-(3*commas)) + outStr
}


/**
 * TODO: WTF DOES THIS DO?
 *
 * @param fileName soon.
 * @param text soon.
 */
fun writeToFile(fileName: String, text: String) {
    File(fileName).writeText(text)
}