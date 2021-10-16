<%-- 
    Document   : eliminar_palabra
    Created on : 22/06/2020, 06:21:08 PM
    Author     : manel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
   <% String categoria=request.getParameter("cate");%>
      
        <h2>Palabras de categoria <% out.println(categoria); %></h2>
        <h3>Agregar Palabra</h3>
         <form id="apalabra" action="eliminaPalabra">
            <output name="tpalabra" value="Palabra: ">PALABRA</output>
            <input name="micat" type="text" value=<%out.println(categoria);%>>
            <input type="submit" value="Eliminar palabra" title="ingresar">
    </body>
</html>
