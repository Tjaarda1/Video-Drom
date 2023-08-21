<template>
  <div>
    <video ref="videoPlayer" class="video-js"></video>
  </div>
</template>

<script>
import 'video.js/dist/video-js.css'
import videojs from 'video.js'
import 'dashjs'
import 'videojs-contrib-dash'

export default {
  name: 'VideoPlayer',
  
  data() {
    return {
      player: null,
      movie: "",
      options:{
        autoplay:'any',
        fluid:true,
        preferFullWindow: true,
        sources: [{}],
        controls: true,

      }
    }
  },
  mounted() {
    this.movie= this.$route.query.movieManifest + '.mpd';
    let tmp = `${window.app_urls.VIDEO_SERVER_URL}/` + this.movie;
    this.options.sources = [
          {
            src: `${window.app_urls.VIDEO_SERVER_URL}/${this.$route.query.movieManifest}.mpd`,
              type: 'application/dash+xml'
          }]
    this.player = videojs(this.$refs.videoPlayer, this.options, () => {
      this.player.log('onPlayerReady', this);
    });
  },
  beforeDestroy() {
    if (this.player) {
      this.player.dispose();
    }
  }
}
</script>


<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  display: inline-block;
  margin: 0 10px;
}

a {
  color: #42b983;
}
</style>