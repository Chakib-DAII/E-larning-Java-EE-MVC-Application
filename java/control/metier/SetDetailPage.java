/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.metier;

import beans_for_entities.ChapitreFacadeLocal;
import beans_for_entities.CoursFacadeLocal;
import beans_for_entities.EtudiantInscritCoursFacadeLocal;
import entities.Chapitre;
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
public class SetDetailPage extends HttpServlet {

    @EJB
    private CoursFacadeLocal coursFacade;

    @EJB
    private EtudiantInscritCoursFacadeLocal etudiantInscritCoursFacade;

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
            
            int id;
             HttpSession session = request.getSession();
             System.out.println("************\n"+session.getSessionContext()+"\n************\n");
            for (Enumeration e = session.getAttributeNames(); e.hasMoreElements();) {
            String attribName = (String) e.nextElement();
            if (attribName.startsWith("org.apache.shiro")) {
                System.out.println("************\nsession Attribute deleted : "+attribName+"\n************\n");
                session.removeAttribute(attribName);
            }
            }
            if(request.getParameter("id").isEmpty())
            { id = (int)session.getAttribute("id");
                System.out.println("************\nsession = "+id+"\n************\n");}
            else {
                id = Integer.parseInt(request.getParameter("id"));
                session.setAttribute("id", id);
                System.out.println("************\nrequest = "+id+"\n************\n");
             }
            
         //a fixer : selon la page prec 
         List<Cours> cours = coursFacade.findAll();
         //session.getAttribute("listeCoursuser");
         Cours courspref = coursFacade.find(id);
         
         
         if(courspref != null)
        {
         String username = System.getProperty("user.name"); 
        System.out.println("\n\n\nusername : "+username+"\n\n\n");  
         String savePath ="C:/Users/"+username+"/Documents/NetBeansProjects/E-Learning/E-Learning-war/web/ressources/pdf/"+courspref.getNom();
         String savePdf ="C:/Users/"+username+"/Documents/NetBeansProjects/E-Learning/E-Learning-war/web/ressources/pdf";
             
        // creates the save directory if it does not exists
               File fileSaveDirPdf = new File(savePdf);
                    if (!fileSaveDirPdf.exists()) {
                        fileSaveDirPdf.mkdir();
                        }
         
        File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }
        
        
            File file =new File(courspref.getRessourceurl());
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
        
            List<Chapitre> chapitres = chapitreFacade.findAll();
            List<Chapitre> chapitresCours = new ArrayList();
            //chapitres de ce cours 
            for(int i=0;i<chapitres.size();i++)
            if((chapitres.get(i).getIdcours().getIdcours()).equals(courspref.getIdcours()))
                       chapitresCours.add(chapitres.get(i));
            
           
            session.setAttribute("chapitresCours", chapitresCours);
        
            for(int i=0;i<chapitresCours.size();i++)
            {    
                 String savePathchap ="C:/Users/"+username+"/Documents/NetBeansProjects/E-Learning/E-Learning-war/web/ressources/pdf/"+courspref.getNom()+File.separator+chapitresCours.get(i).getNom();
            // creates the save directory if it does not exists
            File fileSaveDirchap = new File(savePathchap);
                    if (!fileSaveDirchap.exists()) {
                        fileSaveDirchap.mkdir();
                        }
            File filechap =new File(chapitresCours.get(i).getContenuurl());
            // refines the fileName in case it is an absolute path
            String fileNamechap = filechap.getName();
            //part.write(savePath +File.separator + fileName);
                 OutputStream outchap = null;
                outchap = new FileOutputStream(new File(savePathchap +File.separator + fileNamechap));
                FileInputStream filecontentchap = new FileInputStream(filechap);

                int readchap = 0;
                final byte[] byteschap = new byte[1024];

                while ((readchap = filecontentchap.read(byteschap)) != -1) {
                    outchap.write(byteschap, 0, readchap);
                }
            outchap.close();
            System.out.println("done");
            System.out.println("Filechap Path: " + savePathchap+File.separator + fileNamechap);
        }
        }
            System.out.println("************\ncours pref id  = "+courspref.getIdcours()+"\n************\n");
            List<Cours> coursUser = new ArrayList();
            
            List<EtudiantInscritCours> etCours=etudiantInscritCoursFacade.findAll();
            for(int i=0;i<etCours.size();i++)
            {
            if(etCours.get(i).getEtudiant().getLogin().equals(((Etudiant)session.getAttribute("user")).getLogin()))
                 {  coursUser.add(etCours.get(i).getCours());
                    System.out.println("************\ncours pref added to list: cours pref id  = "+etCours.get(i).getCours().getIdcours()+"\n************\n");
                }
            }
            session.setAttribute("listeCoursuser",coursUser);
            session.setAttribute("Courspref", courspref);
            System.out.println("************\ndelai de session = "+session.getMaxInactiveInterval()+"\n************\n");
             
        response.sendRedirect("/E-Learning-war/detailpage.jsp"); 
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
