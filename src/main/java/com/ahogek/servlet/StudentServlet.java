package com.ahogek.servlet;

import com.ahogek.service.StudentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author AhogeK ahogek@gmail.com
 * @since 2023-12-10 15:52:48
 */
@WebServlet(name = "StudentServlet", urlPatterns = "/studentServlet")
public class StudentServlet extends HttpServlet {

    private final transient StudentService studentService = new StudentService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String studentID = req.getParameter("id");
        if (studentID != null) {
            int id = Integer.parseInt(studentID);
            studentService.getStudent(id).ifPresent(s ->
                    req.setAttribute("studentRecord", s));
        }

        var dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/student-record.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }
}
