<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Student Handler - View Students</title>
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
                <a class="navbar-brand" href="${pageContext.request.contextPath}/handler/register"
                   style="margin-right: 45px; color: #FFFAF0;">Find My Lecture : ${loggedUser.firstname}</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                        data-bs-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false"
                        aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
                    <div class="navbar-nav">
                        <a class="nav-link" style="color: #ffdf9e" aria-current="page"
                           href="${pageContext.request.contextPath}/handler/home">Home</a>
                        <a class="nav-link" style="color: #ffdf9e"
                           href="${pageContext.request.contextPath}/handler/register">Register</a>
                        <a class="nav-link" style="color: #FFFAF0"
                           href="${pageContext.request.contextPath}/handler/view/students">Students</a>
                        <a class="nav-link" style="color: #ffdf9e"
                           href="${pageContext.request.contextPath}/handler/view/timetable">Timetable</a>
                    </div>
                    <div class="navbar-nav">
                        <a class="nav-link" style="color: #ffdf9e"
                           href="${pageContext.request.contextPath}/handler/view/account">My
                            Account</a>
                        <a class="nav-link" style="color: #ffdf9e; cursor: pointer" onclick="logout()">Logout</a><a
                            href="${pageContext.request.contextPath}/logout" id="logout"></a>
                    </div>
                </div>
            </div>
        </nav>
    </nav>
</div>
<div class="container">
    <div style="margin-top: 80px;">
        <form:form id="formSubmit" modelAttribute="students" method="get">
            <div class="col-lg-12 col-md-12 col-sm-12 container justify-content-center">
                <h1 style="color: #414141; margin-top: 25px;">Lecturers</h1>

                <table class="table" style="width: 100%; margin-top: 25px;">
                    <thead>
                    <tr>
                        <th style="width: 10%; color: #414141;">Username</th>
                        <th style="width: 15%; color: #414141;">First name</th>
                        <th style="width: 15%; color: #414141;">Last name</th>
                        <th style="width: 20%; color: #414141;">Contact number</th>
                        <th style="width: 20%; color: #414141;">Email</th>
                        <th style="width: 10%; color: #414141;">Batch</th>
                        <th style="width: 5%; color: #414141;">Update</th>
                        <th style="width: 5%; color: #414141;">Delete</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${students}" var="student">
                        <tr>
                            <td>${student.username}</td>
                            <td>${student.firstname}</td>
                            <td>${student.lastname}</td>
                            <td>${student.contactNumber}</td>
                            <td>${student.email}</td>
                            <td>${student.batch.batchCode}</td>

                            <td><a type="button" class="btn btn-outline-info"
                                   href="${pageContext.request.contextPath}/handler/update/student/${student.username}">Update</a>
                            </td>
                            <td><a type="button" class="btn btn-outline-danger"
                                   onclick="confirmDelete('${student.username}')">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div>
                <p style="display: none" id="errorMessage">${error}</p>
            </div>
            <div>
                <p style="display: none" id="successMessage">${success}</p>
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
                   href="${pageContext.request.contextPath}/handler/view/students">Find
                    My Lecture</a>
            </div>
        </footer>
    </nav>
</div>
</body>
</html>
<script>
    window.onload = function () {
        const errorMessage = document.getElementById("errorMessage").innerHTML;
        const successMessage = document.getElementById("successMessage").innerHTML;

        if (errorMessage !== "") {
            Swal.fire({
                title: "Error occurred!!!",
                text: errorMessage,
                icon: "error",
            });
        }
        if (successMessage !== "") {
            Swal.fire({
                position: 'top-end',
                icon: 'success',
                title: successMessage,
                showConfirmButton: false,
                timer: 3000
            });
        }
    }

    function confirmDelete(name) {
        Swal.fire({
            icon: 'question',
            title: 'Sure you want to delete the student?',
            text: 'This action cannot be reversed!',
            showCancelButton: true,
            confirmButtonText: `Yes!`,
            cancelButtonText: 'Nope!',
        }).then((result) => {
            if (result.isConfirmed) {
                window.location.href = '/handler/delete/student/' + name;
                Swal.fire({
                    title: 'Deleting...',
                    html: 'Hold on a few seconds while we delete the student!',
                    timer: 10000,
                    timerProgressBar: false,
                });
            }
        })
    }

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
</script>