FROM python:3.8-slim-buster

COPY ./ /site 
RUN cd /site \
    && pip install -r --no-cache-dir requirements.txt

EXPOSE 8080
STOPSIGNAL SIGTERM
CMD ['cd /site && gunicorn -w 4 -b 0.0.0.0:8080 mysite.wsgi']