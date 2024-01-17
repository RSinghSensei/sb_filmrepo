<%@ page language = "java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
    <head>
        <meta charset="ISO-8859-1">
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;700&family=Roboto:wght@100&display=swap" rel="stylesheet">
        <style>
            table
            {
                border: 1px solid black;
                border-collapse: collapse;
                font-size:18px;
                font-family: Roboto;
            }
            td
            {
                border: 1px solid black;
                background-color:#FEF958;
                text-align: center;
            }
            th
            {
                border: 1px solid black;
                border-collapse: collapse;
                background-color: #A2FFFA;
            }
        </style>
        <title>Film Table View</title>
    </head>
    <body>
         <table>
            <thead>
                <tr>
                    <th>Flick Name</th>
                    <th>Flick Director</th>
                    <th>User Film Rating</th>
                    <th>Edit</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="film" items="${filmDetailList}">
                    <tr>
                        <td style = "font-weight: bold;">${film.flickName}</td>
                        <td style = "font-weight: bold;">${film.flickDirector}</td>
                        <td style = "font-weight: bolder; text-align: center; font-size:18px;">${film.flickRating}/<span style = "font-size:14px;">10</span></td>
                        <td><a href = "?editFilmId=${film.id}" class = "btn btn-primary" style = "font-weight: boldest; text-align: center; font-size:18px;">Edit</a></td>
                    </tr>
                </c:forEach>
            </tbody>
         </table>

        <h3>Add New Flick and Rating!</h3>
        <form action="/demo/add" method="post" modelAttribute="newFilm">
            <label path="flickName">Flick Name:</label>
            <input type="text" id="flickName" name="flickName" required>
            <label path="flickDirector">Flick Director:</label>
            <input type="text" id="flickDirector" name="flickDirector" required>
            <input type="submit" value="Add Film">
        </form>

        <c:if test="${not empty param.editFilmId}">
            <div id = "film_edit_form">
                <form action="/demo/updateFilm" method="post" modelAttribute="editedFilm">
                    <input type = "hidden" id = "selectedFilmId" name = "selectedFilmId" value = ${editedFilm.id}>
                    <label path="flickId">Flick ID: ${editedFilm.id}</label>
                    <label path="flickName">Flick Name:</label>
                    <input type="text" id="flickName" name="flickName" placeholder=${editedFilm.flickName} required>
                    <label path="flickDirector">Flick Director:</label>
                    <input type="text" id="flickDirector" name="flickDirector" placeholder=${editedFilm.flickDirector} required>
                    <input type="submit" value="Update Film">
                </form>
            </div>
        </c:if>

    </body>
</html>