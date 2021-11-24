package com.companyabc.retail.services;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.companyabc.retail.domain.Client;
import com.companyabc.retail.domain.Transaction;
import com.companyabc.retail.model.RewardReportDTO;

public class RewardServiceImpl implements RewardService {

	private final BigDecimal FIFTY = BigDecimal.valueOf(50);
	private final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);
	
	private final TransactionService transactionService;
	
	public RewardServiceImpl(TransactionService transactionService) {
		super();
		this.transactionService = transactionService;
	}

	@Override
	public RewardReportDTO calculateByClient(final Client client, final LocalDate date) {
		
		List<Transaction> transactions = transactionService.findLastTransactionsByClientFrom(client, date);
		return getReportByClient(client, date, transactions);
	}

	private RewardReportDTO getReportByClient(final Client client, final LocalDate date, final List<Transaction> transactions) {
		Integer totalPoints = transactions.stream().map(t -> t.getAmount()).mapToInt(a -> calculatePoints(a)).sum();
		
		Map<Month, Integer> monthlyPoints = calculateMonthlyPoints(transactions, date);
		RewardReportDTO report = new RewardReportDTO(client.getLastName(), monthlyPoints, totalPoints);
		return report;
	}

	@Override
	public List<RewardReportDTO> calculateAll(final LocalDate date) {
		List<Transaction> transactions = transactionService.findLastTransactionsFrom(date);
		List<RewardReportDTO> reports = new ArrayList<>();
		transactions.stream().collect(Collectors.groupingBy(t -> t.getClient())).forEach((client, listTransac) -> {
			reports.add(getReportByClient(client, date, listTransac));
		});
		return reports;
	}
	
	
	private Map<Month, Integer> calculateMonthlyPoints(final List<Transaction> transactions, final LocalDate date) {
		Map<Month, Integer> monthlyPoints = initializeLastMonths(date);
		transactions.stream().forEach(t -> {
			Month currentMonth = t.getDateTime().getMonth();
			monthlyPoints.put(currentMonth, monthlyPoints.get(currentMonth) + calculatePoints(t.getAmount()));
		});
		return monthlyPoints;
	}

	private Map<Month, Integer> initializeLastMonths(final LocalDate date) {
		Map<Month, Integer> monthlyPoints = new HashMap<>();
		monthlyPoints.put(date.getMonth(), 0);
		monthlyPoints.put(date.getMonth().minus(1), 0);
		monthlyPoints.put(date.getMonth().minus(2), 0);
		return monthlyPoints;
	}

	private Integer calculatePoints(final BigDecimal amount) {
		BigDecimal points = BigDecimal.ZERO;
		
		if( amount.compareTo(ONE_HUNDRED) == 1) {
			points = amount.subtract(ONE_HUNDRED).multiply(BigDecimal.valueOf(2)).add(FIFTY);
		} else if (amount.compareTo(FIFTY) == 1) {
			points = amount.subtract(FIFTY);
		}
				
		return points.round(new MathContext(0)).toBigInteger().intValueExact();
	}
}
