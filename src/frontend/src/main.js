
import { createI18n } from 'vue-i18n'
/* import the fontawesome core */
// import { library } from '@fortawesome/fontawesome-svg-core'

// /* import font awesome icon component */
// import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'

// /* import specific icons */
// import { faPhone } from '@fortawesome/free-solid-svg-icons'
// import { faEnvelope } from '@fortawesome/free-solid-svg-icons'

// /* add icons to the library */
// library.add(faPhone)
// library.add(faEnvelope)

// import 'bootstrap/dist/css/bootstrap.css'
// import 'bootstrap-vue-3/dist/bootstrap-vue-3.css'


import router from "./router"
import App from "./App.vue"
import { createApp } from 'vue'
import BootstrapVue3 from "bootstrap-vue-next";
import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue-next/dist/bootstrap-vue-next.css";

// We define the app urls, the processing backend and the video server backend
let appUrls = await fetch('/app_urls.json')
let appUrlsJson = await appUrls.json()
window.app_urls = appUrlsJson;

const app = createApp(App)

app.use(router)
app.use(BootstrapVue3)
app.mount('#app')


