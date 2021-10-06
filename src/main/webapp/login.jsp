<form method="post" action="${pageContext.request.contextPath}/hello-servlet">
    <table>
        <tbody>
        <tr>
            <td><label>Email</label></td><td><input type="text" value="" name="username" id="username"></td>
        </tr>
        <tr>
            <td><label>Password</label></td><td><input type="password" value="" name="password" id="password"></td>
        </tr>
        <tr>
            <td> <input type="submit" value="login" id="loginBtn" name="login"/>
            <input type="reset" value="reset" id="resetBtn" name="reset"/>
            <input type="submit" value="register" id="registerBtn" name="register"/></td>
        </tr>
        </tbody>
    </table>

</form>