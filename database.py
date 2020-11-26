import sqlite3

"""
Using SQLite directly in Python to store top 5/10 user-score data.
SQL lets us work with a relational database efficiently
Using parameterized queries to prevent SQL injection 
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
    table = "scores"
    fields = "(name TEXT, score INTEGER)"

    sql_query = (f"CREATE TABLE IF NOT EXISTS {table} {fields}")
    csr.execute(sql_query)
    db.commit()

def newHighScore(newName, newScore):
    """
    a method to insert new high score in scores table
    :param newName, newScore:
    :return: None
    """
    table = "scores"
    fields = "(name, score)"

    sql_query = (f"INSERT INTO scores {fields} "
                 f"VALUES (?, ?)")
    csr.execute(sql_query, (newName, newScore))
    db.commit()

def showTopFive():
    """
    a method that selects top five user-score in descending order, sorted by score
    :param: None
    :return: a list with 5 tuples - in (name, score) format:
    """
    table = "scores"
    fields = "name, score"
    order = "score"
    condition = "DESC LIMIT 5"

    sql_query = (f"SELECT {fields} "
                 f"FROM {table} "
                 f"ORDER BY {order} "
                 f"{condition}")

    players = [x for x in csr.execute(sql_query)]
    return players

def close():
    db.commit() # a method to commit to the changes made in the database
    db.close()  # a method to close SQL connection

# Optional method for now
def deleteLowestScore():
    """
    a method to delete the row with the lowest score from scores table
    by looking up minimum score value
    :param: None
    :return: None
    """
    table = "scores"
    condition = "(SELECT min(score) FROM scores)"
    sql_query = (f"DELETE FROM {table} "
                 f"WHERE score = {condition}")
    csr.execute(sql_query)
    db.commit()

def findPlayer(name):
    """
    a method to check if playerName has been stored in database
    :param name: playerName we are trying to find
    :return: True if the name is already in the database
            False if the name is not in the database
    """
    table = "scores"
    field = "name, score"
    condition = "name = ?"

    sql_query = (f"SELECT {field} "
                 f"FROM {table} "
                 f"WHERE {condition}")

    players = [x for x in csr.execute(sql_query, (name,))]

    if len(players) == 0:   # If a row with name is NOT found in the db
        return False
    else:                   # If a row with name is found
        return True
