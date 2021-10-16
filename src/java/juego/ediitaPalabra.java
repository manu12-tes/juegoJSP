/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juego;

import Controles.PalabrasJpaController;
import Controles.exceptions.RollbackFailureException;
import Modelo.Palabras;
import Modelo.Tipos;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;

/**
 *
 * @author manel
 */
@WebServlet(name = "ediitaPalabra", urlPatterns = {"/ediitaPalabra"})
public class ediitaPalabra extends HttpServlet {
     @PersistenceUnit
private EntityManagerFactory emf;

@Resource
private UserTransaction utx;

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
            throws ServletException, IOException, RollbackFailureException, Exception {
        response.setContentType("text/html;charset=UTF-8");
         String cpalabra=(String)request.getParameter("micat");
        String categori =(String)request.getParameter("palabra");
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
           /* out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ediitaPalabra</title>");            
            out.println("</head>");
             out.println("<form>");
            out.println("<body>");
            
             out.println("<h1><input type='text' name='cati' value='"+categori+"'></h1>");*/
             emf=Persistence.createEntityManagerFactory("juegoPU");
          EntityManager em = emf.createEntityManager();
           TypedQuery<Palabras> consultaAlumnos= em.createNamedQuery("Palabras.findByPalabra",Palabras.class);
            consultaAlumnos.setParameter("palabra", cpalabra);
            Palabras tp=(Palabras)consultaAlumnos.getSingleResult();
            
            PalabrasJpaController controlPalabra;
           controlPalabra = new PalabrasJpaController (utx,emf);
            
           tp.setPalabra(categori);
            controlPalabra.edit(tp);
            if(categori.length()<=10){
              tp.setNivel(1);
               controlPalabra.edit(tp);
            }else if(categori.length()>10){
               tp.setNivel(2);
                controlPalabra.edit(tp);
            }else if(categori.length()>20){
               tp.setNivel(3);
                controlPalabra.edit(tp);
            }
            
            controlPalabra.edit(tp);
            response.sendRedirect("ListarTipos");
            // out.println("</form>");
            //out.println("</body>");
            //out.println("</html>");
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
         try {
             processRequest(request, response);
         } catch (Exception ex) {
             Logger.getLogger(ediitaPalabra.class.getName()).log(Level.SEVERE, null, ex);
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
         try {
             processRequest(request, response);
         } catch (Exception ex) {
             Logger.getLogger(ediitaPalabra.class.getName()).log(Level.SEVERE, null, ex);
         }
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
