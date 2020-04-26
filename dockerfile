FROM python:3.8-slim-buster
EXPOSE 8080

COPY ./ /site 
WORKDIR /site
RUN apt-get update \
    && apt-get -y upgrade \
    && apt-get install -y gcc libxml2-dev libxslt1-dev zlib1g-dev \
    && pip install --no-cache-dir -r requirements.txt \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/* \
    && find /usr/local -depth \
        \( \
            \( -type d -a \( -name test -o -name tests \) \) \
            -o \
            \( -type f -a \( -name '*.pyc' -o -name '*.pyo' \) \) \
        \) -exec rm -rf '{}' +

STOPSIGNAL SIGTERM
CMD ['/site/start.sh']