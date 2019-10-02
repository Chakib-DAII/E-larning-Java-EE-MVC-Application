/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.metier;

import beans_for_entities.EtudiantFacadeLocal;
import entities.Etudiant;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 *
 * @author Chakib
 */
public class Signin extends HttpServlet {
        

    @EJB
    private EtudiantFacadeLocal etudiantFacade;

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
        try{
        String prenom = request.getParameter("txtfamilyname");
        String nom = request.getParameter("txtname");
        String mail = request.getParameter("txtLogin");
        String password = request.getParameter("txtPassword");
        
        Etudiant etu = new Etudiant(mail,password,nom,prenom);
            etudiantFacade.create(etu);
             HttpSession session = request.getSession();
             System.out.println("************\n"+session.getSessionContext()+"\n************\n");
        for (Enumeration e = session.getAttributeNames(); e.hasMoreElements();) {
            String attribName = (String) e.nextElement();
            if (attribName.startsWith("org.apache.shiro")) {
                System.out.println("************\nsession Attribute deleted : "+attribName+"\n************\n");
                session.removeAttribute(attribName);
                }
        }
            System.out.println("************\ndelai de session = "+session.getMaxInactiveInterval()+"\n************\n");
            session.setAttribute("user",etu);
            response.sendRedirect("/E-Learning-war/index.jsp");     
                }
       catch(Exception e)
           {System.out.println("err "+e.toString());
           response.sendRedirect("/E-Learning-war/error.jsp");   
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
        processRequest(request, response);
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
