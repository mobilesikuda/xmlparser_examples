package ru.sikuda.mobile.xmlparser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import ru.sikuda.mobile.xmlparser.parsers.XmlDomParserHandler
import ru.sikuda.mobile.xmlparser.parsers.XmlPullParserHandler
import ru.sikuda.mobile.xmlparser.parsers.XmlSaxParserHandler
import java.io.IOException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val listView  = findViewById(R.id.listView) as ListView
        lateinit var employees: List<Employee>
        try {
            val parser1 = XmlPullParserHandler()
            val parser2 = XmlSaxParserHandler()
            val parser3 = XmlDomParserHandler()

            val istream = assets.open("employees.xml")
            employees = parser2.parse(istream)

            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, employees)
            //val adapter = SimpleAdapter(this@MainActivity, empList, R.layout.custom_list, arrayOf("name", "salary", "designation"), intArrayOf(R.id.name, R.id.salary))
            listView.adapter = adapter

        } catch(e: IOException) {
            e.printStackTrace()
        }


    }
}