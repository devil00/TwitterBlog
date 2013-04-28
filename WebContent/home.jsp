<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ page import="com.ConnectionUtils" %>
<%@page import="java.sql.*" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>home</title>
<script language="JavaScript" type="text/javascript">
function showLogout(){
 if(document.getElementById('showDiv').style.display == 'none'){
        document.getElementById('showDiv').style.display = 'block';
    }else{
        document.getElementById('showDiv').style.display = 'none';
    }

} 
function hideLogout(){
 if(document.getElementById('showDiv').style.display == 'block'){
        document.getElementById('showDiv').style.display = 'none';
    }else{
        document.getElementById('showDiv').style.display = 'none';
    }

} 
</script>
<style type="text/css">
body{
	background-image: url("Images/twitter_home_bg.png");
	background-image: url("");
}

ul#Menu li{
	list-style-type:none;
margin:0;
padding:0;
display: inline;
}
#blog-list{
	position:relative;
	top:30px;
	left:900px;
	width:300px;
	height:300px;
	
}
#tweet-compose{
   position:relative;
	float:left;
   	 
}
#follower-list{
	position:absolute;
	top:40px;
	left:0px;
	
}
#footer{
position:relative;
top:200px;	
left:200px;
}
</style>

</head>
<body>
<body>
<div class='wrapper'>
     <%! String follow_sql="select id,userName from blog group by id desc limit 2"; %>
   
<div style="width:800px; margin:0 auto;">
        <div style="position:relative; width:600px; padding:10px; float:left; border:2px solid blue;">
        <div style="float:left;" onmouseover="showLogout();">Welcome <%= session.getAttribute("user") %></div>
        <div style="float:left; margin-left:15px;" onmouseout="hideLogout();" >></div>  

    </div>
    <div id="showDiv" style="display:none; float:right; width:150px; height:50px; position:absolute; margin-top:40px; border:1px solid red; "  >
        <div style="float:right;"><input type="submit" value="Settings" /></div>
        <div style="clear:both;"></div>
        <div style="float:right;"><form method="get" action="LogoutAction" name= "LogoutForm"><input type="submit" value="Sign Out" /></form></div>
</div>
 </div>
    <div id="blog-list">
    <b>Tweets</b>
    <p>Let's play with Twitter</p>
    <%  try {
      int updateQuery=0;
      Connection conn=ConnectionUtils.getConnection();
      int userId=(Integer)session.getAttribute("userId");
      String usr=(String)session.getAttribute("user");
      PreparedStatement psmt = null;   
      String blog=request.getParameter("user_tweet");
      if(blog!=null){
      	if(blog!="")
      	{      
      	 	String sql="update blog set blog = ? where id=? ";
      		 psmt=conn.prepareStatement(sql);
      	     psmt.setString(1, blog);
      	     psmt.setInt(2, userId);
      	     updateQuery=psmt.executeUpdate();  	     
      	}
      }	  
    if(updateQuery!=0) {  
      Statement stmt=conn.createStatement();
      ResultSet rs=stmt.executeQuery("select userName ,blog from blog where  userName in (" + session.getAttribute("followers")+")" + " or id = " + userId ); 
      while (rs.next()){ 
       if(rs.getString("blog")!=null)   {       %>
      <p><%= rs.getString("userName") %> : <%= rs.getString("blog") %></p>  
     <%   }}} conn.close(); } catch(Exception e){e.printStackTrace();} %>
     
     <% try{ Connection conn =ConnectionUtils.getConnection(); 
            Statement stmt = conn.createStatement();
            int userId=(Integer)session.getAttribute("userId");
            PreparedStatement psmt= conn.prepareStatement("update blog set follower = ? where id =?");
            String [] twUsers = request.getParameterValues("users_follow");
            String tusers="";
            for(String a : twUsers){
            	tusers=tusers+ "'"+a +"'" +",";
            	}
            
             tusers=tusers.substring(0,tusers.length()-1);
             psmt.setString(1, tusers);
             psmt.setInt(2, userId);
             int updateStatus= psmt.executeUpdate();
             session.setAttribute("followers", tusers);
             String sql="select userName ,blog from blog where  userName in (" + tusers+")" + " or id = " + userId;  
             ResultSet rs=stmt.executeQuery(sql);
             while(rs.next()){  if (rs.getString("blog")!=null) {%>
        <p> <%= rs.getString("userName") %> : <%= rs.getString("blog") %></p> <% }} conn.close();} catch(Exception ex){ex.printStackTrace();} %>        
            	     
    </div>
    <div id="tweet-compose"><form action="home.jsp" method="post"> <p><span style="color:blue;font-weight:bold;font-family:"MS Sans Serif", Geneva, sans-serif" >Compose your tweet</span></p><textarea name="user_tweet" cols="30" rows="5" placeholder="Enter your tweet"></textarea> <br/><input type="submit" value="Tweet"></form> </div>
    <div id="follower-list">
    <h3>Who to Follow </h3>
    <form action="home.jsp" method="post" >
    <%   try {Connection conn =ConnectionUtils.getConnection();
    Statement stmt=conn.createStatement();
    ResultSet rs =stmt.executeQuery(follow_sql);
    while (rs.next()) { %> 
     <input type="checkbox" name="users_follow" value= <%=rs.getString("userName")%> /> <%=rs.getString("userName") %> <% } conn.close();}  catch(Exception e) {e.printStackTrace();} %> <input type="submit" value="Follow" /></form>
    </div>
    <div id="footer">
    <img src="Images/footerBar.png" width="100" height="6">
<ul id='Menu'>
      <li><span class="copyright">&copy; 2013 Twitter</span></li></ul>
    </div>
</div>


</body>
</html>