<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin - Update ${module.moduleName}</title>
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
                        <a class="nav-link" style="color: #FFFAF0"
                           href="${pageContext.request.contextPath}/admin/view/modules">Modules</a>
                        <a class="nav-link" style="color: #ffdf9e"
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
    <div style="margin-top: 120px;">
        <form:form id="formSubmit" modelAttribute="module" method="post">
            <div class="col-lg-6 col-md-6 col-sm-6 container justify-content-center">
                <h2 style="color: #414141;">Update Module : ${module.moduleName}</h2>
                <div class="mb-3">
                    <label style="color: #414141;" class="form-label">Update Module name</label>
                    <form:input path="moduleName" id="moduleName" type="text" class="form-control"
                                autofocus="autofocus"/>
                </div>
                <div class="mb-3">
                    <label style="color: #414141;" class="form-label">Update module description</label>
                    <form:input path="moduleDescription" id="description" type="textarea" class="form-control"/>
                </div>
                <div class="mb-3">
                    <label style="color: #414141;" class="form-label">Current Lecturer: </label>
                    <label>${module.user.firstname} ${module.user.lastname}</label>
                </div>
                <div class="mb-3 col text-center">
                    <a type="button" class="btn btn-outline-info"
                       href="${pageContext.request.contextPath}/admin/update/module/lecturer/${module.moduleId}">
                        Update Lecturer</a>
                    <a type="button" class="btn btn-outline-danger"
                       onclick="confirmDelete('${module.moduleId}', '${module.user.username}')">
                        De-assign lecturer
                    </a>
                    <div>
                        <p style="display: none" id="moduleLecturerAvailabilityCheck">${module.user}</p>
                    </div>
                </div>
                <div class="mb-3 col text-center">
                    <button type="submit" class="btn btn"
                            style="color: floralwhite; border-color: #414141; background-color: #414141">
                        Update
                    </button>
                </div>
                <div class="col text-center mt-2">
                    <a href="${pageContext.request.contextPath}/admin/view/modules"
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
<div class="container-fluid">
    <nav class="fixed-bottom" style="background-color: #414141">
        <footer class="text-center text-lg-start" style="padding: 10px 0">
            <div class="text-center p-3" style="color: #998d88">
                ?? 2020 Copyright:
                <a style="color: #998d88;" class="text-center p-3"
                   href="${pageContext.request.contextPath}/admin/update/module/${module.moduleId}">Find
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
                title: "Error occurred while updating!!!",
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
        const moduleNameFormat = /[a-zA-Z0-9_.+-]{50}/;
        const descriptionFormat = /[a-zA-Z0-9_.+-]{200}/;

        const moduleName = $("#moduleName").val();
        const description = $("#description").val();

        if (moduleName.length < 3 || moduleName.length > 50 || moduleName.match(moduleNameFormat)) {
            event.preventDefault();
            Swal.fire({
                title: "Error in module name!!!",
                text: "-> The module name cannot be empty!\n" +
                    "-> Module name cannot contain symbols.\n " +
                    "-> Module name should contain 3 to 50 characters!",
                icon: "error",
            });
        } else if (description.length < 3 || description.length > 200 || description.match(descriptionFormat)) {
            event.preventDefault();
            Swal.fire({
                title: "Error in module description!!!",
                text: "-> The module description cannot be empty!\n" +
                    "-> Module description cannot contain symbols.\n " +
                    "-> Module description should contain 3 to 200 characters!",
                icon: "error",
            });
        } else {
            Swal.fire({
                title: 'Updating...',
                html: 'Hold on a few seconds while we update the module!',
                timer: 10000,
                timerProgressBar: false,
            });
        }
    });

    function confirmDelete(moduleId, lecturerUsername) {
        const checkUser = document.getElementById("moduleLecturerAvailabilityCheck").innerHTML;
        console.log(checkUser);
        if (checkUser === "") {
            Swal.fire({
                position: 'top-end',
                icon: 'warning',
                title: "No lecturer to de-assign!",
                showConfirmButton: false,
                timer: 2000
            });
        } else {
            Swal.fire({
                icon: 'question',
                title: 'Sure you want to de-assign the lecturer from the module?',
                text: 'This action cannot be reversed!',
                showCancelButton: true,
                confirmButtonText: `Yes!`,
                cancelButtonText: 'Nope!',
            }).then((result) => {
                if (result.isConfirmed) {
                    window.location.href = '/admin/update/' + moduleId + '/lecturer/' + lecturerUsername + '/de-assign';
                    Swal.fire({
                        title: 'De-assigning...',
                        html: 'Hold on a few seconds while we de-assign the lecturer from the module!',
                        timer: 10000,
                        timerProgressBar: false,
                    });
                }
            })
        }
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