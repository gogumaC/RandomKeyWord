package yubinkim.android_note.randomkeyword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import javax.xml.parsers.DocumentBuilderFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val KEY="E6805A7CB002BD232452351FA634CABB"
        var ranWord="나무"
        var url="https://opendict.korean.go.kr/api/search?certkey_no=2127&key=$KEY&" +
                "target_type=search&q=$ranWord&sort=dict&start=1&num=100&advanced=y&pos=1&method=include"
        //xml파싱위한코드
        val xml: Document =DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(url)
        //val xml:String=parse(url)
        xml.documentElement.normalize()
        Log.d("checkfor","Root element : ${xml.documentElement.nodeName}")


        //찾고자하는 데이터가 어느 노드아래있는지 확인
        val list: NodeList =xml.getElementsByTagName("item")

//        for(i in 0..list.length-1){
//            var n: Node =list.item(i)
//            if(n.getNodeType()==Node.ELEMENT_NODE){
//                val elem=n as Element
//                val map=mutableMapOf<String,String>()
//
//                for(j in 0..elem.attributes.length-1) map.putIfAbsent(elem.attributes.item(j).nodeName,elem.attributes.item(j).nodeValue)
//            }
//        }
        Log.d("checkfor","${list}")

        //스피너 어댑터 만듦
        var spinnerData= listOf<Int>(1,2,3,4,5,6,7,8,9,10)
        var spinnerAdapter=ArrayAdapter<Int>(this,android.R.layout.simple_list_item_1,spinnerData)

        spinner.adapter=spinnerAdapter
        spinner.onItemSelectedListener=object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var viewNum=spinnerData.get(position)
                Log.d("spinner","$viewNum")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }


        }
    }
}