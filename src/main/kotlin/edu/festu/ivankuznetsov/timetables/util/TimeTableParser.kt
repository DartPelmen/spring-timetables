package edu.festu.ivankuznetsov.timetables.util

import edu.festu.ivankuznetsov.timetables.model.Day
import edu.festu.ivankuznetsov.timetables.model.Lesson
import edu.festu.ivankuznetsov.timetables.model.TimeTable

fun parseTimeTable(timeTable: String): TimeTable {
    //{"message":"<h3>07.06.2024 Пятница (40-я неделя)</h3><table class='table table-hover table-bordered'><tr class='d-flex'><td class='col-2' wrap> <div> <div> <b style='font-weight: 800;'>  5-я пара  </b></div>15:15-16:50</div></td><td class='col-4' wrap><div>(Предэкзаменационные консультации) Состав сооружений магистральных трубопроводов и объектов трубопроводного транспорта</div><div>FreeConferenceCall: <a style='display: inline !important;text-decoration: underline;' target='_blank' href='https://www.freeconferencecall.com/wall/kuzdvl7/viewer'>kuzdvl7</a> код доступа: 1554145#\n</div></td><td class='col-sm-2' wrap>а. 3421 </td><td class='col-sm-2' >БВ921НГД [2022] НД - ЭиО</td><td class='col-sm-2' style='border-right: 1px solid #dee2e6;' wrap><div>Кузьминых Дмитрий Владимирович <a style=' display: inline !important;   vertical-align: middle;' href='mailto:kuzdvl@mail.ru\n'>&#9993;</a></div></td></tr></table> <h3>08.06.2024 Суббота (40-я неделя)</h3><table class='table table-hover table-bordered'><tr class='d-flex'><td class='col-2' wrap> <div> <div> <b style='font-weight: 800;'>  3-я пара  </b></div>11:35-13:05</div></td><td class='col-4' wrap><div>(Экзамен) Состав сооружений магистральных трубопроводов и объектов трубопроводного транспорта</div><div>FreeConferenceCall: <a style='display: inline !important;text-decoration: underline;' target='_blank' href='https://www.freeconferencecall.com/wall/kuzdvl7/viewer'>kuzdvl7</a> код доступа: 1554145#\n</div></td><td class='col-sm-2' wrap>а. 3421 </td><td class='col-sm-2' >БВ921НГД [2022] НД - ЭиО</td><td class='col-sm-2' style='border-right: 1px solid #dee2e6;' wrap><div>Кузьминых Дмитрий Владимирович <a style=' display: inline !important;   vertical-align: middle;' href='mailto:kuzdvl@mail.ru\n'>&#9993;</a></div></td></tr></table> "}
    val re = "<[^<>]+>"
    var s = timeTable.replace(Regex(re), "\n")
    while (s.contains("\n\n")) {
        s = s.replace(Regex("\n{2,}\\s*"), " !HTML!\n")
    }
    while (s.contains(Regex("\n"))) {
        s = s.replace("\n", " ")
    }
    s = s.replace("&#9993","")

    val tim = TimeTable(mutableListOf())
    s.split("!HTML!").forEach {
        if (it.isNotBlank() && it.isNotEmpty()) {
            if (it.contains("неделя")) {

                tim.days.add((Day(it, mutableListOf())))
            } else {
                if (it.contains(Regex("(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]"))) {

                    tim.days.last().lessons.add(Lesson(date = it))
                } else {
                    if (tim.days.isNotEmpty()) {
                        if (tim.days.last().lessons.isNotEmpty()) {

                            if (it.isNotBlank()) {
                                if (it.contains("(")) {
                                    val lessonInfo = it.split(")")
                                    tim.days.last().lessons.last().lessonType = lessonInfo.first().replace("(","")
                                    tim.days.last().lessons.last().lesson = lessonInfo.last()
                                } else if (it.contains("Discord") ||
                                    it.contains("Zoom") ||
                                    it.contains("FreeConferenceCall")
                                ) {
                                    tim.days.last().lessons.last().teacherContact = it
                                } else if (it.contains("а.")) {
                                    tim.days.last().lessons.last().auditorium = it
                                } else if (it.contains(Regex("[А-Яа-яёЁ]{2,3}\\d{2,3}[А-Яа-яёЁ]{3}"))) {
                                    tim.days.last().lessons.last().group = it
                                } else {
                                    tim.days.last().lessons.last().teacher = it

                                }

                            }
                        }


                    }
                }
            }
        }

    }
    return tim
}