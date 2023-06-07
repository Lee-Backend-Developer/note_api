<script setup>
import {onMounted, ref, reactive} from "vue";
import axios from "axios";
import router from "@/router";

const showModal = ref(false);
const newNote = ref("");
const errorMessage = ref("");


onMounted(() => {
    getNotes();
    categoryGet();
});

const notes = ref([]);
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

function getNotes() {
    const url = "/api/note";
    axios
        .get(url)
        .then(function (response) {
            console.log(response);
            for (let obj of response.data) {
                console.log("note response => ", response);
                let noteObj = {
                    id: obj.noteId,
                    content: obj.content,
                    category: obj.category,
                    categoryId: obj.categoryId,
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

let categorios = ref([]);
let categoryId;
function categoryGet() {
    axios.get("/api/category")
        .then(function (response) {
            let data = response.data;
            for (let category of data) {
                let categoryObj = {
                    categoryId: category.categoryId,
                    name: category.name
                };
                categorios.value.push(categoryObj);
            }
        });
}

function categoryChange(event) {
    categoryId = event.target.value;
}

function deleteNote(noteId) {
    let url = "/api/note/"+noteId+"/delete";
    axios.delete(url);
    history.go(0);
}

const showEditNote = ref(false);
const content = ref("");
const category = ref("");
const editNoteObj = reactive({
    noteId: "",
    content: "",
    categoryId: "",
})
function editNote(noteId, content, categoryId) {
    console.log("noteId => ", noteId, " content => ", content, " categoryId =>", categoryId)
    showEditNote.value = !showEditNote.value;
    editNoteObj.noteId = noteId;
    editNoteObj.content = content;
    editNoteObj.categoryId = categoryId;
    console.log(editNoteObj);

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
        <div class="container-fluid">
            <header>
                <h1>Notes</h1>
                <button @click="showModal = true">+</button>
            </header>
            <div v-if="showEditNote" class="overlay">
                <div class="modal">
                    <select class="form-select"
                            aria-label="Default select example"
                            @change="categoryChange($event)" v-model="editNoteObj.categoryId">
                        <option selected>카테고리 선택</option>
                        <option v-for="category in categorios"
                                :key="category.categoryId"
                                :value="category.categoryId">
                            {{ category.name }}
                        </option>
                    </select>
                    <textarea
                        name="noteEdit"
                        id="noteEdit"
                        cols="30"
                        rows="10"
                        v-model="editNoteObj.content"
                    ></textarea>
                    <p v-if="errorMessage">{{ errorMessage }}</p>
                    <button @click="addNote">Edit Note</button>
                    <button class="cancel" @click="showEditNote = false">Cancel</button>
                </div>
            </div>
            <div class="container-fluid">
                <div class="row row-cols-auto">
                    <div class="col" v-for="note in notes" :key="note.id">
                        <div class="card" style="width: 18rem;">
                            <div class="card-body">
                                <h5 class="card-title">category: {{ note.category }}</h5>
                                <p class="card-text">{{ note.content }}</p>
                                <a href="#"
                                   class="btn btn-primary m-2"
                                   @click="deleteNote(note.id)">
                                    삭제
                                </a>
                                <a href="#" class="btn btn-primary" @click="editNote(note.id, note.content, note.categoryId)">수정</a>
                            </div>
                        </div>
                    </div>
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

header {
    display: flex;
    justify-content: center;
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
    margin-left: 40px;
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
