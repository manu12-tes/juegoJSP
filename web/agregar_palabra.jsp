<%-- 
    Document   : agregar_palabra
    Created on : 1/06/2020, 11:10:06 PM
    Author     : manel
--%>
<
 
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
     <center>
    <body>
     
      <% String categoria=request.getParameter("cate");%>
      
        <h2>Palabras de categoria <% out.println(categoria); %></h2>
        <h3>Agregar Palabra</h3>
         <form id="apalabra" action="AgregarPalabra">
            <output name="tpalabra" value="Palabra: ">PALABRA</output>
            <input name="palabra" type="text" maxlength="30" minlength="4">
            <input name="micat" type="text" value=<%out.println(categoria);%>>
            <input type="submit" value="Agregar palabra" title="ingresar">
           
            
        </form>
    </body>
     </center>
</html>
