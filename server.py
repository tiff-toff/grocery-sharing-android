from flask import Flask, request
import pymysql, pymysql.cursors

app = Flask(__name__)

def connect():
	host = "goshare-db.co7fxvpg0gtu.us-east-2.rds.amazonaws.com"
	port = 3306
	dbname = "goshare"
	user = "admin"
	password = "mypassword"
	return pymysql.connect(host, user=user,port=port,
                           passwd=password, db=dbname,
                           cursorclass = pymysql.cursors.DictCursor)

@app.route('/test')
def test():
	username = request.args["username"]
	password = request.args["password"]
	connection = connect()
	result = 0
	try:
		with connection.cursor() as cursor:
			sql = "INSERT INTO User (Username,Password) VALUES (%s,%s);"
			cursor.execute(sql,(username,password))
		connection.commit()
		with connection.cursor() as cursor:
			sql =  "SELECT Username,Password from User;"
			cursor.execute(sql)
			result = cursor.fetchone()
	finally:
		connection.close()
	if result == None:
		return "ohno"
	return result
@app.route('/')
def index():
	return "The backend of GroShare"

@app.route('/createacc', methods = ["POST"])
def createacc():
	username = request.form['username']
	password = request.form['password']
	first_name = request.form['firstname']
	last_name = request.form['lastname']
	name = first_name + last_name
	street_address_1 = request.form.get('streetaddress1')
	street_address_2 =  request.form.get('streetaddress2')
	city = request.form.get('city')
	state = request.form.get('state')
	zip_code = request.form.get('zipcode')
	phone_number = request.form.get('phonenumber')
	connection = connect()
	try:
		with connection.cursor() as cursor:
			sql = "SELECT Username from User where Username = %s"
			cursor.execute(sql,(username))
			result = cursor.fetchone()
			if result != None:
				return "Existing"
		with connection.cursor() as cursor:
			sql = "INSERT INTO User (Name, Username, Password, StreetAddress1, StreetAddress2, City, State, Zipcode, PhoneNumber) VALUES (%s, %s, %s,%s, %s,%s, %s,%s, %s);"
			cursor.execute(sql,(name, username, password, street_address_1, street_address_2, city, state, zip_code, phone_number))
		connection.commit()
	finally:
		connection.close()
	return "True"

@app.route('/login', methods = ["GET"])
def login():
	username = request.args["username"]
	password = request.args["password"]
	connection = connect()
	result = 0
	try:
		with connection.cursor() as cursor:
			sql = "SELECT * FROM User WHERE Username = %s AND Password = %s;"
			cursor.execute(sql,(username,password))
			result = cursor.fetchone()
	except:
		connection.close()
	if result != None:
		return "True"
	else:
		return "False"

@app.route('/createrequest', methods = ["POST"])
def createrequest():
	#Active: 0-completed, 1-accepted, 2-open
	#Type: 0 - Trip, 1 - Run
	username = request.form["username"]
	store = request.form["store"]
	storeaddress = request.form["storeaddress"]
	items = request.form["items"]
	active = 2
	request_type = int(request.form['type'])
	connection = connect()
	try:
		with connection.cursor() as cursor:
			sql = "INSERT INTO TripRequest (User, Store, StoreAddress Items, Active, Type) VALUES (%s,%s,%s,%s,%i,%i);"
			cursor.execute(sql,(username,store,storeaddress,items,active,request_type))
		connection.commit()
	finally:
		connection.close()
	return "True"

@app.route('/gettrips',methods = ["GET"])
def gettrips():
	connection = connect()
	try:
		with connection.cursor() as cursor:
			sql = "SELECT (User, Store, StoreAddress, Items, TripNumber) FROM TripRequest WHERE Active = 2 AND Type = 0;"
			cursor.execute(sql)
			results = cursor.fetchall()
	finally:
		connection.close()
	ret = ""
	for result in results:
		for i in result:
			ret += str(result[i]) + ","
		ret += ";"
	return ret

@app.route('/getruns',methods = ["GET"])
def getruns():
	connection = connect()
	try:
		with connection.cursor() as cursor:
			sql = "SELECT (User, Store, StoreAddress, Items, TripNumber) FROM TripRequest WHERE Active = 2 AND Type = 1;"
			cursor.execute(sql)
			results = cursor.fetchall()
	finally:
		connection.close()
	ret = ""
	for result in results:
		for i in result:
			ret += str(result[i]) + ","
		ret += ";"
	return ret

@app.route('/acceptrequest',methods = ["GET"])
def acceptrequest():
	acceptor = request.args["username"]
	tripnumber = request.args["tripnumber"]
	connection = connect()
	try:
		with connection.cursor() as cursor:
			sql = "SELECT * FROM TripRequest WHERE TripNumber = %s"
			sql = "UPDATE TripRequest SET Active = 1 WHERE TripNumber = %s"
			cursor.execute(sql,(tripnumber))
			result = cursor.fetchone()
		connection.commit()
		with connection.cursor() as cursor:
			sql = "INSERT INTO Accepted (Requester, Acceptor, TripNumber) VALUES (%s, %s, %s)"
			cursor.execute(sql,(username, result["username"], tripnumber))
			result = cursor.fetchone()
		connection.commit()
	finally:
		connection.close()
	return "True"
@app.route('/getuser',methods = ["GET"])
def getuser():
	username = request.args["username"]
	connection = connect()
	result = 0
	try:
		with connection.cursor() as cursor:
			sql = "SELECT * FROM User WHERE Username = %s;"
			cursor.execute(sql,(username))
			result = cursor.fetchone()
	finally:
		connection.close()
	info = result["Name"] + "," + result["PhoneNumber"] + "," + result["City"]
	return info

@app.route('/getrequest', methods = ["GET"])
def getrequest():
	tripnumber = request.args["tripnumber"]
	connection = connect()
	result = 0
	try:
		with connection.cursor() as cursor:
			sql = "SELECT * FROM TripRequest WHERE TripNumber = %s"
			cursor.execute(sql,(tripnumber))
			result = cursor.fetchone()
	except:
		connection.close()
	info = result["Store"] + "," + result["StoreAddress"] + "," + result["Items"]
	return info

@app.route('/completerequest', methods = ["GET"])
def completerequest():
	tripnumber = request.args["tripnumber"]
	connection = connect()
	try:
		with connection.cursor() as cursor:
			sql = "UPDATE TripRequest SET Active = 1 WHERE TripNumber = %s"
			sql = "DELETE FROM Accepted WHERE TripNumber = %s"
			cursor.execute(sql,(tripnumber))
			result = cursor.fetchone()
		connection.commit()
	except:
		connection.close()
	return 

@app.route('/cancelrequest', methods = ["GET"])
def cancelrequest():
	tripnumber = request.args["tripnumber"]
	connection = connect()
	try:
		with connection.cursor() as cursor:
			sql = "UPDATE TripRequest SET Active = 2 WHERE TripNumber = %s"
			sql = "DELETE FROM Accepted WHERE TripNumber = %s"
			cursor.execute(sql,(tripnumber))
			result = cursor.fetchone()
		connection.commit()
	except:
		connection.close()
	return
if __name__ == "__main__":
	app.run(host = "0.0.0.0", port = 80)