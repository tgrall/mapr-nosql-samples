<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/mdl/material.min.css">
    <script src="${pageContext.request.contextPath}/mdl/material.min.js"></script>
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">

    <!-- Square card -->
    <style>
        .demo-card-square.mdl-card {
            width: 320px;
        }
        .demo-card-square > .mdl-card__title {
            background: rgba(0, 0, 0, 0.2);
            color: #fff;
        }
    </style>

</head>

<body>

<header class="mdl-layout__header mdl-layout__header--scroll mdl-color--grey-100 mdl-color-text--grey-800">
    <div class="mdl-layout__header-row">
        <a href="${pageContext.request.contextPath}/">
            <span class="mdl-layout-title">MapR-DB Sample Application</span>
        </a>
        <div class="mdl-layout-spacer"></div>
    </div>
</header>


<div class="mdl-grid">
    <div class="mdl-cell mdl-cell--2-col"></div>
    <div class="mdl-cell mdl-cell--8-col">


        <div>
         <h4>Simple Java Web Application for MapR-DB</h4>
        </div>


        <div>
        <!-- Colored raised button -->
        <a class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored" href="${pageContext.request.contextPath}/list">
            View Data
        </a>
        <!-- Accent-colored raised button -->
        <a class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent" href="${pageContext.request.contextPath}/home?action=init" >
            Init
        </a>
        <!-- Accent-colored raised button with ripple -->
        <a class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent" href="${pageContext.request.contextPath}/home?action=delete" >
            Delete
        </a>
        </div>
    </div>
    <div class="mdl-cell mdl-cell--2-col"></div>
</div>




<c:if test="${not empty message}">
<div class="mdl-grid">
    <div class="mdl-cell mdl-cell--4-col"></div>
    <div class="mdl-cell mdl-cell--4-col">
        <div class="demo-card-square mdl-card mdl-shadow--2dp">
            <div class="mdl-card__title mdl-card--expand">
                <h2 class="mdl-card__title-text">Message</h2>
            </div>
            <div class="mdl-card__supporting-text">
                ${message}
            </div>
            <div class="mdl-card__actions mdl-card--border">
                <a class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect" href="${pageContext.request.contextPath}/" >
                    Ok
                </a>
            </div>
        </div>
    </div>
    <div class="mdl-cell mdl-cell--4-col"></div>
</div>

</c:if>









</body>
</html>
