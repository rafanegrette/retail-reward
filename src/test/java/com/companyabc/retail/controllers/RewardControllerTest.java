package com.companyabc.retail.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.companyabc.retail.model.RewardReportDTO;
import com.companyabc.retail.services.RewardService;
import com.companyabc.retail.services.exceptions.ClientNotExistException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

@WebMvcTest(RewardController.class)
class RewardControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	RewardService rewardService;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testFindAllByDate() throws Exception {
		//GIVEN
		Map<Month, Integer> months1 = new HashMap<>();
		months1.put(Month.NOVEMBER, 150);
		RewardReportDTO report1 = new RewardReportDTO("Simpson", months1, 150);
		
		Map<Month, Integer> months2 = new HashMap<>();
		months2.put(Month.OCTOBER, 150);
		RewardReportDTO report2 = new RewardReportDTO("Osborne", months2, 150);
		List<RewardReportDTO> reports = new ArrayList<>();
		reports.add(report1);
		reports.add(report2);
		
		// WHEN 
		when(rewardService.calculateAll(LocalDate.of(2011, 11, 30))).thenReturn(reports);
		MvcResult mvcResult = this.mockMvc.perform(get("/rewards").param("date", "2011-11-30")).andExpect(status().isOk()).andReturn();
		
		// THEN
		String responseStr = mvcResult.getResponse().getContentAsString();
		ArrayNode reportsReturned = new ObjectMapper().readValue(responseStr, ArrayNode.class);
		
		assertEquals(2, reportsReturned.size());
	}
	
	@Test
	void testFindAllByDateNoContent() throws Exception {
		// GIVEN
		List<RewardReportDTO> reports = new ArrayList<>();
		
		// WHEN
		when(rewardService.calculateAll(LocalDate.of(2011, 11, 30))).thenReturn(reports);
		//THEN
		this.mockMvc.perform(get("/rewards").param("date", "2011-11-30")).andExpect(status().isNoContent());
	}

	@Test
	void testFindByClient() throws Exception {
		// GIVEN
		Map<Month, Integer> months = new HashMap<>();
		months.put(Month.NOVEMBER, 150);
		RewardReportDTO report = new RewardReportDTO("Simpson", months, 150);
		// WHEN
		when(rewardService.calculateByClient(1L, LocalDate.of(2011, 11, 30))).thenReturn(report);
		MvcResult mvcResult = this.mockMvc.perform(
				get("/rewards/clients/{idClient}", "1")
				.param("date", "2011-11-30")
				).andExpect(status().isOk()).andReturn();
		// THEN
		String responseStr = mvcResult.getResponse().getContentAsString();
		RewardReportDTO reportReturned = new ObjectMapper().readValue(responseStr, RewardReportDTO.class);
		assertEquals("Simpson", reportReturned.getClientName());
	}
	
	@Test
	void testFindByClientNotFound() throws Exception {
		// GIVEN
		
		// WHEN
		
		doThrow(new ClientNotExistException()).when(rewardService).calculateByClient(1L, LocalDate.of(2011, 11, 30));
		// THEN 
		this.mockMvc.perform(
				get("/rewards/clients/{idClient}", "1")
				.param("date", "2011-11-30")
				).andExpect(status().isNoContent());
	}

}
