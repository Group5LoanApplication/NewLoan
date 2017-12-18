package com.cg.laps.dao;

import java.util.List;

import com.cg.laps.dto.CustomerDetails;
import com.cg.laps.dto.LoanApplication;
import com.cg.laps.dto.LoanProgramsOffered;
import com.cg.laps.exception.LoanException;

public interface LoanDAO {
	public int addCustomerDetails(CustomerDetails customerDTO) throws LoanException;

	public List<LoanProgramsOffered> viewLoanProgramsOffered() throws LoanException;

	public LoanApplication viewLoanById(int applicationId) throws LoanException;
}
