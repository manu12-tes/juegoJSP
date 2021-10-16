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
import Controles.TiposJpaController;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;

/**
 *
 * @author manel
 */
@WebServlet(name = "ListarTipos", urlPatterns = {"/ListarTipos"})
public class ListarTipos extends HttpServlet {
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
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
             emf=Persistence.createEntityManagerFactory("juegoPU");
            TiposJpaController controlTipo;
            controlTipo = new TiposJpaController (utx,emf);
            List<Tipos> lista =controlTipo.findTiposEntities();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ListarTipos</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<center>");
          
            out.println("<form metohd='pos' Action='agregar_palabra.jsp'>");
            out.println("<table align ='center' width='60%' border=11>");
            out.println("<tr>"
                    + "<td class ='datos'>ID</td>"
                    +"<td class ='datos'>Descripcion</td>"
                    + "</tr>"
                    );
            for(Tipos dato:lista){
                out.println("<tr>"
                    + "<td class ='datos'>"+dato.getIdtipos()+"</td>"
                   // +"<td class ='datos'>"+dato.getDescripcion()+"</td>"
                   //+"<td><input type='text' name='cat' value='"+dato.getDescripcion()+"'></td>"     
                    +"<td><input type='submit' name='cate' value='"+dato.getDescripcion()+"'></td> "    
                    + "</tr>"
                    );
            }
            out.println("</table>");
            out.println("<br/>");
            out.println("<a href =\"agregar_tipo_palabra.jsp\">seguir agregando</a>");
            out.println("<a href =\"index.jsp\">Regresar</a>");
            out.println("</form>");
            out.println("</center");
            out.println("</body>");
            out.println("</html>");
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
