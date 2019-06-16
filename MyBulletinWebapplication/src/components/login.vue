<template>
   <div class="container">
      <div class="row">
        <div class="col-12">
          <h1>Login</h1>
          <div class="form-login align-center">                   
              <input v-model="credential.email" type="email" placeholder="Email" id="email"/>
              <input v-model="credential.password" type="password" placeholder="Password" id="password" v-on:keyup.enter="login" />
              <span class="error" v-for="error in errors">{{error}}</span>
              <button class="u-full-width button-primary" type="submit" v-on:click="login">Login</button>
          </div>
          {{cache}}
       </div>
    </div>
  </div>

</template>

<script>
    import axios from 'axios';
    export default {
      data() {
        return {
            errors: [],
            credential: {
              email:'',
              password:''
            }
        }
      },
      computed: {
        cache: function(){
          if(localStorage.getItem('token')){
            localStorage.clear();
          }
        }
      },
      methods: {
        login() {
          this.errors = [];
          if(!this.credential.email){
            this.errors.push("Email is required");
            return;
          }
          if(!this.credential.password){
            this.errors.push("Password is required");
            return;
          }
         axios.post(this.$store.getters.getUrl + 'api/login', this.credential).then((response) =>{
            console.log(response.data);
            localStorage.setItem('token', response.data.token);
            localStorage.setItem('professor_id', response.data.professorId);
            localStorage.setItem('professorName', response.data.professorName);
            localStorage.setItem('isAdmin', response.data.isAdmin);
            localStorage.setItem('refresh', 0);
            if(response.data.isAdmin != 0){
              this.$router.push('/admin'); 
            }else{
              this.$router.push('/subjects'); 
            }
          })
          .catch(function(error){
            this.errors.push("There is no useres with this");
          }.bind(this));
        }
      },
      created: function(){
        if(localStorage.getItem('token')){
          localStorage.clear();
          this.$router.go(0);
        }
      }
    }
</script>
<style scoped>
h1, h2 {
    font-size: 50px;
    text-align: center;
    width: 100%;
    margin: 30px 0;
}
.form{
  max-width: 500px;
  margin: 0 auto;
}
.error{
  color: red;
  display: block;
}
#email, #password{
     width: 100%;
     max-width: 400px;
     display: block;
     margin: 0 auto;
     padding: 10px;
     border: 1px solid #c4c4ef;
     border-radius: 30px;
     text-align: center;
     margin-bottom: 15px;
     outline: none;
}
 #email:focus, #password:focus, #email:active, #password:active{
     border-color: #00529c;
}
.button-primary{
     border: 1px solid #c4c4ef;
     border-radius: 30px;
     background-color: transparent;
     padding: 10px 50px;
     margin-top: 20px;
     transition: 0.4s;
     outline: none;
     cursor: pointer;
}
.button-primary:focus, .button-primary:active, .button-primary:hover{
     border-color: #00529c;
     color: #00529c;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
