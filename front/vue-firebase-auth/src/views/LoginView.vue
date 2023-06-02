<template>
  <div class="container">
    <form @submit.prevent="login">
      <h2 class="mb-3">로그인</h2>
      <div class="input">
        <label for="email">아이디</label>
        <input
          class="form-control"
          type="text"
          name="loginId"
          placeholder="아이디"
        />
      </div>
      <div class="input">
        <label for="password">비밀번호</label>
        <input
          class="form-control"
          type="password"
          name="password"
          placeholder="비밀번호"
        />
      </div>
      <div class="alternative-option mt-4">
        계정이 없습니까? <span @click="moveToRegister">회원가입</span>
      </div>
      <button type="submit" class="mt-4 btn-pers" id="login_button">
        로그인
      </button>
      <div
        class="alert alert-warning alert-dismissible fade show mt-5 d-none"
        role="alert"
        id="alert_1"
      >
        Lorem ipsum dolor sit amet consectetur, adipisicing elit.
        <button
          type="button"
          class="btn-close"
          data-bs-dismiss="alert"
          aria-label="Close"
        ></button>
      </div>
    </form>
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      loginId: "",
      password: "",
    };
  },
  methods: {
    login(submitEvent) {
      this.loginId = submitEvent.target.elements.loginId.value;
      this.password = submitEvent.target.elements.password.value;

      //axios
      const url = "/api/member/login";
      const request = {
        loginId: this.loginId,
        password: this.password,
      };
      let self = this; // axios 위치에 router 사용하기 위함

      axios
        .post(url, request)
        .then(function (response) {
          console.log("http status => " + response.status);
          self.$router.push("/notes");
        })
        .catch(function (error) {
          let response = error.response.data;
          if (response.code == 400) {
            console.log(response.message);
            alert(response.message);
          }
          console.log(error);
        });
    },
    moveToRegister() {
      this.$router.push("/register");
    },
  },
};
</script>
