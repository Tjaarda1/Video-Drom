import * as VueRouter from "vue-router"

import MainPage from "./views/MainPage.vue";
import WatchMovie from "./views/WatchMovie.vue"
import Upload from "./views/UploadMovie.vue"


export const routes = [
    { path: '/', name: "Home",  component: MainPage },
    { path: '/watchMovie', name: "Movie",  component: WatchMovie },
    { path: '/upload', name: "Upload",  component: Upload },
]

const router = VueRouter.createRouter({
    history: VueRouter.createWebHashHistory(),
    routes
})


export default router