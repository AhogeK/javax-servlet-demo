package com.ahogek.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author AhogeK ahogek@gmail.com
 * @since 2023-12-06 12:33:01
 */
@WebServlet(name = "FormServlet", urlPatterns = "/calculateServlet")
public class FormServlet extends HttpServlet {

    private static final String WEB_INF_JSP_INDEX_JSP = "/WEB-INF/jsp/index.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(WEB_INF_JSP_INDEX_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String height = req.getParameter("height");
        String weight = req.getParameter("weight");

        try {
            double bmi = calculateBMI(Double.parseDouble(weight), Double.parseDouble(height));

            req.setAttribute("bmi", bmi);
            resp.setHeader("Test", "Success");
            resp.setHeader("BMI", String.valueOf(bmi));

            req.getRequestDispatcher(WEB_INF_JSP_INDEX_JSP).forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("errorMessage", "Invalid input.");
            req.getRequestDispatcher(WEB_INF_JSP_INDEX_JSP).forward(req, resp);
        }
    }

    private Double calculateBMI(Double weight, Double height) {
        return weight / (height * height);
    }
}
