/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Mike
 */
@WebServlet(name = "ControllerServlet",
        loadOnStartup = 1,
        urlPatterns = {"/category",
            "/addToCart",
            "/viewCart",
            "/updateCart",
            "/checkout",
            "/purchase",
            "/chooseLanguage"})
public class ControllerServlet extends HttpServlet {

 
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
        
        String userPath = request.getServletPath();
        
        // if category page is requested
        if (userPath.equals("/category")) {
            // todo: Implement category request
            
        // if cart page is requested
        } else if (userPath.equals("/viewCart")) {
            // todo: Implement cart page request
            
            userPath = "/cart";
                    
        // if checkout page is requested
        } else if (userPath.equals("/checkout")) {
            // todo: Implement checkout page request
            
        // if user switches language
        } else if (userPath.equals("chooseLanguage")) {
            // todo: Implement language request
            
        }
        
        // use RequestDispatcher to forward request internally
        String url = "/WEB-INF/view" + userPath + ".jsp";
        
        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
        
        String userPath = request.getServletPath();
        
        // if addToCart action is called
        if (userPath.equals("/addToCart")) {
            // todo: Implement add product to cart action
            
        // if updateCart action is called
        } else if (userPath.equals("/updateCart")) {
            // todo: implement update cart action
            
            userPath = "/cart";
        // if purchse action is called
        } else if (userPath.equals("/purchase")) {
            // todo: Implement purchase action
            
            userPath = "/confirmation";
        }
        
        // use RequestDispatcher to forward request internally
        String url = "/WEB-INF/view" + userPath + ".jsp";
        
        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "This servlet controls the AppableBean web app.";
    }// </editor-fold>

}
