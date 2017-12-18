package com.cg.laps.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cg.laps.dto.CustomerDetails;
import com.cg.laps.dto.LoanApplication;
import com.cg.laps.dto.LoanProgramsOffered;
import com.cg.laps.exception.LoanException;
import com.cg.laps.util.DBUtil;

public class LoanDAOImpl implements LoanDAO {
	Connection con;

	public LoanDAOImpl() {
		con = DBUtil.getConnection();
	}

	
	public int generateId() throws LoanException
	{
		int id=0;
		String query="SELECT seq_applicationId.nextval from dual";
		
		Statement stmt;
		try {
			stmt = con.createStatement();
			ResultSet result= stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return id;
		
	}
	@Override
	public int addCustomerDetails(CustomerDetails customerDTO) throws LoanException {
		
		String sql = "INSERT into users VALUES(?,?,?,?)";
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		boolean flag = false;
		int applicationId = generateId();
		try {
			preparedStatement = con.prepareStatement(sql);
			con.setAutoCommit(false);
			preparedStatement.setInt(1, customerDTO.getApplicationId());
			preparedStatement.setString(2, customerDTO.getApplicantName());
			preparedStatement.setDate(3, customerDTO.getDob());
			preparedStatement.setString(4, customerDTO.getMaritalStatus());
			preparedStatement.setLong(5, customerDTO.getPhoneNumber());
			preparedStatement.setLong(6, customerDTO.getMobileNumber());
			preparedStatement.setInt(7, customerDTO.getCountOfDepandants());
			preparedStatement.setString(8, customerDTO.getEmailId());
			result = preparedStatement.executeQuery();
			if (result != null) {
				flag = true;
			}
			con.commit();
		} catch (SQLException e) {
			throw new LoanException("Unable to insert customer details");
		}
		return applicationId;
	}

	@Override
	public List<LoanProgramsOffered> viewLoanProgramsOffered() throws LoanException {
		List<LoanProgramsOffered> list = new ArrayList<LoanProgramsOffered>();
		Connection connection = null;
		connection = DBUtil.getConnection();
		String query = "select * from LoanProgramsOffered";
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				LoanProgramsOffered bean = new LoanProgramsOffered();
				bean.setProgramName(resultSet.getString("programName"));
				bean.setDescription(resultSet.getString("description"));
				bean.setType(resultSet.getString("types"));
				bean.setDurationInYears(resultSet.getInt("durationInYears"));
				bean.setMinLoanAmount(resultSet.getFloat("minLoanAmount"));
				bean.setMaxLoanAmount(resultSet.getFloat("maxLoanAmount"));
				bean.setRateOfInterest(resultSet.getFloat("rateOfInterest"));
				bean.setProofsRequired(resultSet.getString("proofs_Required"));
				list.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new LoanException("Unable to display Loan Programs Offered");
		}

		return list;
	}

	@Override
	public LoanApplication viewLoanById(int applicationId) throws LoanException {
		LoanApplication loan = new LoanApplication();
		Connection connection = DBUtil.getConnection();
		String query = "select * from loanapplication where applicationId=(?)";
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement(query);
			preparedStatement.setInt(1, applicationId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				loan.setApplicationId(resultSet.getInt(1));
				loan.setApplicationDate(resultSet.getDate(2));
				loan.setLoanProgram(resultSet.getString(3));
				loan.setAmountOfLoan(resultSet.getFloat(4));
				loan.setAddressOfProperty(resultSet.getString(5));
				loan.setAnnualFamilyIncome(resultSet.getFloat(6));
				loan.setDocumentProofs(resultSet.getString(7));
				loan.setGuaranteeCover(resultSet.getString(8));
				loan.setMarketValueOfGuarantee(resultSet.getFloat(9));
				loan.setStatus(resultSet.getString(10));
				loan.setDateOfInterview(resultSet.getDate(11));
			}
			System.out.println(loan);
		} catch (SQLException e) {
			throw new LoanException(
					"Unable to view.Application Id doesnt exist");
		}
		return loan;
	}

}
