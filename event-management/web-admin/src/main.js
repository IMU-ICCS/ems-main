
// Initialize global event bus
import mitt from 'mitt'
export const eventBus = mitt()

// Initialize Vue App
import { createApp } from 'vue'
import App from './App.vue'
import router from './router.js'

const app = createApp(App)
app.use(router)
app.mount('#app')
