<%--
  Created by IntelliJ IDEA.
  User: Lichaojie
  Date: 2017/10/24
  Time: 20:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>文件上传</title>
</head>
<body>
<h2>文件上传</h2>
<form action="/file/upload" enctype="multipart/form-data" method="post">
  <table>
    <tr>
      <td>文件描述:</td>
      <td><input type="text" name="path"></td>
    </tr>
    <tr>
      <td>请选择文件:</td>
      <td><input type="file" name="files" multiple="multiple"></td>
    </tr>
    <tr>
      <td><input type="submit" value="上传"></td>
    </tr>
  </table>
</form>
</body>
</html>>
