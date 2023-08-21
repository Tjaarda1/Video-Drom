<template>
    <div style="padding:2em"  class="d-flex align-items-centre flex-column">
        <h1  class="mb-auto p-2" >Upload your files to the server.</h1>
        <b-form-group
            class="mb-auto p-0"
            label="The name of your movie"
            label-for="textarea-formatter"
            >

      <b-form-input
        id="textarea-formatter"
        v-model="movieEntry.title"
        placeholder="Enter your text"
      ></b-form-input>

    </b-form-group>
    <b-form-group
            label="A brief description of the movie"
            label-for="textarea-formatter"
            class="mb-0 p-2"            >
      <b-form-textarea
        id="textarea-formatter2"
        v-model="movieEntry.description"
        placeholder="Enter your text"
      ></b-form-textarea>
    </b-form-group>
        
        <FileInput v-model="movieFile" labelText="Select the movie file (.mp4)"  class="mb-auto p-2"/>
        <FileInput v-model="posterFile" labelText="Select the thumbnail/poster file (.png/.jpg)"  class="mb-auto p-2"/>
        <b-button  variant="primary" @Click="uploadMovie"  class="mb-auto p-2">Upload this movie</b-button>
    </div>
</template>
  
  <script>
  import axios from "axios";
  import FileInput from '../components/FileInput.vue'

    export default {
        components: {
            FileInput,
        },
        data() {
                return {
                    movieEntry: {
                        genre: 'INDIE',
                        description:"",
                        title:"",
                    },
                    movieFile: null,
                    posterFile:null,
                }
        },
        methods: {
            async uploadMovie(){
                const formData = new FormData();
                formData.append('mp4File', this.movieFile);

                formData.append('jpgFile', this.posterFile);

                // Add DTO object
                formData.append('movie', new Blob([JSON.stringify(this.movieEntry)], { type: 'application/json' }));
                
      
                await axios.post(`${window.app_urls.BACKEND_URL}/movies`, formData, {
                    headers: {
                        'Content-Type':  'multipart/form-data'
                    }
                })
                this.$router.push({path: '/' });
            }
        }
    }
  </script>