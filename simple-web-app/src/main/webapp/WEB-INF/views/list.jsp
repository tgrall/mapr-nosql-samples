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


        <c:if test="${not empty message}">
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
        </c:if>



        <c:if test="${not empty results}">
        <table class="mdl-data-table mdl-js-data-table mdl-data-table mdl-shadow--2dp">
            <thead>
            <tr>
                <th class="mdl-data-table__cell--non-numeric">Row Key</th>
                <th class="mdl-data-table__cell--non-numeric">Name</th>
                <th class="mdl-data-table__cell--non-numeric">Alias</th>
                <th class="mdl-data-table__cell--non-numeric">City</th>
                <th class="mdl-data-table__cell--non-numeric">Super Power</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="hero" items="${results}" >
                <tr>
                    <td class="mdl-data-table__cell--non-numeric">${hero.id}</td>
                    <td class="mdl-data-table__cell--non-numeric">${hero.firstName} ${hero.lastName}</td>
                    <td class="mdl-data-table__cell--non-numeric">${hero.alias}</td>
                    <td class="mdl-data-table__cell--non-numeric">${hero.city}</td>
                    <td class="mdl-data-table__cell--non-numeric">${hero.superPower}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        </c:if>

    </div>
    <div class="mdl-cell mdl-cell--2-col"></div>
</div>
</body>
</html>
