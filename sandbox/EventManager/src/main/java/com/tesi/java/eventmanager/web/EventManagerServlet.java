/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesi.java.eventmanager.web;

import com.tesi.java.eventmanager.EventManager;
import com.tesi.java.eventmanager.domain.Event;
import com.tesi.java.eventmanager.util.HibernateUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.ServerException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author mikerocha
 */
public class EventManagerServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet EventManagerServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EventManagerServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
        
        try {
            // Begin unit of work
            HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().begin();
            
            // Process request and render page
            
            // Write HTML header
            PrintWriter out = response.getWriter();
            out.println("<html><head><title>Event Manager</title></head><body>");
            if ("store".equals(request.getParameter("action"))) {
                String eventTitle = request.getParameter("eventTitle");
                String eventDate = request.getParameter("eventDate");
                
                if ("".equals(eventTitle) || "".equals(eventDate)) {
                    out.println("<b><i>Please enter event title and date.</i></b>");
                } else {
                    createAndStoreEvent(eventTitle, dateFormatter.parse(eventDate));
                    out.println("<b><i>Added event.</i></b>");
                }
                
                // Print page
                printEventForm(out);
                listEvents(out, dateFormatter);
                
                // Write HTML footer
                out.println("</body></html>");
                out.flush();
                out.close();
            }
            
            // End unit of work
            HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        } catch (Exception e) {
            HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().rollback();
            if (ServletException.class.isInstance(e)) {
                throw(ServletException) e;
            } else {
                throw new ServletException(e);
            }
        }
    }
    
    private void printEventForm(PrintWriter out) {
        out.println("<h2>Add new event:</h2>");
        out.println("<form>");
        out.println("Title: <input name='eventTitle' length='50'/><br/>");
        out.println("Date (e.g. 24.12.2009): <input name='eventDate' length='10'/><br/>");
        out.println("<input type='submit' name='action' value='store'/>");
        out.println("</form>");
    }
    
    private void listEvents(PrintWriter out, SimpleDateFormat dateFormatter) {
        
        List result = HibernateUtil.getSessionFactory().getCurrentSession()
                                   .createCriteria(Event.class).list();
        if (result.size() > 0) {
            out.println("<h2>Events in database:</h2>");
            out.println("<table border=\"1\">");
            out.println("<tr>");
            out.println("<th>Event Title</th>");
            out.println("<th>Event Date</th>");
            out.println("</tr>");
            Iterator it = result.iterator();
            while (it.hasNext()) {
                Event event = (Event) it.next();
                out.println("<tr>");
                out.println("<td>" + event.getTitle() + "</td>");
                out.println("<td>" + dateFormatter.format(event.getDate()) + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
        }
    }

    private void createAndStoreEvent(String title, Date date) {
        Event theEvent = new Event();
        theEvent.setTitle(title);
        theEvent.setDate(date);
        
        HibernateUtil.getSessionFactory().getCurrentSession().save(theEvent);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
