<%--
  Created by IntelliJ IDEA.
  User: 15532
  Date: 2022/1/5
  Time: 22:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
  <form action="/upload.do" method="post" enctype="multipart/form-data">

    <p>上传人： <input type="text" name="name"></p>
    <p><input type="file" name="file1"></p>
    <p><input type="file" name="file2"></p>
    <p><input type="submit">| <input type="reset"></p>
  </form>
  </body>
</html>
