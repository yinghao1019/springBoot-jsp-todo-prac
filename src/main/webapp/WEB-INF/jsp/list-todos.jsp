<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>

<div class="container">
    <div>
        <a type="button" class="btn btn-primary btn-md" href="<c:url value="/add-todo">">Add Todo</a>
    </div>
    <br>
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3>List of TODO's</h3>
        </div>
        <div class="panel-body">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>Description</th>
                    <th>Target Date</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${todos}" var="todo">
                    <tr>
                        <td>${todo.description}</td>
                        <td><fmt:formatDate value="${todo.targetDate}"
                                            pattern="dd/MM/yyyy"/></td>
                        <td><a type="button" class="btn btn-success">
                            <c:url value="/update-todo">
                                <c:param name="id"
                                         value="${todo.id}"/>
                                Update
                            </c:url>
                        </a>
                            <a type="button" class="btn btn-warning">
                                <c:url value="/delete-todo">
                                    <c:param name="id"
                                             value="${todo.id}"/>
                                Delete
                                </c:url>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

</div>
<%@ include file="common/footer.jspf" %>