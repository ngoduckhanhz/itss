package app.trainticket.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.trainticket.beans.TrainException;
import app.trainticket.constant.ResponseCode;
import app.trainticket.constant.UserRole;
import app.trainticket.service.TrainService;
import app.trainticket.service.impl.TrainServiceImpl;
import app.trainticket.utility.TrainUtil;

@WebServlet("/admincancletrain")
public class AdminCancleTrain extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TrainService trainService = new TrainServiceImpl();

	/**
	 * 
	 * @param req
	 * @param res
	 * @throws IOException
	 * @throws ServletException
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		res.setContentType("text/html");
		PrintWriter pw = res.getWriter();
		TrainUtil.validateUserAuthorization(req, UserRole.ADMIN);
		try {
			String trainNo = req.getParameter("trainno");
			String message = trainService.deleteTrainById(trainNo);
			if (ResponseCode.SUCCESS.toString().equalsIgnoreCase(message)) {
				RequestDispatcher rd = req.getRequestDispatcher("CancleTrain.html");
				rd.include(req, res);
				pw.println("<div class='main'><p1 class='menu'>Train number " + trainNo
						+ " has been Deleted Successfully.</p1></div>");
			} else {
				RequestDispatcher rd = req.getRequestDispatcher("CancleTrain.html");
				rd.include(req, res);
				pw.println("<div class='tab'><p1 class='menu'>Train No." + trainNo + " không có sẵn !</p1></div>");
			}
		} catch (Exception e) {
			throw new TrainException(422, this.getClass().getName() + "_FAILED", e.getMessage());
		}

	}

}
