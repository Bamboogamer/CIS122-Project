import sqlite3

"""
Using SQLite directly in Python to store top 5/10 user-score data.
SQL lets us work with a relational database efficiently
 """

# defining connector & cursor
# Setting up the connection with simonSays database;
# If database.db doesn't exist, it'll be created
# path in my laptop; needs to be changed
db = sqlite3.connect("c:\\Users\\Jiwon Kim\\Desktop\\Fall 2020\\CIS122\\simonSays.db")
# # Obtaining a cursor object from the connection object(db)
csr = db.cursor()

def createTable():
    # a method to create a table scores to keep track of top 5 high scores
    csr.execute("CREATE TABLE IF NOT EXISTS scores(name TEXT, score INTEGER)")

def newHighScore(newName, newScore):
    # a method to update a new high score in scores table
    csr.execute("INSERT INTO scores (name, score) VALUES (?, ?)", (newName, newScore))

def showTopFive():
    """
    a method that shows top five user-score in descending order
    :return a list with 5 tuples - in (name, score) format:
    """
    # Showing all the components in descending order here for now
    topFive = list(csr.execute("SELECT name, score FROM scores ORDER BY score DESC LIMIT 5"))
    return topFive

def close():
    db.commit() # a method to commit to the changes made in the database
    db.close()  # a method to close SQL connection

# Optional method for now
def deleteScore(name, score):
    # a method to delete the row with the lowest score from scores table
    # by looking up minimum score value & deleting that row
    csr.execute("DELETE FROM scores WHERE score = (SELECT min(score) FROM scores)")

# Optional method for now
def findPlayer(playerName):
    """
    a method to check if playerName has been stored in database
    :param playerName we are trying to find:
    :return True if the name is already in the database
            False if the name is not in the database:
    """
    csr.execute("SELECT name FROM scores", playerName)
