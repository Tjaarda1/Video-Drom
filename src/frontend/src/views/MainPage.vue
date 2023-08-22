<template>
<div class="container">
    <div class="row" style="padding-top:2em">
        <div  class="col-sm" v-for="(movie,index) in movies">
            <b-card  :title=movie.title :img-src=movieImage(movie) img-alt="Image" img-top tag="article" style="width: 20em; cursor: pointer" class="mb-2 " @Click="watchMovie(movie.title)" >
                <b-card-text class="truncate" >
                    <p >{{ movie.description }}</p>
                    
                </b-card-text>
                <!-- <b-button  variant="primary" @Click="watchMovie(movie.uniqueKey)">Watch this movie</b-button> -->
            </b-card>
        </div>
    </div>
</div>
</template>

<script>
import axios from "axios";



export default {
	name: "MainPage",
	components: {},
	data() {
		return {
			//All requirements from the database
			movies: [],
            tmp: []
		}
	},
	computed: {
		// Returns the requirements with active version enabled
		    activeRequirements(){
			    return this.movies;
		    }
	},
	
	
	methods: {
		// Calls save requirement or update requirement depending on the option chosen in the save button
		hardcodeMovies(){
            this.movies.push(
                {
                    id:1,
                    title: "Big Buck Bunny",
                    image: "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c5/Big_buck_bunny_poster_big.jpg/800px-Big_buck_bunny_poster_big.jpg",
                    description: "Big Buck Bunny es un corto animado del Instituto Blender; el cual es parte de la Fundación Blender.Como la película previa de la fundación, Elephants Dream, esta película se ha realizado usando software libre. El trabajo comenzó en octubre de 2007 y la película se estrenó del 10 de abril de 2008 en Ámsterdam."
                },
                {
                    id:2,
                    title: "Lord of the rings",
                    image: "https://m.media-amazon.com/images/M/MV5BMzgyNjdjOWMtMjAyYy00NzQ4LWIwYTQtZDk2ZDQzYWVlN2IwXkEyXkFqcGdeQXVyMTYzMDM0NTU@._V1_.jpg",
                    description: "La trilogía cinematográfica de El Señor de los Anillos, basada en la novela homónima del escritor británico J. R. R."
                },
                {
                    id:3,
                    title: "Star wars",
                    image: "https://static.posters.cz/image/750/posters/star-wars-classic-i153628.jpg",
                    description: "Star Wars, conocida también en español como La guerra de las galaxias, ​​​ es una franquicia y universo compartido de fantasía compuesta primordialmente de una serie de películas concebidas "
                },
        )
        },
        watchMovie(uniqueKey){
            console.log(uniqueKey)
            this.$router.push({path: '/watchMovie', query:{movieManifest: uniqueKey} });
        },
        injectImageSourceUrl(){
            // var imagePaths = this.movies.map(movie => movie.imagePath + 'lo');
            // console.log(imagePaths)
            this.movies= this.movies.map(movie => {
                 return { ...movie, imagePath: `${window.app_urls.VIDEO_SERVER_URL}/${movie.imagePath}` };
            });
        },
        movieImage(movie) {
            console.log(movie.uniqueKey)
            return `${window.app_urls.BACKEND_URL}/images/${movie.uniqueKey}`;
        },
        defaultImages(){
            // var imagePaths = this.movies.map(movie => movie.imagePath + 'lo');
            // console.log(imagePaths)
            this.movies= this.movies.map(movie => {
                 return { ...movie, imagePath:`${window.app_urls.BACKEND_URL}/images/${movie.uniqueKey}`};
            });
        },
        async getMovies(){
            let response = await axios.get(`${window.app_urls.BACKEND_URL}/movies`);
            this.movies= response.data
           
        }
		
	},
	async mounted() {
        await this.getMovies();
        // this.hardcodeMovies();
        //this.injectImageSourceUrl();
        this.defaultImages();
	},
}
</script>

<style>
/* https://css-tricks.com/line-clampin/ */

.text-truncate-container {
    width: 250px;
    overflow: hidden;

}
.text-truncate-container p {
    display: -webkit-box;
    -webkit-line-clamp: 3;
    -webkit-box-orient: vertical;
}
.truncate{
  display: -webkit-box;
  -webkit-line-clamp: var(--line-clamp, 3);
  -webkit-box-orient: vertical;
  word-break: var(--word-break, 'none');
  overflow: hidden;
  hyphens: auto;
  text-align: var(--align, left);
  

}

</style>
