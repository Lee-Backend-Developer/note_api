<template>
  <div class="container">
    <form @submit.prevent="register">
      <h2 class="mb-3">회원가입</h2>
      <div class="input">
        <label for="email">아이디</label>
        <input
          class="form-control"
          type="text"
          name="email"
          placeholder="ID를 입력하세요."
        />
      </div>
      <div class="input">
        <label for="password">비밀번호</label>
        <input
          class="form-control"
          type="password"
          name="password"
          placeholder="사용할 비밀번호 입력하세요."
        />
      </div>

      <div class="alternative-option mt-4">
        가입을 했던적이 있습니까? <span @click="moveToLogin">로그인</span>
      </div>

      <button type="submit" id="register_button" class="mt-4 btn-pers">
        회원가입
      </button>

      <div
        class="alert alert-warning alert-dismissible fade show mt-5 d-none"
        role="alert"
        id="alert_2"
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
    register(submitEvent) {
      // data update
      this.loginId = submitEvent.target.elements.email.value;
      this.password = submitEvent.target.elements.password.value;

      //axios
      const url = "/member/register";
      const request = {
        loginId: this.loginId,
        password: this.password,
      };

      let self = this; // axios 위치에 router 사용하기 위함
      axios
        .post(url, request)
        .then(function (response) {
          console.log("http status => " + response.status);
          self.$router.push({ path: "/" });
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
    },
    moveToLogin() {
      this.$router.push("/");
    },
  },
};
</script>
