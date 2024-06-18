package edu.festu.ivankuznetsov.timetables.model

data class Lesson(var date: String = "",
                  var lesson: String = "",
                  var lessonType: String = "",
                  var teacher: String = "",
                  var teacherContact: String = "",
                  var group: String = "",
                  var auditorium: String = "")
