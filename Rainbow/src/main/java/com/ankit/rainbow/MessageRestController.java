package com.ankit.rainbow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
class MessageRestController {

	Logger logger = LoggerFactory.getLogger(MessageRestController.class);

	@Value("${msg:Hello world - Config Server is not working..pelase check}")
	private String msg;

	@RequestMapping("/msg")
	String getMsg() {
		return this.msg;
	}

	@GetMapping("/students/{studentId}/courses/{courseId}")
	public void retrieveDetailsForCourse(@PathVariable String studentId, @PathVariable String courseId) {
		logger.trace("Student-id={} and Course-id={}", studentId, courseId);
		logger.debug("Student-id={} and Course-id={}", studentId, courseId);
		logger.info("Student-id={} and Course-id={}", studentId, courseId);
		logger.warn("Student-id={} and Course-id={}", studentId, courseId);
		logger.error("Student-id={} and Course-id={}", studentId, courseId);
	}
}