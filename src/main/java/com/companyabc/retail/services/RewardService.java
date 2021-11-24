package com.companyabc.retail.services;

import java.time.LocalDate;
import java.util.List;

import com.companyabc.retail.domain.Client;
import com.companyabc.retail.model.RewardReportDTO;

public interface RewardService {

	RewardReportDTO calculateByClient(Client client, LocalDate date);
	
	List<RewardReportDTO> calculateAll(LocalDate date);
}
