package be.tvh.bootcamp.schedule.web;

import be.tvh.bootcamp.schedule.model.EmployeeDto;
import be.tvh.bootcamp.schedule.model.EmployeeType;
import be.tvh.bootcamp.schedule.persistence.Course;
import be.tvh.bootcamp.schedule.persistence.CourseRepository;
import be.tvh.bootcamp.schedule.persistence.Employee;
import be.tvh.bootcamp.schedule.service.CourseConverter;
import be.tvh.bootcamp.schedule.service.CourseService;
import be.tvh.bootcamp.schedule.service.EmployeeConverter;
import org.assertj.core.util.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.ManualRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = {
	CourseController.class, CourseService.class, CourseRepository.class, CourseConverter.class,
	EmployeeConverter.class })
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
public class CourseControllerDocumentationTest {

	@Autowired
	private WebApplicationContext context;

	@MockBean
	private CourseRepository courseRepository;

	@Autowired
	private ManualRestDocumentation restDocumentation;

	private MockMvc mockMvc;

	private RestDocumentationResultHandler document;

	@Before
	public void setup() {
		this.document = document("{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()));
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
			.apply(documentationConfiguration(this.restDocumentation))
			.alwaysDo(this.document)
			.build();
	}

	@Test
	public void retrieveCourseWithIdShouldReturnOneCourse() throws Exception {
		Course course = Course.builder()
			.id(1L)
			.startDate(LocalDate.now())
			.endDate(LocalDate.now())
			.topic("Rick and Morty Motherfucker!")
			.teacher(Employee.builder()
				.id(2L)
				.name("Rick")
				.type(EmployeeType.INTERNAL)
				.department("HR")
				.startDate(LocalDate.now())
				.build())
			.students(Sets.newHashSet())
			.build();

		when(this.courseRepository.findOneById(eq(Long.valueOf(1))))
			.thenReturn(Optional.of(course));

		this.mockMvc.perform(get("/api/course/{id}", "1"))
			.andExpect(status().isOk())
			.andDo(this.document.document(
				responseFields(
					fieldWithPath("id").description("The ID of the course"),
					fieldWithPath("startDate").description("Start date of the course"),
					fieldWithPath("endDate").description("End date of the course"),
					fieldWithPath("topic").description("The topic"),
					fieldWithPath("teacher").description("Employee giving the course").type(EmployeeDto.class),
					fieldWithPath("students").description("Employees taking the course").type(List.class)
				)
			));
	}
}
