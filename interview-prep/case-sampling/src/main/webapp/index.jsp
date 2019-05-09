<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
    
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
          <div class="container" style="background-image: url(../resources/images/ss.png);">
		  <header>
		  <img src="resources/images/epo.png" width="200" height="100" alt="logo" style="margin:-10px 0 0 -10px; float:left;"/>
		  <h1>Case Sampling Algorthim Validation</h1>
		  <img src="resources/images/ss.png" width="200" height="100" alt="logo" style="margin:-89px -14px 0px 0px; float:right;"/>
		  </header>
		  <br><br><br>
		  <table align="center">
          <tr><td>
          <label style="float: left;"><b>User ID:</label>
          <input type="text" name="userID" placeholder="User ID" style="margin:0 0 0 300px"><br><br>
            <label style="float: left;"><b>Directorate Name:</label>
          <input type="text" name="directorateName" placeholder="Directorate Name" style="margin:0 0 0 300px"><br><br>
		  
          <label style="float: left;"><b>Num PAX:</b></label>
          <input type="text" name="numPAX" placeholder="NUM PAX" style="float: right;"><br><br><br><br>
           <label style="float: left;"><b>Director Id:</b></label>
          <input type="text" name="directorId" placeholder="Director Id" style="float: right;"><br><br><br><br>
          <label style="float: left;"><b>Num Examin Director:</b></label>
          <input type="text" name="numExamInDir" placeholder="Num Examin Director" style="float: right;"><br><br><br><br>
          <label style="float: left;"><b>Jan WorkLoad Count:</b></label>
          <input type="text" name="janWorkLoadCount" placeholder="Jan WorkLoad Count" style="float: right;"><br><br><br><br>
          <label style="float: left;"><b>Feb WorkLoad Count:</b></label>
          <input type="text" name="febWorkLoadCount" placeholder="Feb WorkLoad Count" style="float: right;"><br><br><br><br>
          <label style="float: left;"><b>Mar WorkLoad Count:</b></label>
          <input type="text" name="marWorkLoadCount" placeholder="Mar WorkLoad Count" style="float: right;"><br><br><br><br>
          <label style="float: left;"><b>Apr WorkLoad Count:</b></label>
          <input type="text" name="AprWorkLoadCount" placeholder="Apr WorkLoad Count" style="float: right;"><br><br><br><br>
          <label style="float: left;"><b>May WorkLoad Count:</b></label>
          <input type="text" name="mayWorkLoadCount" placeholder="May WorkLoad Count" style="float: right;"><br><br><br><br>
          <label style="float: left;"><b>Jun WorkLoad Count:</b></label>
          <input type="text" name="junWorkLoadCount" placeholder="Jun WorkLoad Count" style="float: right;"><br><br><br><br>
          <label style="float: left;"><b>July WorkLoad Count:</b></label>
          <input type="text" name="julWorkLoadCount" placeholder="Jul WorkLoad Count" style="float: right;"><br><br><br><br>
          <label style="float: left;"><b>Aug WorkLoad Count:</b></label>
          <input type="text" name="augWorkLoadCount" placeholder="Aug WorkLoad Count" style="float: right;"><br><br><br><br>
          <label style="float: left;"><b>Sep WorkLoad Count:</b></label>
          <input type="text" name="sepWorkLoadCount" placeholder="Sep WorkLoad Count" style="float: right;"><br><br><br><br>
          <label style="float: left;"><b>Oct WorkLoad Count:</b></label>
          <input type="text" name="octWorkLoadCount" placeholder="Oct WorkLoad Count" style="float: right;"><br><br><br><br>
          <label style="float: left;"><b>Nov WorkLoad Count:</b></label>
          <input type="text" name="novWorkLoadCount" placeholder="Nov WorkLoad Count" style="float: right;"><br><br><br><br>
          <label style="float: left;"><b>Dec WorkLoad Count:</b></label>
          <input type="text" name="decWorkLoadCount" placeholder="Dec WorkLoad Count" style="float: right;"><br><br><br><br>
		  <center><input type="submit" Value="Submit"></center>
		  </td></tr>
          </table>
		  <br><br><br> <br> <br> <br> <br> <br> <br> <br> <br> <br>
		  <footer>European Patent Office</footer>
		  </div>
        </form:form>
		
    </body>
</html>