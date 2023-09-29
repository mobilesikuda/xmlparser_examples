package ru.sikuda.mobile.xmlparser.parsers

import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import ru.sikuda.mobile.xmlparser.Employee
import java.io.IOException
import java.io.InputStream

class XmlPullParserHandler {
    private val employees = ArrayList<Employee>()
    private var employee: Employee? = null
    private var text: String = ""

    fun parse(inputStream: InputStream): List<Employee> {
        try {
            val factory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = true
            val parser = factory.newPullParser()
            parser.setInput(inputStream, null)
            var eventType = parser.eventType
            while(eventType != XmlPullParser.END_DOCUMENT) {
                val tagname = parser.name
                when(eventType) {
                    XmlPullParser.START_TAG -> if(tagname.equals("employee", ignoreCase = true)) {
                        // create a new instance of employee
                        employee = Employee()
                    }
                    XmlPullParser.TEXT -> text = parser.text
                    XmlPullParser.END_TAG -> if(tagname.equals("employee", ignoreCase = true)) {
                        // add employee object to list
                        employee?.let { employees.add(it) }
                    } else if(tagname.equals("id", ignoreCase = true)) {
                        employee!!.id = Integer.parseInt(text)
                    } else if(tagname.equals("name", ignoreCase = true)) {
                        employee!!.name = text
                    } else if(tagname.equals("salary", ignoreCase = true)) {
                        employee!!.salary = java.lang.Float.parseFloat(text)
                    }

                    else -> {
                    }
                }
                eventType = parser.next()
            }

        } catch(e: XmlPullParserException) {
            e.printStackTrace()
        } catch(e: IOException) {
            e.printStackTrace()
        }
        return employees
    }
}
