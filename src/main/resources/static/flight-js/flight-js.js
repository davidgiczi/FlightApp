/**
 * 
 */

if(document.getElementById("changePassMsg") != null){
	alert(document.getElementById("changePassMsg").value);
}

if(document.getElementById("needful-activation") != null)
alert(document.getElementById("needful-activation").value);

if(document.getElementById("activation-success") != null){
	alert(document.getElementById("activation-success").value);
}

if(document.getElementById("search-field") != null){	
document.getElementById("search-field").addEventListener("focus", setup);
document.getElementById("search-field").focus();
}
if(document.getElementById("mod_arr_date") != null){
	document.getElementById("mod_arr_date").value = getModifiedDate(document.getElementById("langSelect").value);
}

function setup(){
if(document.getElementById("search-value").value != null){
var searchValue = document.getElementById("search-value").value;
document.getElementById("search-field").value = searchValue;
}
}

function searchTicketByPassenger(){
	
	var str = document.getElementById("search-field").value;
	var lang = document.getElementById("langSelect").value.toLowerCase();
	location.href = location.origin + "/flight/ticket/search?text=" + str + "&lang=" + lang;
}

function searchTicketByAdmin(id){
	
	var str = document.getElementById("search-field").value;
	var lang = document.getElementById("langSelect").value.toLowerCase();
	location.href = location.origin + "/admin/ticket/search?text=" + str + "&lang=" + lang + "&id=" + id
}

function searchPassenger(){
	var str = document.getElementById("search-field").value;
	var lang = document.getElementById("langSelect").value.toLowerCase();
	location.href = location.origin + "/admin/passenger/search?text=" + str + "&lang=" + lang;
}

function cancelTicket(id){
	
	location.href = location.origin + "/flight/cancel?id=" + id;
}

function activateTicket(id){
	var lang = document.getElementById("langSelect").value.toLowerCase();
	location.href = location.origin + "/admin/activate?id=" + id + "&lang=" + lang + "&act=yes";
}

function deleteTicket(id){
	var lang = document.getElementById("langSelect").value.toLowerCase();
	var message = document.getElementById("deleteTicket").value;
	
	if(confirm(message)){
	
	location.href = location.origin + "/admin/ticket/delete?id=" + id + "&lang=" + lang;
	}
}

function enterUserAccount(id){
	
	location.href = location.origin + "/admin/enter?id=" + id;
}

function enabledUserAccount(id){
	location.href = location.origin + "/admin/enabled?id=" + id;
	}
	
function setRole(id){
	
	var role = document.getElementById("roleSelect").value;
	
	location.href = location.origin + "/admin/role?id=" + id+ "&role=" + role;
}

function deleteUser(id){
	var message = document.getElementById("deleteMessage").value;
	
	if(confirm(message)){
		
		location.href = location.origin + "/admin/passenger/delete?id=" + id;
		
	}
}

function goReservations(id){
	
	var lang = document.getElementById("langSelect").value.toLowerCase();
	location.href = location.origin + "/admin/reservations?id=" + id + "&lang=" + lang;
}

function goModifyPage(id){
	
	var lang = document.getElementById("langSelect").value.toLowerCase();
	location.href = location.origin + "/admin/ticket/getModifyTicket?id=" + id + "&lang=" + lang;
}

function cancelModifyingTicket(){
	
	var lang = document.getElementById("langSelect").value.toLowerCase();
	var id = document.getElementById("passengerId").value;
	location.href = location.origin + "/admin/reservations?id=" + id + "&lang=" + lang;
}


function getModifiedDate(language){
	
	var mod_dep_year = document.getElementById("mod_dep_year").value;
	var mod_dep_month = document.getElementById("mod_dep_month").value;
	if(parseInt(mod_dep_month) < 10 ){
		mod_dep_month = "0" + mod_dep_month;
	}
	var mod_dep_day = document.getElementById("mod_dep_day").value;
	if(parseInt(mod_dep_day) < 10 ){
		mod_dep_day = "0" + mod_dep_day;
	}
	
	if("EN" == language){
		return mod_dep_day + "-" + mod_dep_month + "-" + mod_dep_year;
	}
	
	return mod_dep_year + "-" + mod_dep_month + "-" + mod_dep_day;
}

function setArrivalDate(){
	document.getElementById("mod_arr_date").value = getModifiedDate(document.getElementById("langSelect").value);
}


function sendModifiedTicket(){
	
	var dep_time = getModifiedDate("HU");
	var dep_place = document.getElementById("mod_dep_place").value;
	var arr_time = getModifiedDate("HU");
	var arr_place = document.getElementById("mod_arr_place").value;
	var flying_number = document.getElementById("mod_flying_number").value;
	var price = document.getElementById("mod_price").value;
	
	document.getElementById("dep_date").value = dep_time;
	document.getElementById("dep_place").value = dep_place;
	document.getElementById("arr_date").value = arr_time;
	document.getElementById("arr_place").value = arr_place;
	document.getElementById("flying_number").value = flying_number;
	document.getElementById("price").value = price;
	document.getElementById("modified_ticket_form").submit();
}

function changePassword(){
	
	var newPwd = prompt(document.getElementById("newPwdMsg").value);
	
	if(newPwd != null){
		
		if(isValidPwd(newPwd)){
			
			document.getElementById("changePass").value = newPwd;
			document.getElementById("changePassForm").submit();
			
		}
		else{
			alert(document.getElementById("newPwdError").value);
		}
		
	}
	
}

function getArrivalDate(){
	var date = document.getElementById("dep-date").value;
	document.getElementById("arr-date").value = date;
}
	
if(document.getElementById("usrnameExists") != null){
	alert(document.getElementById("usrnameExists").value);
}

if(document.getElementById("pass") !== null )
document.getElementById("pass").addEventListener("mouseover", showInfoMessage);

if(document.getElementById("pass") !== null)
document.getElementById("pass").addEventListener("mouseout", clearInfoMessage);

if(document.getElementById("uname") !== null){

document.getElementById("uname").addEventListener("blur", function validate(){

	if(isValidUsr()){
		document.getElementById("username-correct").innerHTML = document.getElementById("username-correct-msg").value;
		document.getElementById("username-error").innerHTML = "";
		document.getElementById("username-correct").style.color= "green";
	}
	else{
		document.getElementById("username-error").innerHTML = document.getElementById("username-error-msg").value;
		document.getElementById("username-correct").innerHTML = "";
		document.getElementById("username-error").style.color= "red";
	}
	
}
);
}

if(document.getElementById("pass") !== null){

document.getElementById("pass").addEventListener("blur", function validate(){

	if(isValidPwd()){
		document.getElementById("password-correct").innerHTML = document.getElementById("password-correct-msg").value;
		document.getElementById("password-error").innerHTML = "";
		document.getElementById("password-correct").style.color= "green";
	}
	else{
		document.getElementById("password-error").innerHTML = document.getElementById("password-error-msg").value;
		document.getElementById("password-correct").innerHTML = "";
		document.getElementById("password-error").style.color= "red";
	}
}
);
}

if(document.getElementById("passagain") !== null){

document.getElementById("passagain").addEventListener("blur", function validate(){

	if(isEqual()){
		document.getElementById("passwords-equal").innerHTML = document.getElementById("passwords-equal-msg").value;
		document.getElementById("passwords-non-equal").innerHTML = "";
		document.getElementById("passwords-equal").style.color= "green";
	}
	else{
		document.getElementById("passwords-non-equal").innerHTML = document.getElementById("passwords-non-equal-msg").value;
		document.getElementById("passwords-equal").innerHTML = "";
		document.getElementById("passwords-non-equal").style.color= "red";
	}
}
);
}

function setLanguage() {
	
	var lang = document.getElementById("langSelect").value.toLowerCase();
		
	if("/flight/reg" === location.pathname){
		location.href = "/login?lang=" + lang;
	}
	else if(location.href.includes("/admin/reservation")    ||
			location.href.includes("/flight/ticket/search") ||
			location.href.includes("/admin/passenger/search") ||
			location.href.includes("/admin/ticket/modify")
			){
		
		var url = location.href.split("&");
		location.href = url[0] + "&lang=" + lang;
	}
	else if(location.href.includes("/admin/ticket/search")){
		var url = location.href.split("&");
		location.href = url[0] + "&lang=" + lang + "&" + url[2];
	}
	else{
	
	location.href = "?lang=" + lang;
	}
}

function showInfoMessage(){
document.getElementById("password-info").innerHTML = document.getElementById("password-info-msg").value;
document.getElementById("password-info").style.color = "green";
}

function clearInfoMessage(){
	document.getElementById("password-info").innerHTML = "";
}

function isValidUsr() {
		
		var user = document.getElementById("uname").value;
		
		if(user === "") {
			return false;
		}
		if( !user.includes("@")) {
			return false;
		}
		
		var userNameComponents = user.split("@");
		
		if(userNameComponents.length >= 3 ) {
			return false;
		}
		
		if(userNameComponents[0].length < 3) {
			return false;
		}
		
		if( !userNameComponents[1].includes(".")) {
			return false;
		}
		
		var serverForMailComponents = userNameComponents[1].split(".");
		
		if(serverForMailComponents.length >= 3) {
			return false;
		}
		
		if(serverForMailComponents[0].length < 3) {
			return false;
		}
		
		if(serverForMailComponents[1].length < 2) {
			return false;
		}
		
		return true;
	}

function isValidPwd(pass){
	
	if(pass == null){
	 pass = document.getElementById("pass").value;
	}		
	if(pass.length < 8) {
			return false;
		}
		
	var upperCase = 0;

	for(var index = 0; index < pass.length; index++){
		for(var upperChar = 65; upperChar < 91; upperChar++){
		if(pass.charCodeAt(index) === upperChar){
			upperCase++;
		}	
	}
}
	if(upperCase < 2) {
			return false;
		}
	
	var digit = 0;
	
	for(var index = 0; index < pass.length; index++){
		for(var digitChar = 48; digitChar < 58; digitChar++){
		if(pass.charCodeAt(index) === digitChar){
			digit++;
		}	
	}
}
	
	if(digit < 1) {
			return false;
		}
	
	var lowerCase = 0;
	
	for(var index = 0; index < pass.length; index++){
		for(var lowerChar = 97; lowerChar < 123; lowerChar++){
		if(pass.charCodeAt(index) === lowerChar){
			lowerCase++;
		}	
	}
}
	
	if(pass.length - lowerCase - upperCase - digit < 1) {
			return false;
		}
	
	return true;
}

function isEqual(){
	if(document.getElementById("pass").value === document.getElementById("passagain").value){	
		return true;
	}
	else{
		return false;
	}
}

var isNonClicked = true;

function isOK(){
	
	
	 if(isValidUsr() && isValidPwd(null) && isEqual() && isNonClicked){
		document.getElementById("regForm").submit();
		document.getElementById("regist-submit-btn").disabled = true;
		isNonClicked = false;
	}
	
}
