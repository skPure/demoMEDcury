package com.MEDcury.demoMEDcury;

import com.MEDcury.demoMEDcury.controller.AppointmentController;
import com.MEDcury.demoMEDcury.request.BookAppointment;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AppointmentController.class)
class DemoMEDcuryApplicationTests {
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void getDoctorScheduleShouldReturnScheduleList() throws Exception{
		this.mockMvc
				.perform(get("/appointment/doctorSchedule?startDate=2021-10-20&endDate=2021-10-30"))
				.andDo(print())
				.andExpect(status().is2xxSuccessful())
				.andExpect(jsonPath("$.dateSearch").value("Schedule from 2021-10-20 to 2021-10-30"));
	}

	@Test
	public void bookingAppointmentShouldReturnMessage() throws Exception{
		BookAppointment bookAppointment = new BookAppointment();
		bookAppointment.setPinCode("333333");
		bookAppointment.setTimeTableId(2);
		bookAppointment.setDate("2021-10-12");
		bookAppointment.setTimeSlot(13.5);
		bookAppointment.setPhone("009101910");
		bookAppointment.setDoctorId("002");

		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(bookAppointment);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.post("/appointment/appointmentSchedule")
				.content(jsonInString)
				.contentType(MediaType.APPLICATION_JSON);

		this.mockMvc
				.perform(builder)
				.andDo(print())
				.andExpect(status().is2xxSuccessful())
				.andExpect(content().string("Your booking success."));
	}

	@Test
	public void cancelAppointmentShouldReturnMessage() throws Exception{
		this.mockMvc
				.perform(delete("/appointment/appointmentSchedule/2"))
				.andDo(print())
				.andExpect(status().is2xxSuccessful())
				.andExpect(content().string("Your booking canceled."));
	}

	@Test
	public void getDoctorAppointmentScheduleShouldReturnName() throws Exception{
		this.mockMvc
				.perform(get("/appointment/doctorAppointmentSchedule/001"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.doctorName").value("หมอ ก"));
	}
}
