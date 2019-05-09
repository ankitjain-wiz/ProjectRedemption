<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
     <%@page isELIgnored="false" %>  
    
<!DOCTYPE html>
<html>
<head>
<style>
div.container {
  width: 100%;
  border: 1px solid gray;
  margin-right: auto;
  margin-left: auto;
  margin-top: 0px;
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
  padding: 0px;
}

header {
    padding: 1em;
    color: Black;
    background-color: #D3D3D3;
    clear: left;
    text-align:center;
}

footer {
    padding: 1em;
    color: Black;
    background-color: #D3D3D3;
    clear: left;
    text-align: center;
}

label {
    text-align:center;
    line-height:150%;
}
</style>
   	 	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
   		<title>Case Sampling</title>
    </head>
    <body>
        <form:form  method="post" modelAttribute="caseSamplingRequest" action="/dossier-mgmt-war/casesample">
          <div class="container">
		  <header>
		  <img src="resources/images/epo.png" width="200" height="100" alt="logo" style="margin:-10px 0 0 -10px; float:left;"/>
		  <h1>Case Sampling</h1>
		  <img src="resources/images/ss.png" width="200" height="100" alt="logo" style="margin:-89px -14px 0px 0px; float:right;"/>
		  </header>
		  <br><br><br>
		  <table align="center">
          <tr><td>
          <label style="float: left;"><b>User ID:</label>
          <label style="margin:0 0 0 300px">${casbean.userID}</label><br><br>
		  <label style="float: left;"><b>Dossier Max Year Target:</b></label>
          <label style="margin:0 0 0 184px">${casbean.numMaxYearTarget}</label><br><br>
		  <label style="float: left;"><b>Dossier Max Month Target:</b></label>
          <label style="margin:0 0 0 169px">${casbean.numMaxMonthTarget}</label><br><br>
		  <label style="float: left;"><b>Dossier Sampled:</b></label>
          <label style="margin:0 0 0 240px">${casbean.numSampled}</label><br><br>
		  <label style="float: left;"><b>Comments:</b></label>
          <label style="margin:0 0 0 278px; color:#ff6666">"Dossier Not Sampled"</label><br><br>
		  <br><br>
		  </td></tr>
          </table>
		  <br><br><br> <br> <br> <br> <br> <br> <br> <br> <br> <br>
		  <footer>European Patent Office</footer>
		  </div>
        </form:form>
		
    </body>
		
    </body>
</html>