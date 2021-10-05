<form method="post" action="${pageContext.request.contextPath}/hello-servlet">
    <table>
        <tbody>
        <tr>
            <td><label>Email</label><input type="text" value="" name="username" id="username"></td>
        </tr>
        <tr>
            <td><label>Password</label><input type="password" value="" name="password" id="password"></td>
        </tr>
        </tbody>
    </table>
    <input type="submit" value="login" id="loginBtn" name="login"/>
    <input type="reset" value="reset" id="resetBtn" name="reset"/>
    <input type="submit" value="register" id="registerBtn" name="register"/>
</form>