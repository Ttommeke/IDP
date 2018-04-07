FROM python

COPY . .

ENV LD_LIBRARY_PATH=/usr/lib/

RUN pip install flask
RUN pip install PyMongo
RUN pip install flask_pymongo

CMD ["python", "server.py"]
