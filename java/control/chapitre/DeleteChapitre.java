/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.chapitre;

import beans_for_entities.ChapitreFacadeLocal;
import entities.Chapitre;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Chakib
 */
@WebServlet(name = "DeleteChapitre", urlPatterns = {"/DeleteChapitre"})
public class DeleteChapitre extends HttpServlet {

    @EJB
    private ChapitreFacadeLocal chapitreFacade;

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
            int id = Integer.parseInt(request.getParameter("id"));
        List<Chapitre> chapitress = chapitreFacade.findAll();
        
        Chapitre ch = chapitreFacade.find(chapitress.get(id).getIdchap());
        chapitreFacade.remove(ch);
        
         List<Chapitre> chapitres = chapitreFacade.findAll();
            
         HttpSession session=request.getSession();
         session.setAttribute("listeChapitres", chapitres);
        
        response.sendRedirect("/E-Learning-war/gestionCours.jsp");
      
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
