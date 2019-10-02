/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.chapitre;

import beans_for_entities.ChapitreFacadeLocal;
import beans_for_entities.CoursFacadeLocal;
import entities.Chapitre;
import entities.Cours;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author Chakib
 */
@WebServlet(name = "ModifyChapitre", urlPatterns = {"/ModifyChapitre"})
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
                 maxFileSize=1024*1024*10,      // 10MB
                 maxRequestSize=1024*1024*50)   // 50MB
public class ModifyChapitre extends HttpServlet {

    @EJB
    private CoursFacadeLocal coursFacade;

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
            HttpSession session = request.getSession();
            List<Cours> cours1 =(List<Cours>)session.getAttribute("listeCours");
            List<Chapitre> chapitre1 = (List<Chapitre>)session.getAttribute("listeChapitres");
            
            int idc = Integer.parseInt(request.getParameter("idc"));
            int id = Integer.parseInt(request.getParameter("id"));
            String nom =request.getParameter("nom");
            String username = System.getProperty("user.name"); 
        System.out.println("\n\n\nusername : "+username+"\n\n\n");
            String savePath ="C:/Users/"+username+"/Documents/E-Learning/ressources/cours/"+cours1.get(idc).getNom()+"/"+nom /*appPath + File.separator + SAVE_DIR*/;
            String description =request.getParameter("description");
                // creates the save directory if it does not exists
                File fileSaveDir = new File(savePath);
                if (!fileSaveDir.exists()) {
                    fileSaveDir.mkdir();
                }
       Part part2 = request.getPart("ressourcefile");
       String fileName2 = extractFileName( part2 );  
       System.out.println("part1\n"+part2+"\n"+fileName2);
       
            // refines the fileName in case it is an absolute path
            fileName2 = new File(fileName2).getName();
            //part.write(savePath +File.separator + fileName);
                 OutputStream out1 = null;
                 InputStream filecontent1 = null;
                out1 = new FileOutputStream(new File(savePath +File.separator + fileName2));
                filecontent1 = part2.getInputStream();

                int read1 = 0;
                final byte[] bytes1 = new byte[1024];

                while ((read1 = filecontent1.read(bytes1)) != -1) {
                    out1.write(bytes1, 0, read1);
                }
                out1.close();
            System.out.println("done");
            System.out.println("File Path: " + savePath+File.separator + fileName2);
            
            String ressourceUrl =savePath+"/"+fileName2;
            Chapitre c = new Chapitre();
            c.setIdcours(cours1.get(idc));
            c.setIdchap(id);
            c.setNom(nom);
            c.setImgurl("nope");
            c.setRessourceurl("nope");
            c.setDescription(description);
            c.setContenuurl(ressourceUrl);
            chapitreFacade.edit(c);
            
            
         List<Chapitre> chapitres =chapitreFacade.findAll();
         session.setAttribute("listeChapitres", chapitres);
         response.sendRedirect("/E-Learning-war/gestionCours.jsp");
        }
           catch(Exception e)
           {System.out.println("err "+e.toString());
           response.sendRedirect("/E-Learning-war/error.jsp");
           }
        
    }
     private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length()-1);
            }
        }
        return "";
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
