package yubinkim.android_note.randomkeyword

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.os.AsyncTask
import android.preference.PreferenceManager
import android.util.Log
import android.widget.TextView
import org.xmlpull.v1.XmlPullParserException
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

private const val DEBUG_TAG = "NetworkStatusExample"

class NetworkActivity: Activity() {


    companion object{
        const val WIFI="Wi-Fi"
        const val Any="Any"
        val KEY = "E6805A7CB002BD232452351FA634CABB"
        var ranWord = "나무"
        var URL="https://opendict.korean.go.kr/api/search?certkey_no=2127&key=$KEY&" +
                "target_type=search&q=$ranWord&sort=dict&start=1&num=10&advanced=y&pos=1&method=include"


    }

    fun loadPage(){
            DownloadXmlTask().execute(URL)
    }

    private inner class DownloadXmlTask: AsyncTask<String, Void, String>(){
        override fun doInBackground(vararg urls: String): String {
            return try{
                loadXmlFromNetWork(urls[0])
            }catch(e:IOException){
                resources.getString(R.string.connection_error)
            }catch(e:XmlPullParserException){
                resources.getString(R.string.xml_error)
            }
        }

        override fun onPostExecute(result: String?) {
            //setContentView(R.layout.activity_main)
            //val tx=findViewById<TextView>(R.id.textSample)
            Log.d("checkfor",result)

        }
    }


    @Throws(XmlPullParserException::class,IOException::class)
    private fun loadXmlFromNetWork(urlString:String):String {

//        val pref: Boolean = PreferenceManager.getDefaultSharedPreferences(this)?.run {
//            getBoolean("summaryPref", false)
//        } ?: false

        val words:List<Word> = downloadUrl(urlString)?.use { stream->
            XmlParser().parse(stream)
        } ?: emptyList()

        return StringBuilder().apply{
            //if(pref) {

                Log.d("checkfor","$words")
                append(words)
            //}
        }.toString()


//        val url = URL(URL)
//        val urlConnection = url.openConnection() as HttpURLConnection
//        urlConnection.requestMethod = "GET"
//        if (urlConnection.responseCode == HttpURLConnection.HTTP_OK) {
//            val streamReader = InputStreamReader(urlConnection.inputStream)
//            val br = BufferedReader(streamReader)
//            val content = StringBuilder()
//
//            while (true) {
//                val line = br.readLine() ?: break
//                content.append(line)
//            }
//            return content.toString()
//        }else return "헷 에러당 @o@/"

    }

    @Throws(IOException::class)
    private fun downloadUrl(urlString:String): InputStream?{
        val url=URL(urlString)
        return (url.openConnection() as? HttpURLConnection)?.run {
            readTimeout=10000
            connectTimeout=15000
            requestMethod="GET"
            doInput=true
            connect()
            inputStream
        }
    }


}