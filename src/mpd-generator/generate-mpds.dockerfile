FROM kingdomcreation/ffmpeg-mp4box:latest

COPY ./video_files .

CMD ["bash","generate_mpds"]