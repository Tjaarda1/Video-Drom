#!/bin/bash

mkdir dash_videos;

movie=$1

declare -A availableConfs
availableConfs[480x270]=128k
availableConfs[640x360]=500k
availableConfs[960x540]=1M
availableConfs[1280x720]=3M
cd dash_videos;


echo "Converting: $movie"
#We iterate through all the specified resolutions
for resolution in "${!availableConfs[@]}"
do
    # First we make 4 different versions of the same video file. Excluding the audio, as the 4 of them will have the same audio file.
    # -i ../movie -> We specify that the input is the movie file (.mp4) corresponding to this loop iteration (example: -i SampleVideo.mp4)
    # -s $resolution -> size of the video image output. Example: 1280x720.
    # -b:v ${availableConfs[$resolution]} -> Video bit rate of the output file. Example 3M.
    # -g 60 -> GOP size. Large (we're in MPEG-4)
    # -an -> Disable audio (we do it later)
    # ${movie%.*}_${resolution}_${availableConfs[$resolution]}.mp4 -> output file name. çin our example it would be Samplevideo_1280x720_3M.mp4
    ffmpeg -i ../videos/$movie -s $resolution -b:v ${availableConfs[$resolution]} -g 60 -an ${movie%.*}_${resolution}_${availableConfs[$resolution]}.mp4
done
#Now we make the audio file separated, its shared among video versions of the same movie
# -c:a aac -> encode the audio with ac3 (delete no audible data)
# -b:a 128k -> output bitrate
# -vn Skip inclusion of video. (Just audio file)
ffmpeg -i ../videos/$movie -c:a aac -b:a 128k -vn ${movie%.*}_128k.mp4 

# Find all instances of the movie name, so we can put them in an array for inputting in the MP4Box command.
availableFiles=`find ${movie%.*}*`;

# -dash 10000 -> 10seconds fragments for the dash
# -rap -> ensure that segments begin with random access points, segment durations might vary depending on the source encoding.
# -profile simple -> TODO: research which profile to use 
# -mpd-title ${movie%.*} -> Movie title
# -out  ${movie%.*}.mpd -> Output mpd file name. Example -> SampleVideo.mpd.
# $availableFiles -> All the files inside the mpd. Example: Samplevideo_480x270_128k.mp4 Samplevideo_640x360_500k.mp4 Samplevideo_960x540_1M.mp4 Samplevideo_1280x720_3M.mp4

MP4Box -dash 5000 -rap -profile simple -mpd-title  ${movie%.*} -out  ${movie%.*}.mpd $availableFiles;



