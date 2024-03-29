import { createRouter, createWebHistory } from "vue-router";
import NoteView from "../views/NoteView.vue";
import LoginView from "../views/LoginView.vue";
import Category from "../views/Category.vue";

const routes = [
  {
    path: "/",
    name: "login",
    component: LoginView,
  },
  {
    path: "/register",
    name: "register",
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/RegisterView.vue"),
  },
  {
    path: "/dashboard",
    name: "dashboard",
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/DashboardView.vue"),
    meta: {
      authRequired: true,
    },
  },
  {
    path: "/notes",
    name: "notes",
    component: NoteView,
  },
  {
    path: "/category",
    name: "Category",
    component: Category
  }
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
