package application

import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleStringProperty
import javafx.scene.text.FontWeight
import javafx.stage.FileChooser
import tornadofx.*
import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.Files

class Store: Controller(){
    val titleProperty = SimpleStringProperty("")
    var book = Book(titleProperty.value)
    val textView: TextView by inject<TextView>()

    fun addVerse(chapter: Chapter?, text:String){
        var newVerse = Verse(text.removePrefix("\\v").trim())
        chapter?.verses?.add(newVerse)
    }
    fun addChapter(book: Book, text:String){
        var newChapter = Chapter(text.removePrefix("\\c").trim())
        book.chapters.add(newChapter)
    }

    fun changeTitle(text: String){
        this.book.titleProperty.value = text.removePrefix("\\h").trim()
    }
//    fun addBook(text: String){
//        var newBook = Book(text.removePrefix("\\h").trim())
//        book = newBook
//    }

    fun parseText(text: String){
        val fileContents = text.split("\n")
        loop@for(line in fileContents){
            when{
                line.startsWith("\\h") -> changeTitle(line)
                line.startsWith("\\c") -> addChapter(book, line)
                line.startsWith("\\v") -> addVerse(book.chapters?.last(), line)
                else -> continue@loop
            }
        }
    }

    fun loadFile(parser: (File) -> String = { Files.readAllBytes(it.toPath()).toString(StandardCharsets.UTF_8)},
                 filters: Array<FileChooser.ExtensionFilter>){
        var fileContents = SimpleStringProperty()
        chooseFile("Select USFM File", filters, FileChooserMode.Single).singleOrNull()?.let{
            fileContents.value = parser.invoke(it)
        }
        this.parseText(fileContents.value)
    }
}

class Book(title: String){
    val titleProperty = SimpleStringProperty(title)
    var title by titleProperty
    val chaptersListProperty = SimpleListProperty<Chapter>()
    var chapters by chaptersListProperty
}

class Chapter(title: String){
    val titleProperty = SimpleStringProperty("Chapter $title")
    var title by titleProperty
    val verseListProperty = SimpleListProperty<Verse>()
    var verses by verseListProperty
}

class Verse(text: String){
    val textProperty = SimpleStringProperty(text)
    var text by textProperty
}

class TextView: View(){
    val store: Store by inject()
    override val root = vbox{
        text(store.book.titleProperty){
            style{
                fontWeight = FontWeight.EXTRA_BOLD
            }
        }
        text(){}
    }
}