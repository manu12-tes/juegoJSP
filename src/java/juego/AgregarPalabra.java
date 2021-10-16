/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juego;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.transaction.UserTransaction;
import Modelo.Tipos;
import Modelo.Palabras;
import Controles.TiposJpaController;
import Controles.PalabrasJpaController;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpSession;

/**
 *
 * @author manel
 */
@WebServlet(name = "AgregarPalabra", urlPatterns = {"/AgregarPalabra"})
public class AgregarPalabra extends HttpServlet {

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
            throws ServletException, IOException,Exception {
        response.setContentType("text/html;charset=UTF-8");
       // HttpSession sesion = request.getSession();
        String tpalabra=(String)request.getParameter("palabra");
        String categori =(String)request.getParameter("micat");
        try (PrintWriter out = response.getWriter()) {
            
             
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ListarPalabras</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<center>");
            out.println("<form metohd='pos' Action='ListarPalabras'>");
            out.println("<h1><input type='text' name='cati' value='"+categori+"'></h1>");
            emf=Persistence.createEntityManagerFactory("juegoPU");
          EntityManager em = emf.createEntityManager();
           TypedQuery<Tipos> consultaAlumnos= em.createNamedQuery("Tipos.findByDescripcion",Tipos.class);
            consultaAlumnos.setParameter("descripcion", categori);
            Tipos tp=(Tipos)consultaAlumnos.getSingleResult();
            //out.println("<h1>"+categori+"</h1>");
         // out.println("<h1><input='number' name='clat' value='"+categori+"'></h1>");
            
            /* TODO output your page here. You may use following sample code. */
            
           PalabrasJpaController controlPalabra;
           controlPalabra = new PalabrasJpaController (utx,emf);
           Palabras pal=new Palabras();
            pal.setPalabra(tpalabra);
            if(tpalabra.length()<=10){
              pal.setNivel(1);
            }else if(tpalabra.length()>10){
               pal.setNivel(2);
            }else if(tpalabra.length()>20){
               pal.setNivel(3);
            }
            pal.setTipo(tp);
           
            controlPalabra.create(pal);
            out.println("<td><input type='submit' name='lis' value='listar'></td>" );
             out.println("</form>");
             
              out.println("<form metohd='pos' Action='editpal'>");
              out.println("<h1><input type='text' name='cati' value='"+categori+"'></h1>");
            out.println("<input type='submit' name='edi' value='editar'>" );
             out.println("</form>");
             
             out.println("<form metohd='pos' Action='elimpal'>");
             out.println("<h1><input type='text' name='cati' value='"+categori+"'></h1>");
            out.println("<input type='submit' name='eli' value='eliminar'>" );
             out.println("</form>");
           
            out.println("</center");
            out.println("</body>");
            out.println("</html>");
            
            
          // response.sendRedirect("ListarPalabras");
        }catch(Exception e){
            
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
         Logger.getLogger(AgregarPalabra.class.getName()).log(Level.SEVERE, null, ex);
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
         Logger.getLogger(AgregarPalabra.class.getName()).log(Level.SEVERE, null, ex);
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
