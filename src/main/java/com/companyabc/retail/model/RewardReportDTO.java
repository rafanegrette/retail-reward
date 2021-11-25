package com.companyabc.retail.model;

import java.time.Month;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RewardReportDTO {
	@NonNull
	private String clientName;
	@NonNull
	private Map<Month, Integer> months;
	@NonNull
	private Integer total;
}
