FROM kingdomcreation/ffmpeg-mp4box:latest

# Install Golang 1.20
RUN apt-get update && apt-get install -y wget
RUN wget https://golang.org/dl/go1.20.linux-amd64.tar.gz
RUN tar -xvf go1.20.linux-amd64.tar.gz
RUN mv go /usr/local

# Set environment variables for Golang
ENV PATH="/usr/local/go/bin:${PATH}"
ENV GOPATH=/go
ENV GOBIN=/usr/local/go/bin


WORKDIR /usr/src/app

COPY go.mod ./

# DOWNLOAD GOLANG APP DEPENDENCIES
RUN go mod download && go mod verify

COPY . .

# BUILD GO APP
RUN go build -v -o /usr/local/bin/app ./...

# RUN GO APP
CMD ["app"]