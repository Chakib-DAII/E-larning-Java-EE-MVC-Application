/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.cours;

import beans_for_entities.CoursFacadeLocal;
import entities.Cours;
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
import org.jboss.weld.bean.proxy.ProxyInstantiator;

       
/**
 *
 * @author Chakib
 */
@WebServlet(name = "GetCours", urlPatterns = {"/GetCours"})
public class GetCours extends HttpServlet {

    @EJB
    private CoursFacadeLocal coursFacade;
  
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
        List<Cours> cours =coursFacade.findAll();
        
        
        HttpSession session=request.getSession();
        session.setAttribute("listeetudiants", cours);
        
        response.sendRedirect("/E-Learning-war/index.jsp"); 
        try (PrintWriter out = response.getWriter()) {
        out.println("<table border=\"1\" cellpadding=\"5\"><tr>"
                 + "<th>Id</th>"
                 + "<th>Nom</th>"
                 + "<th>Description</th>"
                 + "<th>Image url</th>"
                 + "<th>Ressource url</th>"
                 + "<th>Theme</th>"
                 + "</tr>");
        for(int i=0;i<cours.size();i++){
        out.println("<tr>"
        +"<td>"+cours.get(i).getIdcours()+"</td>"
        +"<td>"+cours.get(i).getNom()+"</td>"
        +"<td>"+cours.get(i).getDescription()+"</td>"
        +"<td>"+cours.get(i).getImgurl()+"</td>"
        +"<td>"+cours.get(i).getRessourceurl()+"</td>"
        +"<td>"+cours.get(i).getTheme()+"</td>"        
        +
        "</tr>");
        }
     out.println("</table>");
            out.println("</body>");
            out.println("</html>");
            
         
                 /*try{coursFacade.wait(1000);
                response.sendRedirect("/E-Learning-war/index.jsp");
                }catch(Exception e)
                {out.println(e.getMessage());
                Logger.getLogger(GetCours.class.getName()).log(Level.SEVERE, null, e);
                }*/
        out.println("<a href=\"/E-Learning-war/index.jsp\">ajouter autres cours</a></br>");
        
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
