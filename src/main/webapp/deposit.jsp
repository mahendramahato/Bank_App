<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.*" %>



<html>

<style>
#wrapper{
	text-align:center;
	display: flex;
	justify-content: center;
	flex-direction: column;
}
body { background-color: powderblue; }

.center{
	margin-left:auto;
	margin-right:auto;
}
</style>

<head>
<title>Transaction</title>
<script>
    function toggleChequeField() {
        var type = document.getElementById("transactionType").value;
        var chequeDiv = document.getElementById("chequeField");
        chequeDiv.style.display = (type === "deposit") ? "block" : "none";
    }
</script>
</head>
<body>

 <div id="wrapper" class="container">
     <h2>Transaction</h2>

     <form method="post" action="depo">

         <label for="transactionType">Transaction Type</label>
         <select name="transactionType" id="transactionType" onchange="toggleChequeField()" required>
             <option value="">-- Select --</option>
             <option value="deposit" name="transactionType">Deposit</option>
             <option value="withdraw" name="transactionType">Withdraw</option>
         </select>

         <label for="amount">Amount</label>
         <input type="number" name="amount" id="amount" min="1" step="0.01" required />

         <div id="chequeField" style="display: none;">
             <label for="chequeNo">Cheque Number</label>
             <input type="text" name="chequeNo" id="chequeNo" />
         </div>

         <label for="description">Description</label>
         <textarea name="description" id="description" rows="3" placeholder="Optional description..."></textarea>

           <br><br>
         <button type="submit">Submit</button>
     </form>

     <% String message = request.getParameter("message");
        if (message != null) { %>
         <p style="color: green; margin-top: 20px;"><%= message %></p>
     <% } %>
 </div>

</body>
</html>

