<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>FML -Time Tables</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
            integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"
            integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"
            integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV"
            crossorigin="anonymous"></script>
    <script src="//cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body style="background-color: floralwhite">
<div class="container">
    <nav class="navbar fixed-top" style="background-color: #414141">
        <nav class="navbar navbar-expand-lg navbar-light">
            <div class="container-fluid">
                <a class="navbar-brand" href="${pageContext.request.contextPath}/lecturer/home"
                   style="margin-right: 45px; color: #FFFAF0;">Find My Lecture : ${loggedUser.firstname}</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                        data-bs-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false"
                        aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
                    <div class="navbar-nav">
                        <a class="nav-link" style="color: #ffdf9e" aria-current="page"
                           href="${pageContext.request.contextPath}/lecturer/home">Home</a>
                        <a class="nav-link" style="color: #FFFAF0" aria-current="page"
                           href="${pageContext.request.contextPath}/lecturer/view/timetable">Timetable</a>
                    </div>
                    <div class="navbar-nav">
                        <a class="nav-link" style="color: #ffdf9e"
                           href="${pageContext.request.contextPath}/lecturer/view/account">My Account</a>
                        <a class="nav-link" style="color: #ffdf9e; cursor: pointer" onclick="logout()">Logout</a><a
                            href="${pageContext.request.contextPath}/logout" id="logout"></a>
                    </div>
                </div>
            </div>
        </nav>
    </nav>
</div>
<div class="container">
    <div style="margin-top: 100px;">
        <form:form modelAttribute="schedules" method="get">
            <div class="col-lg-12 col-md-12 col-sm-12 container justify-content-center">
                <h1 style="color: #414141;">Time table</h1>
                <div class="mt-5">
                    <input type="text" name="search" id="search" placeholder="Search...">
                    <button type="button" id="searchButton" onclick="searchFunction()" style="color: #ffdf9e; background-color: #414141;">Search</button>
                </div>
                <table class="table" style="width: 100%; margin-top: 25px;">
                    <thead>
                    <tr>
                        <th style="width: 30%; color: #414141;">Module</th>
                        <th style="width: 20%; color: #414141;">Batch/s</th>
                        <th style="width: 15%; color: #414141;">Date</th>
                        <th style="width: 10%; color: #414141;">Start time</th>
                        <th style="width: 10%; color: #414141;">End time</th>
                        <th style="width: 15%; color: #414141;">Class room</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${schedules}" var="timeTable">
                        <tr>
                            <td>${timeTable.modules.moduleName}</td>
                            <td>
                                <c:forEach items="${timeTable.batches}" var="batch">
                                    ${batch.batchCode}<br>
                                </c:forEach>
                            </td>
                            <td>${timeTable.date}</td>
                            <td>${timeTable.startTime}</td>
                            <td>${timeTable.endTme}</td>
                            <td>${timeTable.rooms.roomName}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </form:form>
    </div>
</div>
<div class="container-fluid" style="margin-top: 80px;">
    <nav class="fixed-bottom" style="background-color: #414141">
        <footer class="text-center text-lg-start" style="padding: 10px 0">
            <div class="text-center p-3" style="color: #998d88">
                © 2020 Copyright:
                <a style="color: #998d88;" class="text-center p-3"
                   href="${pageContext.request.contextPath}/lecturer/home">Find
                    My Lecture</a>
            </div>
        </footer>
    </nav>
</div>
</body>
</html>
<script>
    function logout() {
        Swal.fire({
            icon: 'question',
            title: 'Sure you want to logout?',
            showCancelButton: true,
            confirmButtonText: `Yes!`,
            cancelButtonText: 'Nope!',
        }).then((result) => {
            if (result.isConfirmed) {
                document.getElementById('logout').click();
            }
        })
    }

    function searchFunction(){
        console.log("here");
        const searchBy = $("#search").val();
        console.log(searchBy);

        if(searchBy === ""){
            Swal.fire({
                title: "Empty search!",
                text: "Please enter something to search!",
                icon: "error",
            });
        } else {
            window.location.href = '/lecturer/search/timetable/' + searchBy;
        }
    }
</script>