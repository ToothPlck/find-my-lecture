<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin - Add Class room</title>
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
                <a class="navbar-brand" href="${pageContext.request.contextPath}/admin/register"
                   style="margin-right: 45px; color: #FFFAF0;">Find My Lecture : ${loggedUser.firstname}</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                        data-bs-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false"
                        aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
                    <div class="navbar-nav">
                        <a class="nav-link" style="color: #ffdf9e" aria-current="page"
                           href="${pageContext.request.contextPath}/admin/home">Home</a>
                        <a class="nav-link" style="color: #ffdf9e"
                           href="${pageContext.request.contextPath}/admin/register">Register</a>
                        <a class="nav-link" style="color: #ffdf9e"
                           href="${pageContext.request.contextPath}/admin/view/lecturers">Lecturers</a>
                        <a class="nav-link" style="color: #ffdf9e"
                           href="${pageContext.request.contextPath}/admin/view/batches">Batches</a>
                        <a class="nav-link" style="color: #ffdf9e"
                           href="${pageContext.request.contextPath}/admin/view/modules">Modules</a>
                        <a class="nav-link" style="color: #FFFAF0"
                           href="${pageContext.request.contextPath}/admin/view/rooms">Class rooms</a>
                    </div>
                    <div class="navbar-nav">
                        <a class="nav-link" style="color: #ffdf9e"
                           href="${pageContext.request.contextPath}/admin/view/account">My Account</a>
                        <a class="nav-link" style="color: #ffdf9e; cursor: pointer" onclick="logout()">Logout</a><a
                            href="${pageContext.request.contextPath}/logout" id="logout"></a>
                    </div>
                </div>
            </div>
        </nav>
    </nav>
</div>
<div class="container">
    <div style="margin-top: 200px;">
        <form:form id="formSubmit" modelAttribute="roomForm" method="post">
            <div class="col-lg-4 col-md-4 col-sm-4 container justify-content-center">
                <h2 style="color: #414141;">Add Class Room</h2>
                <div class="mb-3">
                    <label style="color: #414141;" class="form-label">Name</label>
                    <form:input path="roomName" id="roomName" type="text" class="form-control" autofocus="autofocus"/>
                </div>
                <div class="mb-3">
                    <label style="color: #414141;" class="form-label">Type</label>
                    <form:input path="roomType" id="roomType" type="text" class="form-control"/>
                </div>
                <div class="mb-3">
                    <label style="color: #414141;" class="form-label">Description</label>
                    <form:textarea path="roomDescription" id="description" type="textarea" class="form-control"/>
                </div>
                <div class="col text-center">
                    <button type="submit" class="btn btn"
                            style="color: floralwhite; border-color: #414141; background-color: #414141">
                        Add Class Room
                    </button>
                </div>
                <div class="col text-center mt-2">
                    <a href="${pageContext.request.contextPath}/admin/view/rooms"
                       style="color: #414141">< Back</a>
                </div>
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
                   href="${pageContext.request.contextPath}/admin/add/room">Find
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
                title: "Error occurred while adding the classroom!!!",
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

    const form = document.getElementById('formSubmit');

    form.addEventListener('submit', function (event) {
        const roomNameFormat = /[^a-zA-Z0-9\s]+/;
        const typeFormat = /[^a-zA-Z0-9\s]+/;
        const descriptionFormat = /[a-zA-Z0-9_.+-]{200}/;

        const roomName = $("#roomName").val();
        const roomType = $("#roomType").val();
        const description = $("#description").val();

        if (roomName.length < 3 || roomName.length > 25 || roomName.match(roomNameFormat)) {
            event.preventDefault();
            Swal.fire({
                title: "Error in classroom name!!!",
                text: "-> The classroom name cannot be empty!\n" +
                    "-> Classroom name cannot contain symbols.\n " +
                    "-> Classroom name should contain 3 to 25 characters!",
                icon: "error",
            });
        } else if (roomType.length < 3 || roomType.length > 50 || roomType.match(typeFormat)) {
            event.preventDefault();
            Swal.fire({
                title: "Error in classroom type!!!",
                text: "-> The classroom type cannot be empty!\n" +
                    "-> Classroom type cannot contain symbols.\n " +
                    "-> Classroom type should contain 3 to 25 characters!",
                icon: "error",
            });
        } else if (description.length < 3 || description.length > 200 || description.match(descriptionFormat)) {
            event.preventDefault();
            Swal.fire({
                title: "Error in classroom description!!!",
                text: "-> The classroom description cannot be empty!\n" +
                    "-> Classroom description should contain 3 to 200 characters!",
                icon: "error",
            });
        } else {
            Swal.fire({
                title: 'Adding...',
                html: 'Hold on a few seconds while we add the classroom!',
                timer: 10000,
                timerProgressBar: false,
            });
        }
    });

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