/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.etudiant;

import beans_for_entities.EtudiantInscritCoursFacadeLocal;
import entities.Cours;
import entities.Etudiant;
import entities.EtudiantInscritCours;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
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
public class DeleteEtudiantCours extends HttpServlet {

    @EJB
    private EtudiantInscritCoursFacadeLocal etudiantInscritCoursFacade;

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
            HttpSession session = request.getSession();
            System.out.println("************\n"+session.getSessionContext()+"\n************\n");
            for (Enumeration e = session.getAttributeNames(); e.hasMoreElements();) {
            String attribName = (String) e.nextElement();
            if (attribName.startsWith("org.apache.shiro")) {
                System.out.println("************\nsession Attribute deleted : "+attribName+"\n************\n");
                session.removeAttribute(attribName);
            }
            }
            if(session.getAttribute("Courspref")!= null && session.getAttribute("user")!= null)
            { Cours courspref = (Cours)session.getAttribute("Courspref");
            int idcours =((Cours)session.getAttribute("Courspref")).getIdcours();
            String login =((Etudiant)session.getAttribute("user")).getLogin();
            //EtudiantInscritCours test = etudiantInscritCoursFacade.find(new EtudiantInscritCours( idcours,login));  
            //if(test == null)
                etudiantInscritCoursFacade.remove(new EtudiantInscritCours( idcours,login));
           
            List<Cours> coursUser = new ArrayList();
            
            List<EtudiantInscritCours> etCours=etudiantInscritCoursFacade.findAll();
            for(int i=0;i<etCours.size();i++)
            {/*if(etCours.get(i).getEtudiant().getLogin().equals(((Etudiant)session.getAttribute("user")).getLogin())
                        && etCours.get(i).getCours().equals(courspref))
                                 session.setAttribute("inscrit", -1);*/
            if(etCours.get(i).getEtudiant().getLogin().equals(((Etudiant)session.getAttribute("user")).getLogin()))
                 {coursUser.add(etCours.get(i).getCours());
                    System.out.println("************\ncours pref added to list: cours pref id  = "+etCours.get(i).getCours().getIdcours()+"\n************\n");
                }
            }
            session.setAttribute("listeCoursuser",coursUser);
            }
            response.sendRedirect("/E-Learning-war/detailpage.jsp"); 
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
