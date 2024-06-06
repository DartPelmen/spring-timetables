package edu.festu.ivankuznetsov.timetables.service

import edu.festu.ivankuznetsov.timetables.model.TimeTable
import edu.festu.ivankuznetsov.timetables.util.parseTimeTable
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.BodyInserters.fromFormData
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.toEntity
import reactor.core.publisher.Mono

@Service
class TimeTableService {
    private val client = WebClient.builder().baseUrl("https://dvgups.ru/index.php?Itemid=1246&option=com_timetable&view=newtimetable")

    fun getByGroup(groupId: String): Mono<ResponseEntity<TimeTable>> {
        return client.build()
            .post()
            .body(fromFormData("GroupID", groupId).with("GroupID", groupId)).retrieve().toEntity<String>().map {
                parseTimeTable(it.body.toString())
                ResponseEntity<TimeTable>(parseTimeTable(it.toString()),HttpStatusCode.valueOf(200))
            }

    }

}