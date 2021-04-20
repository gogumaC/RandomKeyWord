package yubinkim.android_note.randomkeyword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Xml
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import kotlinx.android.synthetic.main.activity_main.*
import org.xmlpull.v1.XmlPullParser
import yubinkim.android_note.randomkeyword.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main)
        NetworkActivity().loadPage()

//        thread(start=true){
//            try {
//                val KEY = "E6805A7CB002BD232452351FA634CABB"
//                var ranWord = "나무"
//                var urlText = "https://opendict.korean.go.kr/api/search?certkey_no=2127&key=$KEY&" +
//                        "target_type=search&q=$ranWord&sort=dict&start=1&num=10&advanced=y&pos=1&method=include"
//                val url = URL(urlText)
//                val urlConnection = url.openConnection() as HttpURLConnection
//                urlConnection.requestMethod = "GET"
//                if (urlConnection.responseCode == HttpURLConnection.HTTP_OK) {
//                    val streamReader = InputStreamReader(urlConnection.inputStream)
//                    val br = BufferedReader(streamReader)
//                    val content = StringBuilder()
//
//
//                    while (true) {
//                        val line = br.readLine() ?: break
//                        content.append(line)
//
//                    }
//
//                    br.close()
//                    urlConnection.disconnect()
//                    runOnUiThread {
//                        binding.textSample.text = content.toString()
//                    }
//
//                }
//            }catch(e:Exception){
//                e.printStackTrace()
//            }
//        }


            //스피너 어댑터 만듦
            var spinnerData = listOf<Int>(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
            var spinnerAdapter =
                ArrayAdapter<Int>(this, android.R.layout.simple_list_item_1, spinnerData)

            spinner.adapter = spinnerAdapter
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    var viewNum = spinnerData.get(position)
                    Log.d("spinner", "$viewNum")
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }


            }
        }
    }