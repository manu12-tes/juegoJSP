<%-- 
    Document   : agregar_tipo_palabra
    Created on : 31/05/2020, 08:39:18 PM
    Author     : manel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <center>
    <body>
        <h3>Agregar tipo de palabra</h3>
        <form id="atipo" action="AgregarTipo">
            <output name="ttipo" value="Tipo: ">DESCRIPCION</output>
            <input name="tipo" type="text" maxlength="30" minlength="4">
            <input type="submit" value="Aceptar" title="ingresar">
        </form>
    </body>
    </center>
</html>
