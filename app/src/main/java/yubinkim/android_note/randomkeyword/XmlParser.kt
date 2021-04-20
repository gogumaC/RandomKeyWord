package yubinkim.android_note.randomkeyword

import android.util.Log
import android.util.Xml
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParser.START_TAG
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStream
import java.lang.IllegalStateException

private val nameSpace:String?=null
class XmlParser {

    //파서 인스턴스화
    @Throws(XmlPullParserException::class, IOException::class)
    fun parse(inputStream: InputStream): List<Word> {
        inputStream.use { inputStream ->
            val parser: XmlPullParser = Xml.newPullParser()
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(inputStream, null)
            parser.nextTag()
            return readFeed(parser)
        }
    }
}



    //피드읽기
    @Throws(XmlPullParserException::class, IOException::class)
    private fun readFeed(parser: XmlPullParser): List<Word> {
        val words = mutableListOf<Word>()

        //parser.require(XmlPullParser.START_TAG, nameSpace, "feed")
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            //태그찾기
            if (parser.name == "item") {
                words.add(readItem(parser))
            } else {
                skip(parser)
            }
        }
        return words
    }

    //xml 파싱
    data class Word(val word: String?, val category: String?, val definition: String?)

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readItem(parser: XmlPullParser): Word {
        parser.require(XmlPullParser.START_TAG, nameSpace, "item")
        var word: String? = null

        var position: String? = null
        var definition: String? = null
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) continue
            when (parser.name) {
                "word" -> word = readWord(parser)
                "pos" -> position = readPosition(parser)
                "definition" -> definition = readDefinition(parser)
                else -> skip(parser)
            }
        }
        return Word(word, position, definition)
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readWord(parser: XmlPullParser): String {
        parser.require(XmlPullParser.START_TAG, nameSpace, "word")
        val word = readText(parser)
        parser.require(XmlPullParser.END_TAG, nameSpace, "word")
        return word
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readPosition(parser: XmlPullParser): String {
        parser.require(XmlPullParser.START_TAG, nameSpace, "position")
        val position = readText(parser)
        parser.require(XmlPullParser.END_TAG, nameSpace, "position")
        return position
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readDefinition(parser: XmlPullParser): String {
        parser.require(XmlPullParser.START_TAG, nameSpace, "definition")
        val definition = readText(parser)
        parser.require(XmlPullParser.END_TAG, nameSpace, "definition")
        return definition
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readText(parser: XmlPullParser): String {
        var result = ""
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.text
            parser.nextTag()
        }
        return result
    }

    @Throws(XmlPullParserException::class,IOException::class)
    private fun skip(parser:XmlPullParser){
        if(parser.eventType !=XmlPullParser.START_TAG){
            throw IllegalStateException()
        }
        var depth=1
        while(depth !=0){
            when(parser.next()) {
                XmlPullParser.END_TAG -> depth--
                XmlPullParser.START_TAG->depth++
            }
        }
    }
