import { createRouter, createWebHistory } from 'vue-router'
import Admin from './views/admin/admin.vue'
import About from './views/about.vue'
import NotFound from './views/404.vue'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Admin
  },
  {
    path: '/sample',
    name: 'Sample',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "sample" */ './views/sample/sample.vue')
  },
  {
    path: '/about',
    name: 'About',
    component: About
  },
  {
    path: '/:pathMatch(.*)',
    component: NotFound
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
