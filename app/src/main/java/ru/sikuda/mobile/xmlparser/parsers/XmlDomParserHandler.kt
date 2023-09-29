package ru.sikuda.mobile.xmlparser.parsers

import org.w3c.dom.Element
import org.w3c.dom.Node
import org.xml.sax.SAXException
import ru.sikuda.mobile.xmlparser.Employee
import java.io.IOException
import java.io.InputStream
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException

class XmlDomParserHandler {
    private val employees = ArrayList<Employee>()
    fun parse(inputStream: InputStream): List<Employee> {
        try {
            val builderFactory = DocumentBuilderFactory.newInstance()
            val docBuilder = builderFactory.newDocumentBuilder()
            val doc = docBuilder.parse(inputStream)
            //reading the tag "employee" of empdetail file
            val nList = doc.getElementsByTagName("employee")
            for(i in 0 until nList.getLength()) {
                if(nList.item(0).getNodeType().equals(Node.ELEMENT_NODE) ) {
                    val element = nList.item(i) as Element
                    employees.add(
                        Employee( i,
                            getNodeValue("name", element),
                            getNodeValue("salary", element).toFloat())
                    )
                }
            }
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

fun getNodeValue(tag: String, element: Element): String {
    val nodeList = element.getElementsByTagName(tag)
    val node = nodeList.item(0)
    if(node != null) {
        if(node.hasChildNodes()) {
            val child = node.getFirstChild()
            while(child != null) {
                if(child.getNodeType() === Node.TEXT_NODE) {
                    return child.getNodeValue()
                }
            }
        }
    }
    return ""
}
