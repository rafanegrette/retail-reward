package com.companyabc.retail.controllers;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.companyabc.retail.model.RewardReportDTO;
import com.companyabc.retail.services.RewardService;
import com.companyabc.retail.services.exceptions.ClientNotExistException;

@RestController
@RequestMapping("rewards")
public class RewardController {

	private final Logger LOGGER = LoggerFactory.getLogger(RewardController.class);
	private final RewardService rewardService;
	
	public RewardController(RewardService rewardService) {
		super();
		this.rewardService = rewardService;
	}

	@GetMapping
	public ResponseEntity<List<RewardReportDTO>> findAllByDate(
			@RequestParam("date")
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
			LocalDate date) {
		LOGGER.info("Find RewardReport for all clients.");
		List<RewardReportDTO> reports = rewardService.calculateAll(date);
		if (reports.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<> (reports, HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param idClient
	 * @param date: The most common ISO Date Format yyyy-MM-dd — for example, "2000-10-31".
	 * @return report of reward points
	 */
	@GetMapping(value = "/clients/{idClient}")
	public ResponseEntity<RewardReportDTO> findByClient(
			@PathVariable("idClient") Long idClient, 
			@RequestParam("date") 
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
			LocalDate date) {
		try {
			LOGGER.info("Find RewardReport for client: " + idClient);
			
			return new ResponseEntity<> (rewardService.calculateByClient(idClient, date), HttpStatus.OK);
		} catch (ClientNotExistException cEx) {
			LOGGER.error("findByClient ", cEx);
			return new ResponseEntity<> (HttpStatus.NO_CONTENT);
		}
	}
	
	
	
}
