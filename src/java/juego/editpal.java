/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juego;

import Modelo.Palabras;
import Modelo.Tipos;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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
@WebServlet(name = "editpal", urlPatterns = {"/editpal"})
public class editpal extends HttpServlet {
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
        String pala=(String)request.getParameter("cati");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet editpal</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<form metohd='pos' Action='editar_palabra.jsp'>");
            out.println("<h1>Servlet editpal at " + request.getContextPath() + "</h1>");
                 emf=Persistence.createEntityManagerFactory("juegoPU");
            EntityManager em = emf.createEntityManager();
            TypedQuery<Tipos> consultaAlumnos= em.createNamedQuery("Tipos.findByDescripcion",Tipos.class);
            consultaAlumnos.setParameter("descripcion",pala);
            Tipos tp=(Tipos)consultaAlumnos.getSingleResult();
            
             TypedQuery<Palabras> consultaP= em.createNamedQuery("Palabras.ptipo", Palabras.class);
           consultaP.setParameter("tipo",tp);
           List<Palabras> lista= consultaP.getResultList();
             out.println("<table align ='center' width='60%' border=11>");
            out.println("<tr>"
                    + "<td class ='datos'>ID</td>"
                    +"<td class ='datos'>PALABRA</td>"
                    +"<td class ='datos'>Nivel</td>"
                    +"<td class ='datos'>Categoria</td>"
                    + "</tr>"
                    );
            
            for(Palabras dato:lista){
                out.println("<tr>"
                    + "<td class ='datos'>"+dato.getIdpalabra()+"</td>"
                     +"<td><input type='submit' name='cate' value='"+dato.getPalabra()+"'></td> "  
                    +"<td class ='datos'>"+dato.getNivel()+"</td>"
                   +"<td><input type='text' name='cat' value='"+dato.getTipo().getDescripcion()+"'></td>"     
                     
                    + "</tr>"
                    );
            }
            out.println("</form>");
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
