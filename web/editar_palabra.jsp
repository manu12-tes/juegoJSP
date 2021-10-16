<%-- 
    Document   : editar_palabra
    Created on : 22/06/2020, 06:20:37 PM
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
        <h3>Editar Palabra</h3>
         <form id="epalabra" action="ediitaPalabra">
            <output name="tpalabra" value="Palabra: ">PALABRA</output>
            <input name="palabra" type="text" maxlength="30" minlength="4">
            <input name="micat" type="text" value=<%out.println(categoria);%>>
            <input type="submit" value="Editar palabra" title="ingresar">
    </body>
</html>
