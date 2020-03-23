<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Bonjour</title>
</head>
<body>
<%@ include file="menu.jsp" %>
	<c:if test="${ !empty erreur }"><p style="color:red;"><c:out value="${ erreur }" /></p></c:if>
	<form method="post" action="bonjour">
        <p>
            <label for="nom">Ton nom simplet : </label>
            <input type="text" name="nom" id="nom" />
        </p>
        <p>
            <label for="prenom">Ton prénom simplet : </label>
            <input type="text" name="prenom" id="prenom" />
        </p>
        
        <input type="submit" />
    </form>
    
    <ul>
        <c:forEach var="utilisateur" items="${ utilisateurs }">
            <li><c:out value="${ utilisateur.prenom }" /> <c:out value="${ utilisateur.nom }" /></li>
        </c:forEach>
    </ul>  

 </body>
</html>
