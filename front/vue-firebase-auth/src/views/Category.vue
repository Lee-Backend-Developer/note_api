<template>
    <main>
      <div class="container">
          <form>
              <div class="mb-3">
                  <h4 for="exampleInputEmail1" class="form-label">목록 만들기</h4>
                  <input type="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" v-model="name">
              </div>
              <button @click.self.prevent="categoryAdd" type="submit" class="btn btn-primary">만들기</button>
          </form>

          <div>
              <h4 class="mt-3">생성된 카테고리</h4>
              <ul>
                  <li v-for="category in categorios" :key="category.categoryId">
                      {{ category.name }}
                      <button type="button" class="btn btn-outline-secondary" @click="categoryDelete(category.categoryId)">삭제</button>
                  </li>
              </ul>
          </div>
      </div>
    </main>
</template>

<script setup>
import axios from "axios";
import { onMounted, ref } from "vue"
import router from "@/router";

let name = ref("");
let categorios = ref([]);

onMounted(() => {
    console.log("onMounted => ", categorios);
    categoryGet();
});

function categoryAdd() {
    let request = {
        name : name.value,
    }
    axios.post("/api/category/add", request);
    router.go(0);
}

function categoryGet(){
    axios.get("/api/category")
        .then(function (response) {
            let data = response.data;
            for(let category of data) {
                let categoryObj = {
                    categoryId: category.categoryId,
                    name: category.name
                };
                categorios.value.push(categoryObj);
            }
        });
}

function categoryDelete(categoryId) {
    axios.delete("/api/category/" + categoryId + "/delete");
    router.go(0);
}

</script>