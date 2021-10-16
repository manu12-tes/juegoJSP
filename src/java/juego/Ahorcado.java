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
import java.util.Random;
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
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;

/**
 *
 * @author manel
 */
@WebServlet(name = "Ahorcado", urlPatterns = {"/Ahorcado"})
public class Ahorcado extends HttpServlet {
      @PersistenceUnit
private EntityManagerFactory emf;

@Resource
private UserTransaction utx;
    private  String[] PALABRAS1;


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
        HttpSession sesion = request.getSession();
        int maxint =Integer.parseInt((String)request.getParameter("intentos"));
        int dificultad=Integer.parseInt((String)request.getParameter("difi"));
        String categoria = (String)request.getParameter("categori");
        String palabra=null;
        String aciertos;
        String errados;
         emf=Persistence.createEntityManagerFactory("juegoPU");
            EntityManager em = emf.createEntityManager();
            TypedQuery<Tipos> consultaAlumnos= em.createNamedQuery("Tipos.findByDescripcion",Tipos.class);
            consultaAlumnos.setParameter("descripcion",categoria);
            
            
              Tipos tp=consultaAlumnos.getSingleResult();
              TypedQuery<Palabras> consultaP= em.createNamedQuery("Palabras.ptipo", Palabras.class);
           consultaP.setParameter("tipo",tp);
           List<Palabras> lista= consultaP.getResultList();
            PALABRAS1=new String[lista.size()];
           for(int cont=0;cont<lista.size();cont++){
               if(lista.get(cont).getNivel().equals(dificultad))
                PALABRAS1[cont]=lista.get(cont).getPalabra();
           }
           
            
        if(palabra==null){
           
            Random oran=new Random();
            palabra=PALABRAS1[oran.nextInt(PALABRAS1.length)];  
          
            aciertos="";
            errados="";
            sesion.setAttribute("palabra",palabra);
            sesion.setAttribute("aciertos", aciertos);
            sesion.setAttribute("errados", errados);
            
        }else{
            aciertos=(String)sesion.getAttribute("aciertos");
            errados=(String)sesion.getAttribute("errados");
            String letra =(String)sesion.getAttribute("letra");
            
            if(palabra.indexOf(letra)>=0)
                aciertos+=letra;
            else
               errados+=letra;
            
            sesion.setAttribute("aciertos", aciertos);
            sesion.setAttribute("errados", errados);
        }
        PrintWriter out = response.getWriter();
       try  {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Ahorcado</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<center>");
            out.println("<h2>Juego</h2>");
            out.println("<h3>Selecciona una letra</h3>");
            boolean terminado=true;
            out.println("<h2>");
            for(int i=0;i<palabra.length();i++){
                String letra=palabra.substring(i,i+1);
                if(aciertos.indexOf(letra)>=0)
                    out.println(""+letra);
                else
                    out.println(""+"_");
                    terminado=false;
                //}
            }
            out.println("</h2>");
            if(maxint>errados.length()){
                out.println("<br/><br/><br/>");
                for(char car='a';car <='z';car++){
                   if(aciertos.indexOf(car)==-1 && errados.indexOf(car)==-1)
                       out.println("<a href=Ahorcado?letra=" +
                               car+">"+car+"</a>");
                }
                out.println("<br/><h2>"+"oportunidades de errar:  "+(maxint-errados.length())+"</h2>");
                out.println("<br/><h3>la siguiente letra debe ser "+palabra.charAt(0)+"</h2>");
            }
            else{
                sesion.invalidate();
                 out.println("<br/><h3>Juego Terminado</h2>");
                 out.println("<br/><a href='index.jsp'>Regresar</a>");
            }
            if(terminado){
                 sesion.invalidate();
                 out.println("<br/><h1>Juego Completo</h1>");
                 out.println("<br/><a href='index.jsp'>Regresar</a>"); 
            }
            out.println("</center>");
            out.println("</body>");
            out.println("</html>");
        }finally{
           out.close();
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
