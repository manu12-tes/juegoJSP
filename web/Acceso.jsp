<%-- 
    Document   : Acceso
    Created on : 26/05/2020, 08:44:13 PM
    Author     : manel
--%>
<%@page import="juego.Ahorcado"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
    <center>
        <h1>Acceso al juego</h1>
        <h2>Bienvenido</h2>
          <form id="apalabra" action="Ahorcado">
            <output name="tpalabra" value="categoria: ">Categoria</output>
            <input name="categori" type="text" maxlength="30" minlength="4">
             <output name="tpalabra" value="nivel: ">NIvel</output>
             <input name="difi" type="number">
              <output name="tpalabra" value="nivel: ">Intentos</output>
              <input name="intentos" type="number">
            <input type="submit" value="jugar" title="ingresar">
           
            
        </form>
        
      
    </center>
    </body>
</html>
