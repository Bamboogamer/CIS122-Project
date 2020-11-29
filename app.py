"""
Jiwon Kim
Using SQLite directly in Python to store top 5/10 user-score data.
 """
import sqlite3
from flask import Flask, jsonify
app = Flask(__name__)  # name of the current module

def createTable(csr):
    # a method to create a table scores to keep track of top 5 high scores
    csr.execute("CREATE TABLE IF NOT EXISTS scores (name TEXT, score INTEGER)")
    db.commit()

@app.route('/user/<name>/score/<score>')
def nHS(name, score):
    # Setting up the connection with simonSays database;
    # path on my laptop; needs to be changed
    with sqlite3.connect("C:\\sqlite-jdbc\\simonSays.db") as db:
        csr = db.cursor()
        newHighScore(csr, name, score)
        db.commit()
        return "true"
 
def newHighScore(csr, name, score):
    """
    a method to insert new high score in scores table
    :param newName, newScore:
    :return: None
    """
    csr.execute("INSERT INTO scores (name, score) VALUES (?, ?)", (name, score))

@app.route('/showTopFive')
def sTF():
    with sqlite3.connect("C:\\sqlite-jdbc\\simonSays.db") as db:
        csr = db.cursor()
        db.commit()
        return jsonify(showTopFive(csr))

def showTopFive(csr):
    """
    a method that selects top five user-score in descending order, sorted by score
    :param: None
    :return: a list with 5 tuples - in (name, score) format:
    """
    topFive = list(csr.execute("SELECT name, score FROM scores ORDER BY score DESC LIMIT 5"))
    return topFive

@app.route('/closeDB')
def c():
    with sqlite3.connect("C:\\sqlite-jdbc\\simonSays.db") as db:
        close()

def close():
    db.commit() # a method to commit to the changes made in the database
    db.close()  # a method to close SQL connection

@app.route('/delLowScore')
def dLS():
    with sqlite3.connect("C:\\sqlite-jdbc\\simonSays.db") as db:
        csr = db.cursor()
        delLowestScore(csr)
        db.commit()
        return "true"


def delLowestScore(csr):
    """
    a method to delete the row with the lowest score from scores table
    by looking up minimum score value
    :param: None
    :return: None
    """
    csr.execute("DELETE FROM scores WHERE score = (SELECT min(score) FROM scores)")

@app.route('/showLowestScore')
def sLS():
    with sqlite3.connect("C:\\sqlite-jdbc\\simonSays.db") as db:
        csr = db.cursor()
        return jsonify(showLowestScore(csr))

def showLowestScore(csr):
    """
    a method to check the lowest score in database
    :return: int - the lowest score
    """
    minScore = list(csr.execute("SELECT min(score) FROM scores"))
    return minScore[0]

@app.route('/find/<name>')
def fP(name):
    with sqlite3.connect("C:\\sqlite-jdbc\\simonSays.db") as db:
        csr = db.cursor()
        db.commit()
        return jsonify(findPlayer(csr, name))

def findPlayer(csr, name):
    """
    a method to check if playerName has been stored in database
    :param name: playerName we are trying to find
    :return: True if the name is already in the database
            False if the name is not in the database
    """
    player = list(csr.execute("SELECT name, score FROM scores WHERE name = ?", (name,)))

    if len(player) == 0:   # If a row with name is NOT found in the db
        return False
    else:                   # If a row with name is found
        return True
    

db = sqlite3.connect("C:\\sqlite-jdbc\\simonSays.db")
# Obtaining a cursor object from the connection object(db)
csr = db.cursor()
createTable(csr)
