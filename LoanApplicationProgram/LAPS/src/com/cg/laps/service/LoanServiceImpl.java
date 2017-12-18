package com.cg.laps.service;

import java.util.List;

import com.cg.laps.dao.LoanDAO;
import com.cg.laps.dao.LoanDAOImpl;
import com.cg.laps.dto.CustomerDetails;
import com.cg.laps.dto.LoanApplication;
import com.cg.laps.dto.LoanProgramsOffered;
import com.cg.laps.exception.LoanException;

public class LoanServiceImpl implements LoanService{
	LoanDAO Dao=null;

	public LoanServiceImpl() {
	Dao=new LoanDAOImpl();
	}

	@Override
	public int addCustomerDetails(CustomerDetails customerDTO)
			throws LoanException {
		return Dao.addCustomerDetails(customerDTO);
	}

	@Override
	public List<LoanProgramsOffered> viewLoanProgramsOffered() throws LoanException {
		 
		return Dao.viewLoanProgramsOffered();
	}

	@Override
	public LoanApplication viewLoanById(int applicationId) throws LoanException {
	 
		return Dao.viewLoanById(applicationId);
	}

}
