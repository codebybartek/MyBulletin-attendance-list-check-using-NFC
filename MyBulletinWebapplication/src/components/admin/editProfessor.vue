<template>
   <div class="container">
      <div class="row">
        <div class="col-12">
          <h1>Edit professor: </h1>
          <div class="form-login align-center">      
              <span class="success">{{success}}</span>             
              <input v-model="professor.name" type="text" placeholder="Professor name and surename" id="name"/>
              <input v-model="professor.password" type="text" placeholder="Professor password" id="password"/>
              <input v-model="professor.email" type="email" placeholder="Professor email" id="email"/>
              <input v-model="professor.tagId" type="text" placeholder="Professor tag id" id="tagId"/>
              <span class="error" v-for="error in errors">{{error}}</span>
              <button class="u-full-width button-primary" type="submit" v-on:click="saveProfessor()" >Edit professor</button>
          </div>
       </div>
    </div>
  </div>

</template>

<script>
  import axios from 'axios';
  export default {
    name: 'editProfessor',
    data() {
      return {
          errors: [],
          success: '',
          professor_id: this.$route.params.id,
          professor: {
            name: '',
            password: '',
            email: '',
            tagId: '',
            _method: "PUT"
          }
      }
    },
    methods: {
      saveProfessor() {
        if(!professor.name){
          this.errors.push("Name is required");
          return;
        }
        if(!professor.tagId){
          this.errors.push("Tag id is required");
          return;
        }
        if(!professor.password){
          this.errors.push("Password is required");
          return;
        }
        if (!professor.email) {
          this.errors.push('Email required.');
          return;
        } else if (!this.validEmail(professor.email)) {
          this.errors.push('Valid email required.');
          return;
        }
        console.log(this.professor);
        axios.post(this.$store.getters.getUrl + 'api/professors/' + this.professor_id, this.professor)
        .then((response) =>{
          alert(response.data['updated']);
          this.getProfessor(this.professor_id);
        })
        .catch((error)=>{
          this.$router.push('/login'); 
        });
      },
      getProfessor(id) {
        axios.get(this.$store.getters.getUrl + 'api/professors/' + id + '/edit')
         .then(function (response) {
            this.professor.name = response.data.name;
            this.professor.email = response.data.email;
            this.professor.tagId = response.data.tagId;
            console.log(response.data[0]);
          }.bind(this))
          .catch((error)=>{
            this.$router.push('/login'); 
          });
      },
      validEmail(email){
        var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        return re.test(email);
      }
    },
    created: function () {
      if(!localStorage.getItem('token') || localStorage.getItem('isAdmin') != 1){
        this.$router.push('/login'); 
      }
      this.getProfessor(this.professor_id);
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
.success{
  display: block;
  margin-bottom: 30px;
  color: green;
}
input{
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
select{
  text-align-last:center;
}
#groupId option{
  text-align: center;
}
 #name:focus, #tagId:focus, #groupId:focus, #name:active, #tagId:active, #groupId:active{
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
