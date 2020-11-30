cd C:\Users\abelm\Documents\flask_app
py -m venv env
CALL env\Scripts\activate
pip install flask
cd C:\Users\abelm\eclipse-workspace\ProjectPython\databasepackage
set FLASK_APP=databaseweb.py
set FLASK_ENV=development
flask run