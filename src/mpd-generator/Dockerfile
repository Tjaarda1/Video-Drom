FROM golang:1.20

# INSTALL DEPENDENCIES FOR USING FFMPEG AND MP4BOX
RUN apt-get update -qq && apt-get -y install \
  autoconf \
  automake \
  build-essential \
  cmake \
  git-core \
  libass-dev \
  libfreetype6-dev \
  libgnutls28-dev \
  libmp3lame-dev \
  libsdl2-dev \
  libtool \
  libva-dev \
  libvdpau-dev \
  libvorbis-dev \
  libxcb1-dev \
  libxcb-shm0-dev \
  libxcb-xfixes0-dev \
  meson \
  ninja-build \
  pkg-config \
  texinfo \
  wget \
  yasm \
  zlib1g-dev \
  libunistring-dev  \
  libaom-dev \
  libdav1d-dev

# INSTALL FFMPEG FROM SOURCE FILES
RUN mkdir ~/ffmpeg_sources && \
cd ~/ffmpeg_sources && \
wget -O ffmpeg-snapshot.tar.bz2 https://ffmpeg.org/releases/ffmpeg-snapshot.tar.bz2 && \
tar xjvf ffmpeg-snapshot.tar.bz2 && \
cd ffmpeg && \
PATH="/bin:$PATH" PKG_CONFIG_PATH="/ffmpeg_build/lib/pkgconfig" ./configure \
  --prefix="/ffmpeg_build" \
  --pkg-config-flags="--static" \
  --extra-cflags="-I/ffmpeg_build/include" \
  --extra-ldflags="-L/ffmpeg_build/lib" \
  --extra-libs="-lpthread -lm" \
  --ld="g++" \
  --bindir="/bin" \
  --enable-gpl \
  --enable-gnutls \
  --enable-libaom \
  --enable-libass && \
PATH="/bin:$PATH" make && \
make install && \
hash -r

# INSTALL GPAC FOR MP4BOX

# Install dependencies
RUN apt-get -y install zlib1g-dev

RUN cd ~ &&\
  git clone https://github.com/gpac/gpac.git gpac_public &&\
  cd gpac_public &&\
  ./configure --static-bin &&\
  make &&\
  make install

# INSTALL DOS2UNIX FOR FORMATTING THE BASH FILE
RUN apt-get -y install dos2unix 

WORKDIR /usr/src/app

COPY go.mod ./

# DOWNLOAD GOLANG APP DEPENDENCIES
RUN go mod download && go mod verify

COPY . .

# FORMATTING THE BASH FILE THAT WILL BE EXECUTED FROM THE GO SERVER
RUN dos2unix generate_mpd.sh

# BUILD GO APP
RUN go build -v -o /usr/local/bin/app ./...

# RUN GO APP
CMD ["app"]