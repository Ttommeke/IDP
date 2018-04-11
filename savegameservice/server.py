from flask import Flask
from flask import jsonify
from flask import request
from flask_pymongo import PyMongo
import os
app = Flask(__name__)

app.config['MONGO_DBNAME'] = os.environ['MONGODB_DATABASE']
app.config['MONGO_URI'] = 'mongodb://mongodb:27017/'

mongo = PyMongo(app)

#http://www.bogotobogo.com/python/MongoDB_PyMongo/python_MongoDB_RESTAPI_with_Flask.php

@app.route('/')
def hello_world():
    return 'Hello World!'


@app.route('/getsavegame/<ownerid>', methods=['GET'])
def get_owner_savegames(ownerid):
    savegames = mongo.db.savegames
    r = savegames.find_one({'ownerId': ownerid})
    if r:
        output = {'ownerId': r['ownerId'], 'savegame': r['savegame']}
    else:
        savegame_id = savegames.insert({'ownerId': ownerid, 'savegame': {}})
        new_savegame = savegames.find_one({'_id': savegame_id})
        output = {'ownerId': new_savegame['ownerId'], 'savegame': new_savegame['savegame']}

    return jsonify({'result': output})


@app.route('/addsavegame', methods=['POST'])
def addsavegameforowner():
    savegames = mongo.db.savegames
    r = savegames.find_one({'ownerId': request.json['ownerId']})
    if r:
        savegames.update_one({'_id': r['_id']}, {'$set': {'savegame': request.json['savegame']}})
        savegame_id = r['_id']
    else:
        savegame_id = savegames.insert({'ownerId': request.json['ownerId'], 'savegame': request.json['savegame']})

    new_savegame = savegames.find_one({'_id': savegame_id})
    output = {'ownerId': new_savegame['ownerId'], 'savegame': new_savegame['savegame']}
    return jsonify({'result': output})


if __name__ == '__main__':
    print("having fun...");
    app.run(debug=True, host='0.0.0.0', port=int(os.environ["PORT"]))
