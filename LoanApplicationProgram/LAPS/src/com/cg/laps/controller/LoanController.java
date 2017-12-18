package com.cg.laps.controller;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cg.laps.dto.CustomerDetails;
import com.cg.laps.dto.LoanProgramsOffered;
import com.cg.laps.exception.LoanException;
import com.cg.laps.service.LoanService;
import com.cg.laps.service.LoanServiceImpl;

/**
 * Servlet implementation class LoanController
 */
@WebServlet(urlPatterns = { "/LoanCont1" , "/formAction", "/CustomerForm","/LoanForm"})
public class LoanController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	LoanService loanService;

	public LoanController() {
		super();
		loanService = new LoanServiceImpl();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = null;
		HttpSession session = request.getSession();
		String path = request.getServletPath();
		String url = "";
		System.out.println("idhar again");
		switch (path) {
		case "/LoanCont1":
			System.out.println("LoanController");

			List<LoanProgramsOffered> programlist;
			try {
				programlist = loanService.viewLoanProgramsOffered();
				request.setAttribute("program", programlist);
				dispatcher = request.getRequestDispatcher("LoanProgram.jsp");
				dispatcher.forward(request, response);
			} catch (LoanException e) {
				dispatcher = request.getRequestDispatcher("LoanError.jsp");
				request.setAttribute("exception", e);
				dispatcher.forward(request, response);
			}
			break;
		case "/formAction" : System.out.println("formAction");
			
				String action=request.getParameter("action");
			 	session.setAttribute("action1", action); 
				dispatcher=request.getRequestDispatcher("CustomerForm.jsp");
				dispatcher.forward(request, response);
				break;
				
		case "/CustomerForm" :
			System.out.println("CustomerForm");
			CustomerDetails customer= new CustomerDetails();
			
			 
			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String date= request.getParameter("dob");
			String status= request.getParameter("typeStatus.value");
			long mobNo= Long.parseLong(request.getParameter("mobileNumber"));
			long landline= Long.parseLong(request.getParameter("landlineNumber"));
			int dependents = Integer.parseInt(request.getParameter("dependents"));
			String email= request.getParameter("mail");
			
			java.sql.Date dobDate= null;
			try {
				dobDate = (Date) new SimpleDateFormat("dd/mm/yyyy").parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			customer.setApplicantName(firstName+ " "+lastName);
			customer.setDob(dobDate);
			customer.setMaritalStatus(status);
			customer.setMobileNumber(mobNo);
			customer.setPhoneNumber(landline);
			customer.setCountOfDepandants(dependents);
			customer.setEmailId(email);
			
			try {
				int applicationId=loanService.addCustomerDetails(customer);
				customer.setApplicationId(applicationId);
				session.setAttribute("CustomerBean", customer);
			} catch (LoanException e) {
				dispatcher = request.getRequestDispatcher("LoanError.jsp");
				request.setAttribute("exception", e);
				dispatcher.forward(request, response);
			}
			dispatcher = request.getRequestDispatcher("LoanForm.jsp");
			dispatcher.forward(request, response);
			
			
			break;
				
		default:
			break;

		}

	}
}
