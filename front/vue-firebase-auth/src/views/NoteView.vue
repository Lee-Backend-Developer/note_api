<script setup>
import { onMounted, ref } from "vue";
import axios from "axios";
import router from "@/router";

const showModal = ref(false);
const newNote = ref("");
const errorMessage = ref("");
const notes = ref([]);
let categorios = ref([]);
let categoryId;


onMounted(() => {
  getNotes();
  categoryGet();
});

function getNotes() {
  const url = "/api/note";
  axios
    .get(url)
    .then(function (response) {
      for (let obj of response.data) {
        let noteObj = {
          id: obj.id,
          content: obj.content,
          category: obj.category,
        };
        notes.value.push(noteObj);
      }
    })
    .catch(function (error) {
      console.log(error);
      let response = error.response.data;
      if (response.code == 400) {
        console.log(response.message);
        alert(response.message);
      }
      console.log(error);
    });
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

const addNote = () => {
  if (newNote.value.length < 10) {
    return (errorMessage.value = "Note must be at least 10 characters long!");
  }

  const url = "/api/note/write";
  const request = {
    content: newNote.value,
    categoryId: categoryId,
  };
  axios
    .post(url, request)
    .then(function (response) {
      console.log("메모를 성공적으로 등록됨", response);
      router.go(0);
    })
    .catch(function (error) {
      console.log("메모를 전송 실패", error);
    });
  showModal.value = false;
  newNote.value = "";
  errorMessage.value = "";
};
function categoryChange(event) {
    categoryId = event.target.value;
}
</script>
<template>
  <main>
    <div v-if="showModal" class="overlay">
      <div class="modal">
          <select class="form-select" aria-label="Default select example" @change="categoryChange($event)">
              <option selected>카테고리 선택</option>
              <option v-for="category in categorios"
                      :key="category.categoryId"
                      :value="category.categoryId">
                  {{ category.name }}
              </option>
          </select>
        <textarea
          v-model.trim="newNote"
          name="note"
          id="note"
          cols="30"
          rows="10"
        ></textarea>
        <p v-if="errorMessage">{{ errorMessage }}</p>
        <button @click="addNote">Add Note</button>
        <button class="cancel" @click="showModal = false">Cancel</button>
      </div>
    </div>
    <div class="container">
      <header>
        <h1>Notes</h1>
        <button @click="showModal = true">+</button>
      </header>

      <div class="cards-container">
        <div
          v-for="note in notes"
          :key="note.id"
          class="card"
          :style="{ backgroundColor: note.backgroundColor }"
        >
          <p class="main-text">{{ note.content }}</p>
        </div>
      </div>
    </div>
  </main>
</template>

<style scoped>
main {
  height: 100vh;
  width: 100vw;
  top: 58% !important;
}

.container {
  max-width: 1000px;
  padding: 10px;
  margin: 0 auto;
  position: relative;
}

header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

h1 {
  font-weight: bold;
  margin-bottom: 25px;
  font-size: 75px;
}

button {
  border: none;
  padding: 10px;
  width: 50px;
  height: 50px;
  cursor: pointer;
  background-color: black;
  border-radius: 100%;
  color: white;
  font-size: 20px;
}

.card {
  width: 225px;
  height: 100%;
  min-height: 225px;
  background-color: rgb(237, 182, 44);
  padding: 20px;
  gap: 10px;
  border-radius: 2px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  margin-right: 20px;
  margin-bottom: 20px;
  word-wrap: break-word;
}

.date {
  font-size: 12px;
  font-weight: bold;
}

.cards-container {
  display: flex;
  flex-wrap: wrap;
}

.overlay {
  position: absolute;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 10;
  display: flex;
  align-items: center;
  justify-content: center;
}

.modal {
  width: 750px;
  background-color: white;
  border-radius: 2px;
  padding: 20px;
  position: relative;
  display: flex;
  flex-direction: column;
}

.modal button {
  padding: 10px 20px;
  font-size: 20px;
  width: 100%;
  background-color: black;
  border: none;
  border-radius: 2px;
  color: white;
  cursor: pointer;
  margin-top: 15px;
}

.modal .cancel {
  background-color: white;
  color: black;
  border: 2px solid black;
  margin-top: 8px;
}

.modal p {
  color: darkred;
  margin-top: 8px;
}

</style>
