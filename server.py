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
			sql =  "SELECT * from User;"
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
	street_address_1 = request.form['streetaddress1']
	street_address_2 =  request.form['streetaddress2']
	city = request.form['city']
	state = request.form['state']
	zip_code = request.form['zipcode']
	phone_number = request.form['phonenumber']
	connection = connect()
	try:
		with connection.cursor() as cursor:
			sql = "SELECT Username from User where Username = %s"
			cursor.execute(sql,(username))
			result = cursor.fetchone()
			if result != None:
				connection.close()
				return "User already exists"
		with connection.cursor() as cursor:
			sql = "INSERT INTO User (Name, Username, Password, StreetAddress1, StreetAddress2, City, State, Zipcode, PhoneNumber) VALUES (%s, %s, %s,%s, %s,%s, %s,%s, %s)"
			cursor.execute(sql,(name, username, password, street_address_1, street_address_2, city, state, zip_code, phone_number))
		connection.commit()
	finally:
		connection.close()

@app.route('/login')
def login():
	username = request.args["username"]
	password = request.args["password"]
	connection = connect()
	result = 0
	try:
		with connection.cursor() as cursor:
			sql = "SELECT * FROM User WHERE Username = %s AND Password = %s"
			cursor.execute(sql,(username,password))
			result = cursor.fetchone()
	except:
		connection.close()
	if result != None:
		return "True"
	else:
		return "False"

if __name__ == "__main__":
	app.run(host = "0.0.0.0", port = 80)