/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.metier;

import beans_for_entities.EtudiantInscritCoursFacadeLocal;
import entities.Cours;
import entities.Etudiant;
import entities.EtudiantInscritCours;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
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
public class SetMesCoursPage extends HttpServlet {

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
       try{List<EtudiantInscritCours> etCours=etudiantInscritCoursFacade.findAll();
        
        List<Cours> cours = new ArrayList();
        
         HttpSession session = request.getSession();
         System.out.println("************\n"+session.getSessionContext()+"\n************\n");
        for (Enumeration e = session.getAttributeNames(); e.hasMoreElements();) {
            String attribName = (String) e.nextElement();
            if (attribName.startsWith("org.apache.shiro")) {
                System.out.println("************\nsession Attribute deleted : "+attribName+"\n************\n");
                session.removeAttribute(attribName);
            }
        }
        for(int i=0;i<etCours.size();i++)
            if(etCours.get(i).getEtudiant().getLogin().equals(((Etudiant)session.getAttribute("user")).getLogin()))
            {cours.add(etCours.get(i).getCours());
            System.out.println("************\ncours pref added : cours pref id  = "+etCours.get(i).getCours().getIdcours()+"\n************\n");
            }  
        // gets absolute path of the web application
        //String appPath = request.getServletContext().getRealPath("");
        // constructs path of the directory to save uploaded file
        String username = System.getProperty("user.name"); 
        System.out.println("\n\n\nusername : "+username+"\n\n\n"); 
        String savePath ="C:/Users/"+username+"/Documents/NetBeansProjects/E-Learning/E-Learning-war/web/ressources/images";
         
        // creates the save directory if it does not exists
        File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }
        for(int i=0;i<cours.size();i++){
            File file =new File(cours.get(i).getImgurl());
            // refines the fileName in case it is an absolute path
            String fileName = file.getName();
            //part.write(savePath +File.separator + fileName);
                 OutputStream out = null;
                out = new FileOutputStream(new File(savePath +File.separator + fileName));
                FileInputStream filecontent = new FileInputStream(file);

                int read = 0;
                final byte[] bytes = new byte[1024];

                while ((read = filecontent.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
            out.close();
            System.out.println("done");
            System.out.println("File Path: " + savePath+File.separator + fileName);
        }
            
        
        session.setAttribute("listeCoursuser", cours);
        System.out.println("************\ndelai de session = "+session.getMaxInactiveInterval()+"\n************\n");
        response.sendRedirect("/E-Learning-war/mesCours.jsp"); 
       }catch(Exception e)
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
