package ru.sikuda.mobile.xmlparser

data class Employee(
    var id: Int = 0,
    var name: String = "",
    var salary: Float = 0.toFloat()
){
    override fun toString(): String {
        return " Id = $id\n Name = $name\n Salary = $salary"
    }
}
