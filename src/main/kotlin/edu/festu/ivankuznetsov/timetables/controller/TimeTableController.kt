package edu.festu.ivankuznetsov.timetables.controller

import edu.festu.ivankuznetsov.timetables.model.TimeTable
import edu.festu.ivankuznetsov.timetables.service.TimeTableService
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.net.MalformedURLException
import java.nio.file.Path
import java.nio.file.Paths




@RestController
class TimeTableController {
    private val fileStorageLocation: Path = Paths.get("src/main/resources/static").toAbsolutePath().normalize()

    private final val service = TimeTableService()
    @GetMapping("/group/{groupId}")
    fun getByGroup(@PathVariable groupId: String): Mono<TimeTable> =  service.getByGroup(groupId).map {
        it.body
    }
    @GetMapping("/download/{fileName}")
    fun downloadFile(@PathVariable fileName: String): ResponseEntity<Resource> {
        try {
            val filePath: Path = fileStorageLocation.resolve(fileName).normalize()
            val resource: Resource = UrlResource(filePath.toUri())

            return if (resource.exists()) {
                ResponseEntity.ok()
                    .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        ("attachment; filename=\"" + resource.filename) + "\""
                    )
                    .body(resource)
            } else {
                ResponseEntity.notFound().build()
            }
        } catch (ex: MalformedURLException) {
            return ResponseEntity.badRequest().build()
        }
    }

}