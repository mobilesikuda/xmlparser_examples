package ru.sikuda.mobile.xmlparser.parsers

import org.xml.sax.SAXException
import org.xml.sax.helpers.DefaultHandler
import ru.sikuda.mobile.xmlparser.Employee
import java.io.IOException
import java.io.InputStream
import javax.xml.parsers.ParserConfigurationException
import javax.xml.parsers.SAXParser
import javax.xml.parsers.SAXParserFactory

class XmlSaxParserHandler {
    private val employees = ArrayList<Employee>()
    private var employee: Employee = Employee()
    private var text: String = ""

    fun parse(inputStream: InputStream): List<Employee> {
        try {
            val parserFactory = SAXParserFactory.newInstance()
            //instancing the SAXParser class
            val saxParser: SAXParser = parserFactory.newSAXParser()
            val defaultHandler= object : DefaultHandler() {
                var currentValue = ""
                var currentElement = false
                //overriding the startElement() method of DefaultHandler
                override fun startElement(uri: String, localName: String, qName: String, attributes: org.xml.sax.Attributes) {
                    currentElement = true
                    currentValue = ""
                    if(localName == "employee") {
                        employee = Employee()
                    }
                }
                //overriding the endElement() method of DefaultHandler
                override fun endElement(uri: String, localName: String, qName: String) {
                    currentElement = false
                    if(localName.equals("name", ignoreCase = true))
                        employee.name = currentValue
                    else if(localName.equals("salary", ignoreCase = true))
                        employee.salary= currentValue.toFloat()
                    else if(localName.equals("employee", ignoreCase = true))
                        employees.add(employee)
                }
                //overriding the characters() method of DefaultHandler
                override fun characters(ch: CharArray, start: Int, length: Int) {
                    if(currentElement) {
                        currentValue = currentValue + String(ch, start, length)
                    }
                }
            }
            saxParser.parse(inputStream, defaultHandler)
        } catch(e: IOException) {
            e.printStackTrace()
        } catch(e: ParserConfigurationException) {
            e.printStackTrace()
        } catch(e: SAXException) {
            e.printStackTrace()
        }
        return employees
    }
}