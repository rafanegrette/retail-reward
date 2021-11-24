package com.companyabc.retail.model;

import java.time.Month;
import java.util.Map;

import lombok.Data;
import lombok.NonNull;

@Data
public class RewardReportDTO {
	@NonNull
	private String clientName;
	@NonNull
	private Map<Month, Integer> months;
	@NonNull
	private Integer total;
}
