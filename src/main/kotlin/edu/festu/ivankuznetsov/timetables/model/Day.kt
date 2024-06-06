package edu.festu.ivankuznetsov.timetables.model

import java.time.LocalDateTime

data class Day(val date: String, val lessons : MutableList<Lesson>)
